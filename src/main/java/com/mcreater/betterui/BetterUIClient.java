package com.mcreater.betterui;

import com.mcreater.betterui.config.Configuration;
import com.mcreater.betterui.shaders.MotionBlurShader;
import net.fabricmc.api.ClientModInitializer;

public class BetterUIClient implements ClientModInitializer {
    public static final String MOD_ID = "betterui";
    public void onInitializeClient() {
        Configuration.readConfig();
        MotionBlurShader.init();
    }
}
