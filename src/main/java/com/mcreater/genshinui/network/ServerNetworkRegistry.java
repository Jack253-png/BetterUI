package com.mcreater.genshinui.network;

import com.mcreater.genshinui.network.packet.c2s.GenshinAdvancedMessageC2SPacket;

import java.util.Stack;

public class ServerNetworkRegistry {
    private static final Stack<NbtMessage> nbtMessages = new Stack<>();
    public static void register() {
        GenshinAdvancedMessageC2SPacket.addListener((server, player, handler, buf, responseSender) -> {
            nbtMessages.push(NbtMessage.from(buf.readNbt()));
            System.out.println("message stack: " + nbtMessages);
        });
    }
}
