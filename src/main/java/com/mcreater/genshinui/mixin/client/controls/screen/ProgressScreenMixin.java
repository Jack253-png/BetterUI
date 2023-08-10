package com.mcreater.genshinui.mixin.client.controls.screen;

import net.minecraft.client.gui.screen.ProgressScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mcreater.genshinui.screens.ScreenHelper.fillScreen;

@Mixin(value = {ProgressScreen.class}, priority = Integer.MAX_VALUE)
public class ProgressScreenMixin {
    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    public void onRenderBackground(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        fillScreen(matrices, 255);
        ci.cancel();
    }
}
