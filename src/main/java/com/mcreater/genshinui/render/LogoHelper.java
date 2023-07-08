package com.mcreater.genshinui.render;

import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public abstract class LogoHelper {
    public static LogoProvider create(Identifier id, int width, int height) {
        return new SimpleLogoProvider() {
            public Identifier get() {
                return id;
            }

            @Override
            public Pair<Integer, Integer> getSize() {
                return new Pair<>(width, height);
            }
        };
    }
}
