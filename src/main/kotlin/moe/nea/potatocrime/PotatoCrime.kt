package moe.nea.potatocrime

import moe.nea.potatocrime.entity.PotatoGuardEntity
import moe.nea.potatocrime.registry.PotatoRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import org.slf4j.LoggerFactory

object PotatoCrime : ModInitializer {

	val modId = "potato-crime"
    private val logger = LoggerFactory.getLogger("potato-crime")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		PotatoRegistry.registerAll()
		FabricDefaultAttributeRegistry.register(PotatoRegistry.potatoGuard, PotatoGuardEntity.createMobAttributes())
	}
}