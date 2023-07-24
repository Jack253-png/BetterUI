package com.mcreater.genshinui.elements;

import com.mcreater.genshinui.elements.skyisland.GenshinSkyIslandPathBlockEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

import static com.mcreater.genshinui.elements.GenshinEntities.GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY;

public class GenshinEntitiesRenderer {
    public static void register() {
        BlockEntityRendererRegistry.register(
                GENSHIN_SKY_ISLAND_PATH_BLOCK_ENTITY,
                GenshinSkyIslandPathBlockEntityRenderer::new
        );
    }
}
