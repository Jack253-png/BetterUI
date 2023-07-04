package com.mcreater.betterui.mixin;

import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {SubtitlesHud.class}, priority = Integer.MAX_VALUE)
public class SubtitlesHudMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(MatrixStack matrices, CallbackInfo ci) {

    }
}
