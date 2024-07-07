package com.lautiux.golwer.datagen

import com.lautiux.golwer.block.MBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider

class MLootTableProvider(
    output: FabricDataOutput,
) : FabricBlockLootTableProvider(output) {
    override fun generate() {
        addDrop(MBlocks.FLOWER_BLOCK, MBlocks.FLOWER_BLOCK)
        addDrop(MBlocks.FLOWERED_CARVED_PUMPKIN_BLOCK, MBlocks.FLOWERED_CARVED_PUMPKIN_BLOCK)
    }
}