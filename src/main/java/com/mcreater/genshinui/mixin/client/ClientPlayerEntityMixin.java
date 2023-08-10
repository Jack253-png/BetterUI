package com.mcreater.genshinui.mixin.client;

import com.mcreater.genshinui.network.ClientNetworkRegistry;
import com.mcreater.genshinui.network.NbtMessage;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPlayerEntity.class, priority = Integer.MAX_VALUE)
public class ClientPlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "sendChatMessage")
    public void onSendChatMessage(String message, CallbackInfo ci) {
        ClientNetworkRegistry.sendMessage(new NbtMessage(message));
    }
}
