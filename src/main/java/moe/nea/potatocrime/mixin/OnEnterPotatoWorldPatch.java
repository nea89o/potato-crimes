package moe.nea.potatocrime.mixin;

import moe.nea.potatocrime.events.OnEnterPotatoWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class OnEnterPotatoWorldPatch {
    @Inject(method = "tickPortal", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;moveToWorld(Lnet/minecraft/server/world/ServerWorld;Z)Lnet/minecraft/entity/Entity;",
            shift = At.Shift.AFTER
    ))
    private void onMoveToWorld(CallbackInfo ci) {
        OnEnterPotatoWorld.Companion.getEVENT().invoker().onEnterPotatoWorld((Entity) (Object) this);
    }
}
