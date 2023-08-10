package com.mcreater.genshinui.network.emoji;

import net.minecraft.util.Identifier;

import javax.annotation.Nonnull;
import java.util.*;

public record GenshinResourceEmoji(Identifier resourcePath, @Nonnull Identifier id) {
    public static List<GenshinResourceEmoji> EMOJIS = new Vector<>();
    public static Map<String, List<GenshinResourceEmoji>> CLASSIFIED_EMOJIS = new HashMap<>();
    public GenshinResourceEmoji {
        EMOJIS.forEach(e -> {
            if (Objects.equals(e.id, id))  {
                throw new RuntimeException("Emoji already exists: " + e.id);
            }
        });
        EMOJIS.add(this);

        if (!CLASSIFIED_EMOJIS.containsKey(id.getNamespace())) CLASSIFIED_EMOJIS.put(id.getNamespace(), new Vector<>());

        CLASSIFIED_EMOJIS.get(id.getNamespace()).add(this);
    }

    public static GenshinResourceEmoji find(Identifier id) {
        return EMOJIS.stream().filter(e -> Objects.equals(e.id, id)).findFirst().orElse(null);
    }
}
