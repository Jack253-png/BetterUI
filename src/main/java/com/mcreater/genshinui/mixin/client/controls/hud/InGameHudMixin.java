package com.mcreater.genshinui.mixin.client.controls.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mcreater.genshinui.GenshinUIClient.NARRATION_WIDGET;

@Mixin(value = {InGameHud.class}, priority = Integer.MAX_VALUE)
public class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void onRenderTop(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        NARRATION_WIDGET.render(matrices, 0, 0, tickDelta);
        if (NARRATION_WIDGET.hasNarration()) ci.cancel();
    }
}
