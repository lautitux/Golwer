// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
package com.lautiux.golwer.entity

import com.lautiux.golwer.animation.FlowerGolemAnimations
import com.lautiux.golwer.entity.custom.FlowerGolemEntity
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper


class FlowerGolemModel<T : FlowerGolemEntity?>(root: ModelPart) : SinglePartEntityModel<T>() {
    private val FlowerGolem: ModelPart = root.getChild("Cuerpo")
    private val Head: ModelPart = root.getChild("Cuerpo").getChild("SubCuerpo").getChild("Cabeza")

    override fun render(
        matrices: MatrixStack,
        vertexConsumer: VertexConsumer,
        light: Int,
        overlay: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        FlowerGolem.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha)
    }

    override fun setAngles(
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        part.traverse().forEach { it.resetTransform() }
        setHeadAngles(headYaw, headPitch)
        animateMovement(FlowerGolemAnimations.Walk, limbAngle, limbDistance, 2f, 2.5f)
        if (entity != null) {
            updateAnimation(entity.idleAnimationState, FlowerGolemAnimations.Idle, animationProgress, 1f)
            updateAnimation(entity.plantGrowAnimationState, FlowerGolemAnimations.GrowPlants, animationProgress, 1f)
        }
    }

    private fun setHeadAngles(headYaw: Float, headPitch: Float) {
        val yaw = MathHelper.clamp(headYaw, -30f, 30f)
        val pitch = MathHelper.clamp(headPitch, -35f, 35f)

        // Convertirlo a radianes ?
        Head.yaw = yaw * 0.017453292f
        Head.pitch = pitch * 0.017453292f
    }

    override fun getPart(): ModelPart {
        return FlowerGolem
    }

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            val Cuerpo =
                modelPartData.addChild("Cuerpo", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 24.0f, 0.0f))

            val SubCuerpo =
                Cuerpo.addChild("SubCuerpo", ModelPartBuilder.create(), ModelTransform.pivot(0.0f, 0.0f, 0.0f))

            val Cabeza = SubCuerpo.addChild(
                "Cabeza",
                ModelPartBuilder.create().uv(0, 20).cuboid(-3.0f, -5.0f, -3.0f, 6.0f, 6.0f, 6.0f, Dilation(0.0f)),
                ModelTransform.pivot(0.0f, -14.0f, 0.0f)
            )

            val Nariz = Cabeza.addChild(
                "Nariz",
                ModelPartBuilder.create().uv(1, 1).cuboid(-1.0f, -1.0f, 0.0f, 2.0f, 3.0f, 1.0f, Dilation(0.0f)),
                ModelTransform.pivot(0.0f, -2.0f, -4.0f)
            )

            val Pata_Der = SubCuerpo.addChild(
                "Pata_Der",
                ModelPartBuilder.create().uv(30, 0).cuboid(-2.0f, 0.0f, -2.0f, 3.0f, 3.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(-2.0f, -3.0f, 0.0f)
            )

            val Pata_Izq = SubCuerpo.addChild(
                "Pata_Izq",
                ModelPartBuilder.create().uv(0, 32).cuboid(-1.0f, -1.0f, -2.0f, 3.0f, 3.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(2.0f, -2.0f, 0.0f)
            )

            val Brazo_Der = SubCuerpo.addChild(
                "Brazo_Der",
                ModelPartBuilder.create().uv(20, 31).cuboid(-2.0f, -1.0f, -2.0f, 3.0f, 7.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(7.0f, -11.0f, 0.0f)
            )

            val Brazo_Izq = SubCuerpo.addChild(
                "Brazo_Izq",
                ModelPartBuilder.create().uv(24, 20).cuboid(-1.0f, -1.0f, -2.0f, 3.0f, 7.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(-7.0f, -11.0f, 0.0f)
            )

            val Torso = SubCuerpo.addChild(
                "Torso",
                ModelPartBuilder.create().uv(0, 0).cuboid(-5.0f, -13.0f, -5.0f, 10.0f, 10.0f, 10.0f, Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f)
            )
            return TexturedModelData.of(modelData, 64, 64)
        }
    }
}