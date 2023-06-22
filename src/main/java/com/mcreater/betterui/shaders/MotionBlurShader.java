package com.mcreater.betterui.shaders;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.util.Identifier;

import static com.mcreater.betterui.BetterUIClient.MOD_ID;
import static com.mcreater.betterui.config.Configuration.OPTION_MOTION_BLUR_FACTOR;

public class MotionBlurShader {
    private static final ManagedShaderEffect motion_blur = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/motion_blur.json"));
    private static float currentBlur;

    public static void init() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (getBlurCalc() != 0) {
                if(currentBlur != getBlurCalc()) {
                    motion_blur.setUniformValue("BlendFactor", getBlurCalc());
                    currentBlur = getBlurCalc();
                }
                motion_blur.render(tickDelta);
            }
        });
    }

    private static float getBlurCalc() {
        return Math.min(OPTION_MOTION_BLUR_FACTOR.getValue(), 99) / 100F;
    }
}
