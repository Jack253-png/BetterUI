package com.mcreater.genshinui.shaders;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.awt.*;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;

public class Shapes {
    private static float LAST_TICK = 0;
    public static final ManagedShaderEffect ROUNDED_RECT_FILL = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/rounded_rect_fill.json"));
    private static float getScale() {
        return (float) MinecraftClient.getInstance().getWindow().getScaleFactor();
    }
    public static void fillCircle(float delta, int radius, int x, int y, Color color) {
        fillRoundedRect(delta, radius, x - radius, y - radius, x + radius, y + radius, color);
    }
    public static void fillRoundedRect(float delta, int radius, float x1, float y1, float x2, float y2, Color color) {
        fillRoundedRect(delta, radius, x1, y1, x2, y2, color, 20);
    }
    public static void fillRoundedRect(float delta, int radius, float x1, float y1, float x2, float y2, Color color, int blurSamples) {
        if (delta == LAST_TICK) {
            delta++;
        }
        LAST_TICK = delta;
        ROUNDED_RECT_FILL.findUniform1f("Radius").set(radius * getScale());
        ROUNDED_RECT_FILL.findUniform2f("Center1").set(x1 * getScale(), y1 * getScale());
        ROUNDED_RECT_FILL.findUniform2f("Center2").set(x2 * getScale(), y2 * getScale());
        ROUNDED_RECT_FILL.findUniform4f("Color").set(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                color.getAlpha() / 255F
        );
        ROUNDED_RECT_FILL.findUniform1f("Opacity").set(color.getAlpha() / 255F);
        ROUNDED_RECT_FILL.findUniform1i("BlurSamples").set(blurSamples);
        ROUNDED_RECT_FILL.render(delta);
    }
}
