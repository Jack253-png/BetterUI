package com.mcreater.genshinui.shaders;

import net.minecraft.client.MinecraftClient;

public class ShaderHelper {
    public static float getScale() {
        return (float) MinecraftClient.getInstance().getWindow().getScaleFactor();
    }
}
