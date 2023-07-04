package com.mcreater.betterui.render.logos;

import com.mcreater.betterui.render.LogoProvider;
import net.minecraft.util.Identifier;

import static com.mcreater.betterui.BetterUIClient.MOD_ID;

public class CraftTableProvider implements LogoProvider {
    public Identifier getLight() {
        return new Identifier(MOD_ID, "textures/logos/craft_table_light.png");
    }

    public Identifier getDark() {
        return new Identifier(MOD_ID, "textures/logos/craft_table_light.png");
    }
}
