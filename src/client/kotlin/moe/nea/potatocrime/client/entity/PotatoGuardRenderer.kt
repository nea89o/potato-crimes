package moe.nea.potatocrime.client.entity

import moe.nea.potatocrime.entity.PotatoGuardEntity
import moe.nea.potatocrime.registry.PotatoRegistry
import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier


class PotatoGuardRenderer(context: EntityRendererFactory.Context) :
    MobEntityRenderer<PotatoGuardEntity, PotatoGuardModel>(
        context,
        PotatoGuardModel(context.getPart(modelLayer)),
        0.5f
    ) {
    companion object {
        val modelLayer = EntityModelLayer(PotatoRegistry.identifier("potato_guard"), "main")
    }

    override fun getTexture(entity: PotatoGuardEntity): Identifier {
        return PotatoRegistry.identifier("textures/entity/potato_guard.png")
    }
}

class PotatoGuardModel(val part: ModelPart) : EntityModel<PotatoGuardEntity>() {
    val PotatoGuard = part.getChild("PotatoGuard")
    val PotatoHead = part.getChild("PotatoHead")

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            val PotatoGuard = modelPartData.addChild(
                "PotatoGuard",
                ModelPartBuilder.create().uv(0, 37).cuboid(-7.0f, -21.5f, -7.0f, 14.0f, 21.0f, 14.0f, Dilation(0.0f))
                    .uv(0, 0).cuboid(-8.0f, 0.5f, -8.0f, 16.0f, 21.0f, 16.0f, Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 0.5f, 0.0f)
            )

            val PotatoHead = modelPartData.addChild(
                "PotatoHead",
                ModelPartBuilder.create().uv(56, 29).cuboid(-4.0f, -6.5f, -4.0f, 8.0f, 13.0f, 8.0f, Dilation(0.0f)),
                ModelTransform.pivot(0.0f, -28.5f, 0.0f)
            )

            return TexturedModelData.of(modelData, 128, 128)

        }
    }

    override fun render(
        matrices: MatrixStack?,
        vertices: VertexConsumer?,
        light: Int,
        overlay: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        listOf(PotatoGuard, PotatoHead).forEach {
            it.render(matrices, vertices, light, overlay, red, green, blue, alpha)
        }
    }
     fun lerpAngle(angleOne: Float, angleTwo: Float, magnitude: Float): Float {
        var f = (magnitude - angleTwo) % 6.2831855f
        if (f < -3.1415927f) {
            f += 6.2831855f
        }

        if (f >= 3.1415927f) {
            f -= 6.2831855f
        }

        return angleTwo + angleOne * f
    }

    override fun setAngles(
        entity: PotatoGuardEntity?,
        limbAngle: Float,
        limbDistance: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        PotatoHead.yaw = headYaw / 180 * Math.PI.toFloat()
        PotatoHead.pitch = headPitch / 180 * Math.PI.toFloat()
//        PotatoGuard.setAngles(limbAngle, 0F, 0F)
    }
}