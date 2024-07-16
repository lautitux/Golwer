package com.lautiux.golwer.entity.custom

import com.lautiux.golwer.Golwer
import com.lautiux.golwer.block.MBlocks
import com.lautiux.golwer.entity.MEntities
import net.minecraft.block.*
import net.minecraft.entity.AnimationState
import net.minecraft.entity.EntityPose
import net.minecraft.entity.EntityType
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.ItemTags
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3i
import net.minecraft.world.StructureSpawns.BoundingBox
import net.minecraft.world.World
import net.minecraft.world.WorldEvents
import kotlin.math.min

class FlowerGolemEntity(entityType: EntityType<out AnimalEntity>, world: World) : AnimalEntity(entityType, world) {

    companion object {
        fun createFlowerGolemAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
        }

        private val POLLINATING_TRACKER: TrackedData<Boolean> =
            DataTracker.registerData(FlowerGolemEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
    }

    override fun initDataTracker(builder: DataTracker.Builder) {
        super.initDataTracker(builder)
        builder.add(POLLINATING_TRACKER, false)
    }


    private var pollinating: Boolean
        get() {
            return dataTracker.get(POLLINATING_TRACKER)
        }
        set(state) {
            dataTracker.set(POLLINATING_TRACKER, state)
        }

    // Animations
    val idleAnimationState = AnimationState()
    private var idleAnimationTimeout = 0

    val plantGrowAnimationState = AnimationState()
    private var plantGrowAnimationTimeout = 0

    override fun tick() {
        super.tick()
        if (world.isClient) {
            setUpAnimationState()
        }
        updateCropGrowingTiemout()
    }

    private fun setUpAnimationState() {
        if (idleAnimationTimeout <= 0) {
            idleAnimationTimeout = 40
            idleAnimationState.start(age)
        } else {
            idleAnimationTimeout -= 1
        }

        if (pollinating && plantGrowAnimationTimeout <= 0) {
            plantGrowAnimationTimeout = 35
            plantGrowAnimationState.start(age)
            // Golwer.logger.info("Animating $pollinating, $plantGrowAnimationTimeout")
        } else if (plantGrowAnimationTimeout > 0) {
            plantGrowAnimationTimeout -= 1
        }

        if (!pollinating && plantGrowAnimationTimeout <= 0) {
            // Golwer.logger.info("Animating Stop $pollinating, $plantGrowAnimationTimeout")
            plantGrowAnimationState.stop()
        }
    }

    // Extraido de clase: CamelEntity
    override fun updateLimbs(posDelta: Float) {
        val f = if (pose == EntityPose.STANDING) {
            min((posDelta * 6.0f).toDouble(), 1.0).toFloat()
        } else {
            0.0f
        }
        limbAnimator.updateLimbs(f, 0.2f)
    }

    override fun initGoals() {
        this.goalSelector.add(0, SwimGoal(this))
        this.goalSelector.add(
            1,
            TemptGoal(
                this,
                1.2,
                Ingredient.fromTag(ItemTags.FLOWERS),
                false
            )
        )
        this.goalSelector.add(2, LookAtEntityGoal(this, PlayerEntity::class.java, 4.0f))
        this.goalSelector.add(3, AnimalMateGoal(this, 1.15))
        this.goalSelector.add(4, FollowParentGoal(this, 1.2))
        this.goalSelector.add(5, WanderAroundGoal(this, 1.0))
        this.goalSelector.add(6, GrowCropsGoal())
    }

    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.isIn(ItemTags.FLOWERS)
    }

    override fun onDeath(damageSource: DamageSource?) {
        super.onDeath(damageSource)
        if (!world.isClient() && damageSource != null) {
            val itemDrop = ItemEntity(world, pos.x, pos.y, pos.z, ItemStack(MBlocks.FLOWER_BLOCK))
            world.spawnEntity(itemDrop)
            val entity = damageSource.source
            if (entity is LivingEntity) {
                entity.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, 100, 1))
            }
        }
    }

    override fun createChild(world: ServerWorld, entity: PassiveEntity): PassiveEntity {
        return MEntities.FLOWER_GOLEM.create(world)!!
    }

//    override fun getAmbientSound(): SoundEvent? {
//        return SoundEvents.ENTITY
//    }

    override fun getHurtSound(source: DamageSource?): SoundEvent? {
        return SoundEvents.ENTITY_FOX_HURT
    }

    override fun getDeathSound(): SoundEvent? {
        return SoundEvents.ENTITY_FOX_DEATH
    }

    // Crops
    private val maxCropsGrownInCropOneCharge = 5
    private var cropGrowingTimeoutTicks = (800..1600).random()
    private var cropsGrownSinceCharge = maxCropsGrownInCropOneCharge

    private fun updateCropGrowingTiemout() {
        if (cropGrowingTimeoutTicks <= 0) {
            cropGrowingTimeoutTicks = (800..1600).random()
            cropsGrownSinceCharge = 0
        } else if (cropsGrownSinceCharge >= maxCropsGrownInCropOneCharge) {
            cropGrowingTimeoutTicks -= 1
        }
    }

    inner class GrowCropsGoal : Goal() {
        override fun canStart(): Boolean {
            return if (cropsGrownSinceCharge >= maxCropsGrownInCropOneCharge) {
                //Golwer.logger.info("Cant Start $cropsGrownSinceCharge - $cropGrowingTimeoutTicks")
                false
            } else {
                (0..2).random() == 1
            }
        }

        override fun shouldContinue(): Boolean {
            return canStart()
        }

        override fun stop() {
            pollinating = false
            // Golwer.logger.info("Stop $pollinating")
            super.stop()
        }

        override fun tick() {
            if ((0..getTickCount(30)).random() == 0) {
                var pollinated = false
                for (i in -1..1) {
                    for (j in -1..1) {
                        val blockPos = blockPos.add(Vec3i(i, 0, j))
                        val blockState = world.getBlockState(blockPos)
                        val block = blockState.block
                        var updatedBlockState: BlockState? = null
                        if (blockState.isIn(BlockTags.BEE_GROWABLES)) {
                            when (block) {
                                is CropBlock -> {
                                    if (!block.isMature(blockState)) {
                                        updatedBlockState = block.withAge(block.getAge(blockState) + 1)
                                    }
                                }

                                is StemBlock -> {
                                    val age = blockState.get(StemBlock.AGE)
                                    if (age < 7) {
                                        updatedBlockState = blockState.with(StemBlock.AGE, age + 1)
                                    }
                                }

                                else -> {
                                    if (blockState.isOf(Blocks.SWEET_BERRY_BUSH)) {
                                        val age = blockState.get(SweetBerryBushBlock.AGE)
                                        if (age < 3) {
                                            updatedBlockState = blockState.with(SweetBerryBushBlock.AGE, age + 1)
                                        }
                                    }
                                }
                            }
                            if (updatedBlockState != null) {
                                world.syncWorldEvent(WorldEvents.BEE_FERTILIZES_PLANT, blockPos, 15)
                                world.setBlockState(blockPos, updatedBlockState)
                                pollinated = true
                                cropsGrownSinceCharge += 1
                            }
                        }
                    }
                }
                if (pollinated) {
//                    for (player in world.getNonSpectatingEntities(ServerPlayerEntity::class.java, boundingBox.expand(5.0))) {
////                        MCriteria.FLOWER_GOLEM_POLINATED.trigger(player)
//                    }
                    pollinating = true
                }
                // Golwer.logger.info("Polinizando $pollinating, $idleAnimationTimeout")
            }
        }
    }

}