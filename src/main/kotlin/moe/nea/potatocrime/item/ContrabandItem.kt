package moe.nea.potatocrime.item

import moe.nea.potatocrime.registry.PotatoRegistry
import moe.nea.potatocrime.registry.PotatoTranslations
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class ContrabandItem : Item(
    Settings().maxCount(1)
) {

    fun ItemStack.getContrabandCount(): Int {
        return getOrDefault(PotatoRegistry.contrabandData, 0)
    }

    fun ItemStack.setContrabandCount(count: Int) {
        set(PotatoRegistry.contrabandData, count)
    }

    fun dropCarrots(user: PlayerEntity, oldCount: Int): Int {
        val carrotCount = oldCount.coerceAtMost(Items.CARROT.maxCount)
        val stack = ItemStack(Items.CARROT, carrotCount)
        if (user is ServerPlayerEntity)
            user.dropItem(stack, false, true)
        return oldCount - carrotCount
    }

    override fun appendTooltip(
        stack: ItemStack,
        world: World?,
        tooltip: MutableList<Text>,
        context: TooltipContext
    ) {
        val count = stack.getContrabandCount()
        tooltip.add(PotatoTranslations.contrabandFillText.format(count.toString())
            .styled { it.withColor(Formatting.DARK_GRAY) })
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val stack = user.getStackInHand(hand)
        if (user.isSneaking) {
            val count = stack.getContrabandCount()
            if (count <= 0)
                return TypedActionResult.fail(stack)
            val newCount = dropCarrots(user, count)
            stack.setContrabandCount(newCount)
            return TypedActionResult.success(stack)
        } else {
            val count = stack.getContrabandCount()
            val carrotSlot = user.inventory.getSlotWithStack(ItemStack(Items.CARROT))
            if (carrotSlot < 0) {
                if (user is ServerPlayerEntity)
                    user.sendMessage(PotatoTranslations.noCarrotsToDeposit.format())
                return TypedActionResult.fail(stack)
            }
            val removed = user.inventory.removeStack(carrotSlot, (1000 - count).coerceAtLeast(0))
            stack.setContrabandCount(removed.count + count)
            return TypedActionResult.pass(stack)
        }
    }
}
