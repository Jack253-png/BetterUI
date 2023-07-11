package com.mcreater.genshinui.mixin.interfaces;

import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClickableWidget.class)
public interface ClickableWidgetInvoker {
    @Invoker("onClick")
    void invokeOnClick(double mouseX, double mouseY);
}
