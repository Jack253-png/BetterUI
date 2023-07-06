package com.mcreater.betterui.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {
    @Inject(at = @At("HEAD"), method = "tickTime", cancellable = true)
    public void onTickTime(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "setTimeOfDay", cancellable = true)
    public void onSetTimeOfDay(long l, CallbackInfo ci) {
        ci.cancel();
    }
}
