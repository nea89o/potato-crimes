package moe.nea.potatocrime.registry

import com.mojang.serialization.Codec
import moe.nea.potatocrime.PotatoCrime
import moe.nea.potatocrime.item.ContrabandItem
import net.minecraft.component.DataComponentType
import net.minecraft.item.Item
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object PotatoRegistry {
    private val delayedRegistries = mutableListOf<() -> Unit>()
    fun registerAll() {
        delayedRegistries.forEach { it.invoke() }
    }

    private fun <U, T : U> register(registry: Registry<U>, name: String, t: T): T {
        delayedRegistries.add {
            Registry.register(registry, Identifier(PotatoCrime.modId, name), t)
        }
        return t
    }

    private fun <T : Item> item(name: String, t: T): T = register(Registries.ITEM, name, t)
    private fun <T> dataComponent(name: String, block: (DataComponentType.Builder<T>) -> DataComponentType.Builder<T>) =
        register(
            Registries.DATA_COMPONENT_TYPE, name,
            block(DataComponentType.builder()).build()
        )

    val contraband = item("contraband", ContrabandItem())
    val contrabandData = dataComponent("contraband_data") {
        it.codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT)
    }
}