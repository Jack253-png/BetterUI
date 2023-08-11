package com.mcreater.genshinui.screens.widget.controls;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationProvider;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;

import java.awt.*;
import java.util.List;
import java.util.Vector;

public abstract class ShapeWidget extends ClickableWidget {
    public enum State {
        DISABLED,
        ACTIVE,
        HOVERED,
        CLICKED
    }
    public int clickedOpacity = 255;
    public int hoveredOpacity = 225;
    public int activeOpacity = 175;
    public int disabledOpacity = 125;
    private final AnimatedValue opacity;
    private List<ShapeWidget> childrens = new Vector<>();

    public ShapeWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
        this.opacity = new AnimatedValue(activeOpacity, activeOpacity, 75, AnimationProvider.func(AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.CIRCULAR));
    }

    public double getOpacity() {
        return opacity.getCurrentValue();
    }

    public abstract State getState();
    private void updateOpacity() {
        switch (getState()) {
            case DISABLED -> opacity.setExpectedValue(disabledOpacity);
            case ACTIVE ->  opacity.setExpectedValue(activeOpacity);
            case HOVERED -> opacity.setExpectedValue(hoveredOpacity);
            case CLICKED -> opacity.setExpectedValue(clickedOpacity);
            default -> {}
        }
    }

    public void addChild(ShapeWidget child) {
        childrens.add(child);
    }

    public void removeChild(ShapeWidget child) {
        childrens.remove(child);
    }
    public void removeAllChild() {
        childrens.clear();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateOpacity();
        childrens.forEach(c -> c.render(matrices, mouseX, mouseY, delta));
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

    public static RoundedRectGlassWidget createRoundedRectGlass(int x, int y, int width, int height, int radius, int blurSamples, Color color) {
        return new RoundedRectGlassWidget(x, y, width, height, radius, blurSamples, color);
    }

    public static TriangleWidget createTriangle(Vec2f a, Vec2f b, Vec2f c) {
        return new TriangleWidget(a, b, c);
    }
}
