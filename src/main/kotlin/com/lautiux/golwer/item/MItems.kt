package com.lautiux.golwer.item

import com.lautiux.golwer.Golwer
import com.lautiux.golwer.entity.MEntities
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.SpawnEggItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object MItems {
    val FLOWER_GOLEM_SPAWN_EGG = registerItem(
        "flower_golem_spawn_egg",
        SpawnEggItem(MEntities.FLOWER_GOLEM, 0x7ebe54, 0xb878ed, FabricItemSettings())
    )

    private fun registerItem(name: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier(Golwer.MOD_ID, name), item)
    }

    fun registerItems() {
        Golwer.logger.info("Registering ${Golwer.MOD_ID} items")
    }
}