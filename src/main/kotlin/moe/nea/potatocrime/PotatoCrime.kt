package moe.nea.potatocrime

import moe.nea.potatocrime.entity.PotatoGuardEntity
import moe.nea.potatocrime.events.OnEnterPotatoWorld
import moe.nea.potatocrime.registry.PotatoRegistry
import moe.nea.potatocrime.registry.PotatoTranslations
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.SpawnReason
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Direction
import net.minecraft.world.dimension.DimensionTypes
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
        OnEnterPotatoWorld.EVENT.register(OnEnterPotatoWorld { player ->
            if (player is ServerPlayerEntity
                && player.world.dimensionEntry.matchesKey(DimensionTypes.POTATO)
                && hasContraband(player)
            ) {
                player.sendMessage(PotatoTranslations.youBrokeTheLaw.format())
                repeat(player.random.nextBetween(2, 4)) {
                    val randomOffsetPosition =
                        player.blockPos
                            .offset(Direction.Axis.X, player.random.nextBetween(-10, 10))
                            .offset(Direction.Axis.Z, player.random.nextBetween(-10, 10))
                    player.world.spawnEntity(
                        PotatoRegistry.potatoGuard.create(
                            player.world as ServerWorld,
                            null,
                            randomOffsetPosition,
                            SpawnReason.EVENT,
                            false,
                            false
                        )
                    )
                }
            }
        })

    }

    fun hasContraband(entity: ServerPlayerEntity): Boolean {
        return entity.inventory
            .contains { it.isIn(PotatoRegistry.carrotIshItems) }
    }
}