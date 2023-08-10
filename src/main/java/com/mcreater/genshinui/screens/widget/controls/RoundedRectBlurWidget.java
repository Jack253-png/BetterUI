package com.mcreater.genshinui.screens.widget.controls;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import java.awt.*;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;
import static com.mcreater.genshinui.shaders.ShaderHelper.getScale;

public class RoundedRectBlurWidget extends ShapeWidget {
    public static final ManagedShaderEffect ROUNDED_RECT_BLUR = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/rounded_rect_box_blur.json"));
    public int blurSamples;
    public int radius;
    private final Color color = new Color(0, 0, 0, 0);
    public RoundedRectBlurWidget(int x, int y, int width, int height, int radius, int blurSamples) {
        super(x, y, width, height, new LiteralText(""));
        this.blurSamples = blurSamples;
        this.radius = radius;
    }

    public void renderShape(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        ROUNDED_RECT_BLUR.findUniform1f("Radius").set(radius * getScale());
        ROUNDED_RECT_BLUR.findUniform2f("Center1").set(x * getScale(), y * getScale());
        ROUNDED_RECT_BLUR.findUniform2f("Center2").set((x + width) * getScale(), (y + height) * getScale());
        ROUNDED_RECT_BLUR.findUniform4f("Color").set(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                color.getAlpha() / 255F
        );
        ROUNDED_RECT_BLUR.findUniform1f("Opacity").set(color.getAlpha() / 255F);
        ROUNDED_RECT_BLUR.findUniform1f("BlurSamples").set(blurSamples);
        ROUNDED_RECT_BLUR.render(delta);
    }
}
