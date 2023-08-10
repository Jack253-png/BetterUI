package com.mcreater.genshinui.network.packet.c2s;

import com.mcreater.genshinui.network.NbtMessage;
import com.mcreater.genshinui.network.NetworkConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;

public class GenshinAdvancedMessageC2SPacket extends CustomPayloadC2SPacket {
    private NbtMessage message;
    public GenshinAdvancedMessageC2SPacket(NbtMessage message) {
        super(NetworkConstants.C2S_GENSHIN_ADVANCED_MESSAGE, PacketByteBufs.create().writeNbt(message));
        this.message = message;
    }

    public void read(PacketByteBuf buf) {
        message = NbtMessage.from(buf.readNbt());
    }

    public NbtCompound getMessage() {
        return message;
    }

    public static void addListener(ServerPlayNetworking.PlayChannelHandler handler) {
        ServerPlayNetworking.registerGlobalReceiver(NetworkConstants.C2S_GENSHIN_ADVANCED_MESSAGE, handler);
    }
}
