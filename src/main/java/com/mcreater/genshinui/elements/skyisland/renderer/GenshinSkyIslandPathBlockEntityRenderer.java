package com.mcreater.genshinui.elements.skyisland.renderer;

import com.mcreater.genshinui.elements.skyisland.entity.GenshinSkyIslandPathBlockEntity;
import com.mcreater.genshinui.elements.skyisland.model.GenshinSkyIslandPathBlockModel;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GenshinSkyIslandPathBlockEntityRenderer extends GeoBlockRenderer<GenshinSkyIslandPathBlockEntity> {
    public GenshinSkyIslandPathBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        super(new GenshinSkyIslandPathBlockModel());
    }
}
