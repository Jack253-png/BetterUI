package com.mcreater.betterui;

import com.mcreater.betterui.config.Configuration;
import com.mcreater.betterui.shaders.MotionBlurShader;
import net.fabricmc.api.ClientModInitializer;

import static com.mcreater.betterui.render.InternalFonts.STANDARD;
import static com.mcreater.betterui.render.InternalFonts.TITLE;

public class GenshinUIClient implements ClientModInitializer {
    public static final String MOD_ID = "betterui";
    public void onInitializeClient() {
        Configuration.readConfig();
        MotionBlurShader.init();
        TITLE.getNamespace();
        STANDARD.getNamespace();
    }
}
