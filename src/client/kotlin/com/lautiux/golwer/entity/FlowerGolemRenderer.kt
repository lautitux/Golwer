package com.lautiux.golwer.entity

import com.lautiux.golwer.Golwer
import com.lautiux.golwer.entity.custom.FlowerGolemEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

class FlowerGolemRenderer(context: EntityRendererFactory.Context): MobEntityRenderer<FlowerGolemEntity, FlowerGolemModel<FlowerGolemEntity>>(context,
    FlowerGolemModel(
        context.getPart(
            MModelLayers.FLOWER_GOLEM
        )
    ), 0.4f,)
{
    private val TEXTURE = Identifier.of(Golwer.MOD_ID, "textures/entity/flower_golem.png")

    override fun getTexture(entity: FlowerGolemEntity?): Identifier {
        return TEXTURE
    }

    override fun render(
        mobEntity: FlowerGolemEntity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int
    ) {
        if (mobEntity.isBaby) {
            matrixStack.scale(0.6f, 0.6f, 0.6f)
        } else {
            matrixStack.scale(1.0f, 1.0f, 1.0f)
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }
}