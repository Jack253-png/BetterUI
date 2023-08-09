package com.mcreater.genshinui.network;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class NbtMessage extends NbtCompound {
    private NbtMessage() {}
    public NbtMessage(String message) {
        putString("message", message);
    }
    public static NbtMessage from(@Nullable NbtCompound compound) {
        return Optional.ofNullable(compound)
                .map(compound1 -> {
                    if (compound1.contains("message")) return new NbtMessage(compound1.getString("message"));
                    return new NbtMessage();
                })
                .get();
    }
}
