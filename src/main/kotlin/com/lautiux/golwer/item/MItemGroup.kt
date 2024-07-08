package com.lautiux.golwer.item

import com.lautiux.golwer.Golwer
import com.lautiux.golwer.block.MBlocks
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object MItemGroup {
    fun registerItemGroup() {
        Golwer.logger.info("Registering ${Golwer.MOD_ID} item group")
        val group = FabricItemGroup.builder()
            .displayName(Text.literal("Golwer"))
            .icon { ItemStack(Items.DANDELION) }
            .entries { _, entries ->
                // Aca van los items
                entries.add(MItems.FLOWER_GOLEM_SPAWN_EGG)
                entries.add(MBlocks.FLOWER_BLOCK)
                entries.add(MBlocks.FLOWERED_CARVED_PUMPKIN_BLOCK)
            }.build()
        Registry.register(Registries.ITEM_GROUP, Identifier.of(Golwer.MOD_ID, "golwer"), group)
    }
}