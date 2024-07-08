package com.lautiux.golwer

import com.lautiux.golwer.block.MBlocks
import com.lautiux.golwer.datagen.MLootTableProvider
import com.lautiux.golwer.datagen.MModelProvider
import com.lautiux.golwer.datagen.MRecipieProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput


object GolwerDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider { dataOutput, registryLookup -> MLootTableProvider(dataOutput, registryLookup) }
        pack.addProvider { dataOutput, registryLookup -> MRecipieProvider(dataOutput, registryLookup) }
        pack.addProvider { dataOutput, _ -> MModelProvider(dataOutput) }
    }
}