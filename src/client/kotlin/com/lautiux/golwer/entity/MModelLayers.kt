package com.lautiux.golwer.entity

import com.lautiux.golwer.Golwer
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier

class MModelLayers {
    companion object {
        val FLOWER_GOLEM = EntityModelLayer(Identifier.of(Golwer.MOD_ID, "flower_golem"), "main")
    }
}