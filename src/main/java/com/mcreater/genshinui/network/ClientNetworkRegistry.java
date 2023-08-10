package com.mcreater.genshinui.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

import java.util.Stack;

import static com.mcreater.genshinui.network.NetworkConstants.C2S_GENSHIN_ADVANCED_MESSAGE_CHANNEL;
import static com.mcreater.genshinui.network.NetworkConstants.S2C_GENSHIN_ADVANCED_MESSAGE_CHANNEL;

@Environment(EnvType.CLIENT)
public class ClientNetworkRegistry {
    private static final Stack<NbtMessage> nbtMessages = new Stack<>();
    @Environment(EnvType.CLIENT)
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(S2C_GENSHIN_ADVANCED_MESSAGE_CHANNEL, ClientNetworkRegistry::onReceiveServerChatSyncPacket);
    }

    public static void onReceiveServerChatSyncPacket(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        NbtCompound mes = buf.readNbt();
        nbtMessages.push(NbtMessage.from(mes));
        System.out.println("server sync: " + mes);
    }

    public static void sendMessage(NbtMessage message) {
        ClientPlayNetworking.send(
                C2S_GENSHIN_ADVANCED_MESSAGE_CHANNEL,
                message.toPacketByteBuf()
        );
    }
}
