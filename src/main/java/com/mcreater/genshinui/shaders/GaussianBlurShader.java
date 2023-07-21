package com.mcreater.genshinui.shaders;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class GaussianBlurShader {
    private static final ManagedShaderEffect SHADER_EFFECT = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/gaussian_blur.json"));

    public static void init() {
        ShaderEffectRenderCallback.EVENT.register(SHADER_EFFECT::render);
    }

    public static void setBlurSamples(int samples) {
        SHADER_EFFECT.findUniform1i("Samples").set(samples);
    }

    public static void render(float delta) {
        SHADER_EFFECT.render(delta);
    }
}
