package com.mcreater.genshinui.network;

import com.mcreater.genshinui.network.packet.c2s.GenshinAdvancedMessageC2SPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.Stack;

@Environment(EnvType.SERVER)
public class ServerNetworkRegistry {
    private static final Stack<NbtMessage> nbtMessages = new Stack<>();
    public static void register() {
        GenshinAdvancedMessageC2SPacket.addListener((server, player, handler, buf, responseSender) -> {
            nbtMessages.push(NbtMessage.from(buf.readNbt()));
        });
    }
}
