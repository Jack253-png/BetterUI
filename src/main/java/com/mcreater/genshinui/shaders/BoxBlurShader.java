package com.mcreater.genshinui.shaders;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class BoxBlurShader {
    private static final ManagedShaderEffect SHADER_EFFECT = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/box_blur.json"));
    public static void init() {
        ShaderEffectRenderCallback.EVENT.register(SHADER_EFFECT::render);
    }

    public static void setRadius(float radius) {
        SHADER_EFFECT.findUniform1f("Radius").set(radius);
    }
}
