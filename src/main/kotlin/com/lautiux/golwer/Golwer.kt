package com.lautiux.golwer

import com.lautiux.golwer.block.MBlocks
import com.lautiux.golwer.entity.MEntities
import com.lautiux.golwer.item.MItemGroup
import com.lautiux.golwer.item.MItems
import net.fabricmc.api.ModInitializer
import net.minecraft.block.Block
import net.minecraft.block.PumpkinBlock
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.slf4j.LoggerFactory
import kotlin.math.log

object Golwer : ModInitializer {
	val MOD_ID = "golwer"
	val logger = LoggerFactory.getLogger("golwer")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello world! from $MOD_ID")

		MItems.registerItems()
		MBlocks.registerBlocks()
		MItemGroup.registerItemGroup()
		MEntities.registerEntities()
	}
}