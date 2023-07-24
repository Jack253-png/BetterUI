package com.mcreater.genshinui;

import com.mcreater.genshinui.elements.GenshinBlocks;
import com.mcreater.genshinui.elements.GenshinEntities;
import com.mcreater.genshinui.network.EmojiHandler;
import net.fabricmc.api.ModInitializer;

public class GenshinUI implements ModInitializer {
    public void onInitialize() {
        EmojiHandler.registerServer();

        GenshinBlocks.register();
        GenshinEntities.register();
    }
}
