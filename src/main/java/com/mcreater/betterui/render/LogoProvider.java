package com.mcreater.betterui.render;

import com.mcreater.betterui.render.logos.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public interface LogoProvider {
    LogoProvider PICKAXE = new PickaxeLogoProvider();
    LogoProvider AXE = new AxeLogoProvider();
    LogoProvider SWORD = new SwordLogoProvider();
    LogoProvider COMPASS = new CompassLogoProvider();
    LogoProvider BOW = new BowLogoProvider();
    LogoProvider ENDER_EYE = new EnderEyeLogoProvider();
    LogoProvider NETHER_STAR = new NetherStarLogoProvider();
    Identifier getLight();
    Identifier getDark();
    Pair<Integer, Integer> getSize();
}
