package com.mcreater.genshinui.network;

import com.mcreater.genshinui.network.emoji.GenshinResourceEmoji;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NbtMessage extends NbtCompound {
    private NbtMessage() {}
    public NbtMessage(String message) {
        putString("message", message);
    }
    public NbtMessage(GenshinResourceEmoji emoji) {
        putString("emoji", emoji.id().toString());
    }
    public static NbtMessage from(@Nullable NbtCompound compound) {
        return Optional.ofNullable(compound)
                .map(compound1 -> {
                    if (compound1.contains("message")) return new NbtMessage(compound1.getString("message"));
                    if (compound1.contains("emoji")) {
                        GenshinResourceEmoji emoji = GenshinResourceEmoji.find(Identifier.tryParse(compound1.getString("emoji")));
                        return Optional
                                .ofNullable(emoji)
                                .map(NbtMessage::new)
                                .orElse(new NbtMessage());
                    }
                    return new NbtMessage();
                })
                .get();
    }

    public PacketByteBuf toPacketByteBuf() {
        return PacketByteBufs.create().writeNbt(this);
    }
}
