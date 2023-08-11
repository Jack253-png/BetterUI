package com.mcreater.genshinui.screens.widget.controls;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.awt.*;

public class RoundedRectGlassWidget extends ShapeWidget {
    public int radius;
    public Color color;
    public int blurSamples;
    private final RoundedRectWidget roundedRectWidget;
    public RoundedRectGlassWidget(int x, int y, int width, int height, int radius, int blurSamples, Color color) {
        super(x, y, width, height, new LiteralText(""));
        this.radius = radius;
        this.blurSamples = blurSamples;
        this.color = color;

        roundedRectWidget = new RoundedRectWidget(x, y, width, height, radius, color);

        addChild(new RoundedRectBlurWidget(x, y, width, height, radius, blurSamples));
        addChild(roundedRectWidget);
    }

    public RoundedRectGlassWidget hasOpacity(boolean hasOpacity) {
        roundedRectWidget.hasOpacity(hasOpacity);
        return this;
    }

    public State getState() {
        return State.ACTIVE;
    }

    public void renderShape(MatrixStack matrices, int mouseX, int mouseY, float delta) {}
}
