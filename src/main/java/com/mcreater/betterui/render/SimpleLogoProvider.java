package com.mcreater.betterui.render;

import net.minecraft.util.Identifier;

public interface SimpleLogoProvider extends LogoProvider {
    Identifier get();

    default Identifier getLight() {
        return get();
    }

    default Identifier getDark() {
        return get();
    }
}
