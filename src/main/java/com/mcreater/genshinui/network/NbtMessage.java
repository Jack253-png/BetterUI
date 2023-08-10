package com.mcreater.genshinui.network;

import com.mcreater.genshinui.network.emoji.GenshinResourceEmoji;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NbtMessage extends NbtCompound {
    private NbtMessage() {
        try {
            NbtCompound playerMessage = new NbtCompound();
            GameProfile prof = MinecraftClient.getInstance().getSession().getProfile();

            playerMessage.putString("name", prof.getName());
            playerMessage.putString("id", prof.getId().toString());

            put("sender", playerMessage);
        }
        catch (Exception e) {
            putInt("is_server", 1);
        }
    }
    private NbtMessage(NbtCompound playerMessage) {
        if (playerMessage != null) {
            put("sender", playerMessage);
        }
        else {
            putInt("is_server", 1);
        }
    }
    public NbtMessage(String message) {
        this();
        putString("message", message);
    }
    public NbtMessage(String message, NbtCompound playerMessage) {
        this(playerMessage);
        putString("message", message);
    }
    public NbtMessage(GenshinResourceEmoji emoji) {
        this();
        putString("emoji", emoji.id().toString());
    }
    public NbtMessage(GenshinResourceEmoji emoji, NbtCompound playerMessage) {
        this(playerMessage);
        putString("emoji", emoji.id().toString());
    }
    public static NbtMessage from(@Nullable NbtCompound compound) {
        return Optional.ofNullable(compound)
                .map(compound1 -> {
                    NbtElement ply = compound1.get("sender");
                    NbtCompound comp = ply == null ? null : ((NbtCompound) ply);
                    if (compound1.contains("message")) return new NbtMessage(compound1.getString("message"), comp);
                    if (compound1.contains("emoji")) {
                        GenshinResourceEmoji emoji = GenshinResourceEmoji.find(Identifier.tryParse(compound1.getString("emoji")));
                        return Optional
                                .ofNullable(emoji)
                                .map(NbtMessage::new)
                                .orElse(new NbtMessage(comp));
                    }
                    return new NbtMessage(comp);
                })
                .get();
    }

    public PacketByteBuf toPacketByteBuf() {
        return PacketByteBufs.create().writeNbt(this);
    }
}
