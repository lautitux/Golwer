package com.lautiux.golwer.datagen

import com.lautiux.golwer.block.MBlocks
import com.lautiux.golwer.item.MItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.data.client.*
import net.minecraft.item.BlockItem
import net.minecraft.util.Identifier
import java.util.*

class MModelProvider(generator: FabricDataOutput) : FabricModelProvider(generator) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(MBlocks.FLOWER_BLOCK)

        // Sacado de BlockStateModelGenerator.class `private void registerPumpkins()`
        val textureMap = TextureMap.sideEnd(Blocks.PUMPKIN)
        blockStateModelGenerator.registerNorthDefaultHorizontalRotatable(
            MBlocks.FLOWERED_CARVED_PUMPKIN_BLOCK,
            textureMap
        )
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(
            MItems.FLOWER_GOLEM_SPAWN_EGG,
            Model(Optional.of(Identifier("item/template_spawn_egg")), Optional.empty())
        )
    }
}