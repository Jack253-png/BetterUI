package com.mcreater.genshinui.mixin.controls.screen;

import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = Screen.class, priority = Integer.MAX_VALUE)
public class ScreenMixin {
    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;fillGradient(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"), method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V")
    public void modifyRenderBackgroundArgs(Args args) {
        int colorStart = args.get(5);
        int colorEnd = args.get(6);

        args.set(5, 0x00000000);
        args.set(6, 0x00000000);
    }
}
