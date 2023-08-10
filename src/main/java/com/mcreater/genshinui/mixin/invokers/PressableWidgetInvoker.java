package com.mcreater.genshinui.mixin.invokers;

import net.minecraft.client.gui.widget.PressableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PressableWidget.class)
public interface PressableWidgetInvoker {
    @Invoker("onPress")
    void invokeOnPress();
}
