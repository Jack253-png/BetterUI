package com.mcreater.genshinui;

import com.mcreater.genshinui.network.EmojiHandler;
import net.fabricmc.api.ModInitializer;

public class GenshinUI implements ModInitializer {
    public void onInitialize() {
        EmojiHandler.registerServer();
    }
}
