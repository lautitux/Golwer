package com.lautiux.golwer.block

import com.lautiux.golwer.Golwer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object MBlocks {
    val FLOWER_BLOCK = registerBlock("flower_block", Block(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK)))

    val FLOWERED_CARVED_PUMPKIN_BLOCK = registerBlock(
        "flowered_carved_pumpkin",
        FloweredCarvedPumpkin(FabricBlockSettings.copyOf(Blocks.CARVED_PUMPKIN))
    )

    private fun registerBlock(name: String, block: Block): Block {
        registerBItem(name, block)
        return Registry.register(Registries.BLOCK, Identifier(Golwer.MOD_ID, name), block)
    }

    private fun registerBItem(name: String, block: Block): Item {
        return Registry.register(
            Registries.ITEM,
            Identifier(Golwer.MOD_ID, name),
            BlockItem(block, FabricItemSettings())
        )
    }

    fun registerBlocks() {
        Golwer.logger.info("Registering ${Golwer.MOD_ID} blocks")

    }
}