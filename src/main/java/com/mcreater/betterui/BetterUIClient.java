package com.mcreater.betterui;

import com.mcreater.betterui.config.Configuration;
import net.fabricmc.api.ClientModInitializer;

public class BetterUIClient implements ClientModInitializer {
    public static final String MOD_ID = "betterui";
    public void onInitializeClient() {
        Configuration.readConfig();
    }
}
