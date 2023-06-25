package com.mcreater.betterui.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mcreater.betterui.config.Configuration.OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING;
import static com.mcreater.betterui.util.SafeValue.safeBoolean;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Nullable public Screen currentScreen;

    @Inject(at  = @At("HEAD"), method = "setScreen", cancellable = true)
    public void onSetScreen(Screen screen, CallbackInfo ci) {
        if (safeBoolean(OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING.getValue())) return;

        if (screen == null) {
            if (currentScreen instanceof ChatScreen) {
                currentScreen.removed();
                ci.cancel();
            }
        }
    }
}
