package com.mcreater.genshinui.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.mcreater.genshinui.network.NetworkConstants.C2S_GENSHIN_EMOJI;
import static com.mcreater.genshinui.network.NetworkConstants.S2C_GENSHIN_EMOJI_SYNC;

public class EmojiHandler {
    public static final Logger LOGGER = LogManager.getLogger(EmojiHandler.class);
    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(C2S_GENSHIN_EMOJI, (server, player, handler, buf, responseSender) -> {
            server.getPlayerManager().getPlayerList().forEach(p -> {
                if (ServerPlayNetworking.canSend(p, S2C_GENSHIN_EMOJI_SYNC)) {
                    ServerPlayNetworking.send(p, S2C_GENSHIN_EMOJI_SYNC, new PacketByteBuf(buf.copy()));
                } else {
                    System.out.println(p + " cannot sync");
                }
            });
            LOGGER.info("C2S: {} : {} : {}", buf.readUuid(), buf.readString(), buf.readNbt());
        });
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(S2C_GENSHIN_EMOJI_SYNC, (client, handler, buf, responseSender) -> {
            LOGGER.info("S2C sync: {} : {} : {}", buf.readUuid(), buf.readString(), buf.readNbt());
        });
    }

    @Environment(EnvType.CLIENT)
    public static void sendC2S(ClientPlayerEntity entity) {
        PacketByteBuf buf = PacketByteBufs.create();
        NbtCompound compound = new NbtCompound();
        compound.putString("emoji_id", "test");

        buf.writeUuid(entity.getGameProfile().getId())
                .writeString(entity.getGameProfile().getName())
                .writeNbt(compound);

        ClientPlayNetworking.send(C2S_GENSHIN_EMOJI, buf);
    }
}
