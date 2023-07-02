package com.mcreater.betterui.mixin;

import net.minecraft.client.gui.RotatingCubeMapRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {RotatingCubeMapRenderer.class}, priority = Integer.MAX_VALUE)
public class RotatingCubeMapRendererMixin {
    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    public void onRender(float delta, float alpha, CallbackInfo ci) {
//        ci.cancel();
    }
}
