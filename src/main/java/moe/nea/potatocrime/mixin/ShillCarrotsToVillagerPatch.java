package moe.nea.potatocrime.mixin;

import moe.nea.potatocrime.registry.PotatoTranslations;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class ShillCarrotsToVillagerPatch extends MerchantEntity {
    @Shadow
    public abstract boolean isClient();

    public ShillCarrotsToVillagerPatch(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void onInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var itemInHand = player.getStackInHand(hand);
        if (this.isAlive() && !this.hasCustomer() && !this.isSleeping()) {
            if (itemInHand.isOf(Items.CARROT)) {
                if (!isClient()) {
                    playSound(SoundEvents.ENTITY_VILLAGER_TRADE);
                    itemInHand.setCount(itemInHand.getCount() - 1);
                    player.giveItemStack(new ItemStack(Items.EMERALD));
                    player.sendMessage(PotatoTranslations.INSTANCE.getVillagerTrade().format());
                }
                cir.setReturnValue(ActionResult.success(isClient()));
            }
            if (itemInHand.isOf(Items.GOLDEN_CARROT)) {
                if (!isClient()) {
                    playSound(SoundEvents.ENTITY_VILLAGER_TRADE);
                    itemInHand.setCount(itemInHand.getCount() - 1);
                    player.giveItemStack(new ItemStack(Items.EMERALD_BLOCK));
                    player.sendMessage(PotatoTranslations.INSTANCE.getVillagerTrade().format());
                }
                cir.setReturnValue(ActionResult.success(isClient()));
            }
        }
    }
}
