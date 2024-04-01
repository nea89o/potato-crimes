package moe.nea.potatocrime

import moe.nea.potatocrime.client.entity.PotatoGuardModel
import moe.nea.potatocrime.client.entity.PotatoGuardRenderer
import moe.nea.potatocrime.registry.PotatoRegistry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry


object PotatoCrimeClient : ClientModInitializer {
    override fun onInitializeClient() {
        EntityRendererRegistry.register(PotatoRegistry.potatoGuard, ::PotatoGuardRenderer)
        EntityModelLayerRegistry.registerModelLayer(
            PotatoGuardRenderer.modelLayer,
            PotatoGuardModel::getTexturedModelData
        )
    }
}