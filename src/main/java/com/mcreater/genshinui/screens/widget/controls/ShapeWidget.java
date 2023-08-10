package com.mcreater.genshinui.screens.widget.controls;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationProvider;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public abstract class ShapeWidget extends ClickableWidget {
    public int clickedOpacity = 255;
    public int hoveredOpacity = 225;
    public int activeOpacity = 175;
    public int disabledOpacity = 125;
    private final AnimatedValue opacity;

    public ShapeWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
        this.opacity = new AnimatedValue(activeOpacity, activeOpacity, 75, AnimationProvider.func(AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.CIRCULAR));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderShape(matrices, mouseX, mouseY, delta);
    }

    public abstract void renderShape(MatrixStack matrices, int mouseX, int mouseY, float delta);

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        appendDefaultNarrations(builder);
    }

    public static RoundedRectWidget createRoundedRect(int x, int y, int width, int height, int radius, Color color) {
        return new RoundedRectWidget(x, y, width, height, radius, color);
    }

    public static RoundedRectBlurWidget createRoundedRectBlur(int x, int y, int width, int height, int radius, int blurSamples) {
        return new RoundedRectBlurWidget(x, y, width, height, radius, blurSamples);
    }
}
