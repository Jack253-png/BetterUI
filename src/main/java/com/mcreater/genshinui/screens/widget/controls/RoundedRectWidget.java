package com.mcreater.genshinui.screens.widget.controls;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import java.awt.*;

import static com.mcreater.genshinui.GenshinUIClient.MOD_ID;
import static com.mcreater.genshinui.shaders.ShaderHelper.getScale;

public class RoundedRectWidget extends AbstractRectWidget {
    public static final ManagedShaderEffect ROUNDED_RECT_FILL = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/rounded_rect_fill.json"));
    public int radius;
    public Color color;
    private boolean hasOpacity = false;
    public RoundedRectWidget(int x, int y, int width, int height, int radius, Color color) {
        super(x, y, width, height, new LiteralText(""));
        this.radius = radius;
        this.color = color;
    }
    public RoundedRectWidget(int x, int y, int width, int height, Color color) {
        this(x, y, width, height, 8, color);
    }
    public RoundedRectWidget hasOpacity(boolean hasOpacity) {
        this.hasOpacity = hasOpacity;
        return this;
    }

    public void renderShape(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        ROUNDED_RECT_FILL.findUniform1f("Radius").set(radius * getScale());
        ROUNDED_RECT_FILL.findUniform2f("Center1").set(x * getScale(), y * getScale());
        ROUNDED_RECT_FILL.findUniform2f("Center2").set((x + width) * getScale(), (y + height) * getScale());
        ROUNDED_RECT_FILL.findUniform4f("Color").set(
                color.getRed() / 255F,
                color.getGreen() / 255F,
                color.getBlue() / 255F,
                color.getAlpha() / 255F
        );
        ROUNDED_RECT_FILL.findUniform1f("Opacity").set(
                !hasOpacity ?
                (float) ((float) color.getAlpha() / 255F * getOpacity()) :
                color.getAlpha() / 255F
        );
        ROUNDED_RECT_FILL.findUniform1f("BlurSamples").set(0);
        ROUNDED_RECT_FILL.render(delta);
    }
}
