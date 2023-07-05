package com.mcreater.betterui.render.logos;

import com.mcreater.betterui.render.SimpleLogoProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import static com.mcreater.betterui.BetterUIClient.MOD_ID;

public class EnderEyeLogoProvider implements SimpleLogoProvider {
    public Identifier get() {
        return new Identifier(MOD_ID, "textures/logos/ender_eye.png");
    }

    public Pair<Integer, Integer> getSize() {
        return new Pair<>(16, 16);
    }
}
