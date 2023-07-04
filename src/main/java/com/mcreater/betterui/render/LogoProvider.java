package com.mcreater.betterui.render;

import com.mcreater.betterui.render.logos.CraftTableProvider;
import net.minecraft.util.Identifier;

public interface LogoProvider {
    LogoProvider CRAFT_TABLE = new CraftTableProvider();
    Identifier getLight();
    Identifier getDark();
}
