package com.mcreater.genshinui.network;

import com.mcreater.genshinui.network.emoji.GenshinResourceEmoji;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class NbtMessage {
    private GameProfile profile;
    private String source_message = "";
    private Map<Integer, GenshinResourceEmoji> emojis = new HashMap<>();
    private MessageType type = MessageType.EMPTY;
    private NbtMessage(GameProfile profile) {
        this.profile = profile;
    }
    private NbtMessage() {
        this(MinecraftClient.getInstance().getSession().getProfile());
    }
    public NbtMessage(String message) {
        this();
        source_message = message;
        updateType();
    }
    public NbtMessage(String message, GameProfile playerMessage) {
        this(playerMessage);
        source_message = message;
        updateType();
    }
    public NbtMessage(GenshinResourceEmoji emoji) {
        this();
        emojis.put(0, emoji);
        updateType();
    }
    public NbtMessage(GenshinResourceEmoji emoji, GameProfile playerMessage) {
        this(playerMessage);
        emojis.put(0, emoji);
        updateType();
    }
    public NbtMessage(String sourceMessage, Map<Integer, GenshinResourceEmoji> emojis) {
        this();
        source_message = sourceMessage;
        this.emojis = emojis;
        updateType();
    }

    public NbtMessage(String sourceMessage, Map<Integer, GenshinResourceEmoji> emojis, GameProfile playerMessage) {
        this(playerMessage);
        source_message = sourceMessage;
        this.emojis = emojis;
        updateType();
    }

    private void updateType() {
        boolean isTextEmp = source_message.length() == 0;
        boolean hasEmoj = !emojis.isEmpty();

        this.type = isTextEmp ? (hasEmoj ? MessageType.EMOJI : MessageType.EMPTY) : (hasEmoj ? MessageType.TEXT : MessageType.BOTH);
    }

    public NbtCompound toNbt() {
        NbtCompound compound = new NbtCompound();
        compound.putByte("type", type.getId());

        NbtCompound sender = new NbtCompound();
        sender.putUuid("id", profile.getId());
        sender.putString("name", profile.getName());
        compound.put("sender", sender);

        if (type.hasText) {
            compound.putString("text", source_message);
        }
        if (type.hasEmoji) {
            NbtCompound emjs = new NbtCompound();
            emojis.forEach((k, v) -> emjs.putString(String.valueOf(k), v.id().toString()));
            compound.put("emojis", emjs);
        }

        return compound;
    }

    public GameProfile getProfile() {
        return profile;
    }

    public Map<Integer, GenshinResourceEmoji> getEmojis() {
        return emojis;
    }

    public String getMessage() {
        return source_message;
    }

    public static NbtMessage fromNbt(NbtCompound compound) {
        NbtCompound sender = compound.getCompound("sender");
        GameProfile prof = new GameProfile(sender.getUuid("id"), sender.getString("name"));
        return switch (compound.getByte("type")) {
            case 0 -> new NbtMessage(compound.getString("text"), prof);
            case 1 ->
                    new NbtMessage(GenshinResourceEmoji.find(Identifier.tryParse(compound.getCompound("emojis").getString("0"))), prof);
            case 2 -> new NbtMessage(
                    compound.getString("text"),
                    new HashMap<>() {{
                        compound.getCompound("emojis").getKeys().forEach(k -> put(Integer.valueOf(k), GenshinResourceEmoji.find(Identifier.tryParse(compound.getCompound("emojis").getString(k)))));
                    }},
                    prof
            );
            case -1 -> new NbtMessage(prof);
            default -> throw new IllegalStateException("Unexpected value: " + compound.getByte("type"));
        };
    }

    public PacketByteBuf toPacketByteBuf() {
        return PacketByteBufs.create().writeNbt(this.toNbt());
    }

    public MessageType getType() {
        return type;
    }

    public enum MessageType {
        EMPTY(-1, false, false),
        TEXT(0, true, false),
        EMOJI(1, false, true),
        BOTH(2, true, true);
        private final int id;
        private final boolean hasText;
        private final boolean hasEmoji;
        MessageType(int id, boolean hasText, boolean hasEmoji) {
            this.id = id;
            this.hasText = hasText;
            this.hasEmoji = hasEmoji;
        }

        public boolean hasText() {
            return hasText;
        }

        public boolean hasEmoji() {
            return hasEmoji;
        }

        public byte getId() {
            return (byte) id;
        }
    }
}
