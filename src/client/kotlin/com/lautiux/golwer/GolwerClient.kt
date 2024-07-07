package com.lautiux.golwer

import com.lautiux.golwer.entity.FlowerGolemModel
import com.lautiux.golwer.entity.FlowerGolemRenderer
import com.lautiux.golwer.entity.MEntities
import com.lautiux.golwer.entity.MModelLayers
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry

object GolwerClient : ClientModInitializer {
	override fun onInitializeClient() {
		EntityRendererRegistry.register(MEntities.FLOWER_GOLEM) { context -> FlowerGolemRenderer(context) }
		EntityModelLayerRegistry.registerModelLayer(MModelLayers.FLOWER_GOLEM) { FlowerGolemModel.getTexturedModelData() }
	}
}