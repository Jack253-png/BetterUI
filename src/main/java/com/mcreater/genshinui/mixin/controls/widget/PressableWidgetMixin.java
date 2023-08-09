package com.mcreater.genshinui.mixin.controls.widget;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {PressableWidget.class}, priority = Integer.MAX_VALUE)
public abstract class PressableWidgetMixin extends ClickableWidget {
    public PressableWidgetMixin(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Inject(at = @At("HEAD"), method = "onClick", cancellable = true)
    public void onClick(double mouseX, double mouseY, CallbackInfo ci) {
        super.onClick(mouseX, mouseY);
        ci.cancel();
    }
}
