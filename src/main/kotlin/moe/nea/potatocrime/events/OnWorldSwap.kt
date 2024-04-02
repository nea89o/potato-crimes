package moe.nea.potatocrime.events

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.Entity

fun interface OnEnterPotatoWorld {
    fun onEnterPotatoWorld(entity: Entity)

    companion object {
        val EVENT = EventFactory.createArrayBacked(OnEnterPotatoWorld::class.java) { events ->
            OnEnterPotatoWorld { entity -> events.forEach { it.onEnterPotatoWorld(entity) } }
        }
    }
}