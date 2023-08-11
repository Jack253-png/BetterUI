package com.mcreater.genshinui.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Stack;

import static com.mcreater.genshinui.network.NetworkConstants.C2S_GENSHIN_ADVANCED_MESSAGE_CHANNEL;
import static com.mcreater.genshinui.network.NetworkConstants.S2C_GENSHIN_ADVANCED_MESSAGE_CHANNEL;

public class ServerNetworkRegistry {
    private static final Stack<NbtMessage> nbtMessages = new Stack<>();
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(C2S_GENSHIN_ADVANCED_MESSAGE_CHANNEL, ServerNetworkRegistry::onReceiveClientChatPacket);
    }

    public static void onReceiveClientChatPacket(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        server.getPlayerManager().getPlayerList().forEach(serverPlayerEntity -> {
            if (ServerPlayNetworking.canSend(serverPlayerEntity, S2C_GENSHIN_ADVANCED_MESSAGE_CHANNEL)) {
                ServerPlayNetworking.send(serverPlayerEntity, S2C_GENSHIN_ADVANCED_MESSAGE_CHANNEL, PacketByteBufs.copy(buf));
            }
            else {
                System.out.println(player + " cannot sync");
            }
        });
        NbtCompound mes = buf.readNbt();
        nbtMessages.push(NbtMessage.fromNbt(mes));
        System.out.println("message stack: " + nbtMessages);
    }
}
