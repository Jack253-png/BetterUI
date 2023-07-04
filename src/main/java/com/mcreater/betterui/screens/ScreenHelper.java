package com.mcreater.betterui.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public abstract class ScreenHelper extends Screen {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    protected ScreenHelper(Text title) {
        super(title);
    }
    public static void fillScreen(MatrixStack matrices, int opacity) {
        fill(matrices, 0, 0, CLIENT.getWindow().getWidth(), CLIENT.getWindow().getHeight(), new Color(253, 253, 253, opacity).getRGB());
    }
    public static void drawCenteredTextWithoutShadow(MatrixStack matrices, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
        textRenderer.draw(matrices, text, (float)(centerX - textRenderer.getWidth(text) / 2), (float)y, color);
    }
    public static void drawTextWithoutShadow(MatrixStack matrices, TextRenderer textRenderer, Text text, int x, int y, int color) {
        textRenderer.draw(matrices, text, x, (float)y, color);
    }
}
