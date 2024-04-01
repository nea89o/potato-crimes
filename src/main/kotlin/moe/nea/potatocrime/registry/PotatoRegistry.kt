package moe.nea.potatocrime.registry

import com.mojang.serialization.Codec
import moe.nea.potatocrime.PotatoCrime
import moe.nea.potatocrime.entity.PotatoGuardEntity
import moe.nea.potatocrime.item.ContrabandItem
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.component.DataComponentType
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

object PotatoRegistry {
    private val delayedRegistries = mutableListOf<() -> Unit>()
    private val items = mutableListOf<Item>()
    fun registerAll() {
        delayedRegistries.forEach { it.invoke() }
    }

    private fun <U, T : U> register(registry: Registry<U>, name: String, t: T): T {
        delayedRegistries.add {
            Registry.register(registry, Identifier(PotatoCrime.modId, name), t)
        }
        return t
    }

    private fun <T : Item> item(name: String, t: T): T = register(Registries.ITEM, name, t).also(items::add)
    private fun <T> dataComponent(name: String, block: (DataComponentType.Builder<T>) -> DataComponentType.Builder<T>) =
        register(
            Registries.DATA_COMPONENT_TYPE, name,
            block(DataComponentType.builder()).build()
        )

    fun identifier(name: String) = Identifier(PotatoCrime.modId, name)

    val group = register(Registries.ITEM_GROUP, "default_group", FabricItemGroup.builder()
        .icon { ItemStack(contraband) }
        .displayName(PotatoTranslations.itemGroup.format())
        .entries { _, entries ->
            items.forEach(entries::add)
        }
        .build())
    val carrotIshItems = TagKey.of(RegistryKeys.ITEM, identifier("carrotish"))
    val contraband = item("contraband", ContrabandItem())
    val contrabandData = dataComponent("contraband_data") {
        it.codec(Codec.INT).packetCodec(PacketCodecs.VAR_INT)
    }
    val potatoGuard = register(
        Registries.ENTITY_TYPE, "potato_guard",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ::PotatoGuardEntity)
            .dimensions(EntityDimensions.fixed(1f, 3f))
            .build()
    )

}