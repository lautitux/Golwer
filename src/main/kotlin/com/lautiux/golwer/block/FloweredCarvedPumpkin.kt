package com.lautiux.golwer.block

import com.lautiux.golwer.Golwer
import com.lautiux.golwer.entity.MEntities
import com.lautiux.golwer.entity.custom.FlowerGolemEntity
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.block.BlockState
import net.minecraft.block.CarvedPumpkinBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i
import net.minecraft.world.World

class FloweredCarvedPumpkin(settings: Settings) : CarvedPumpkinBlock(settings) {
    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack?
    ) {
        super.onPlaced(world, pos, state, placer, itemStack)
        if (!world.isClient() && placer != null) {
            val posUnder = pos.subtract(Vec3i(0, 1, 0))
            val blockUnder = world.getBlockState(posUnder)
            if (blockUnder.block == MBlocks.FLOWER_BLOCK) {
                world.breakBlock(posUnder, false)
                world.breakBlock(pos, false)

                val golem = FlowerGolemEntity(MEntities.FLOWER_GOLEM, world)
                golem.refreshPositionAndAngles(posUnder, 0f, 0f)
                world.spawnEntity(golem)

                // Trigger advancement
                for (player in world.getNonSpectatingEntities(
                    ServerPlayerEntity::class.java,
                    golem.boundingBox.expand(5.0)
                )) {
                    Criteria.SUMMONED_ENTITY.trigger(player, golem)
                    Criteria.BEE_NEST_DESTROYED
                }
            }
        }
    }
}