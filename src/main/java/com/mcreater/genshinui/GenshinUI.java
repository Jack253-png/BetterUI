package com.mcreater.genshinui;

import com.mcreater.genshinui.elements.GenshinBlocks;
import com.mcreater.genshinui.elements.GenshinEntities;
import net.fabricmc.api.ModInitializer;

public class GenshinUI implements ModInitializer {
    public void onInitialize() {
        GenshinBlocks.register();
        GenshinEntities.register();
    }
}
