package com.lautiux.golwer.entity

import com.lautiux.golwer.Golwer
import com.lautiux.golwer.entity.custom.FlowerGolemEntity
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object MEntities {
    val FLOWER_GOLEM: EntityType<FlowerGolemEntity> = Registry.register(Registries.ENTITY_TYPE,
        Identifier.of(Golwer.MOD_ID, "flower_golem"),
        FabricEntityTypeBuilder
            .create(SpawnGroup.CREATURE) { entityType, world -> FlowerGolemEntity(entityType, world) }
            .dimensions(EntityDimensions.fixed(1.0f, 1.1f))
            .build())

    fun registerEntities() {
        Golwer.logger.info("Registering ${Golwer.MOD_ID} entities")
        FabricDefaultAttributeRegistry.register(FLOWER_GOLEM, FlowerGolemEntity.createFlowerGolemAttributes())
    }
}