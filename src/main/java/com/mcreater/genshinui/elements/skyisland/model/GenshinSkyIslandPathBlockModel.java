package com.mcreater.genshinui.elements.skyisland.model;

import com.mcreater.genshinui.elements.skyisland.entity.GenshinSkyIslandPathBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class GenshinSkyIslandPathBlockModel extends AnimatedGeoModel<GenshinSkyIslandPathBlockEntity> {
    public Identifier getModelLocation(GenshinSkyIslandPathBlockEntity object) {
        return new Identifier(MOD_ID, "geo/GenshinSkyIslandPath.geo.json");
    }

    public Identifier getTextureLocation(GenshinSkyIslandPathBlockEntity object) {
        return new Identifier(MOD_ID, "textures/texture2.png");
    }

    public Identifier getAnimationFileLocation(GenshinSkyIslandPathBlockEntity animatable) {
        return new Identifier(MOD_ID, "animations/GenshinSkyIslandPath.animation.json");
    }
}
