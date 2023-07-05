package com.mcreater.betterui.screens;

import com.mcreater.betterui.render.LogoProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.List;
import java.util.Vector;

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
    public static void drawTexture(MatrixStack matrix, LogoProvider logo, float opacity, int x, int y, int width, int height, double percent) {
        int imgW = logo.getSize().getLeft();
        int imgH = logo.getSize().getRight();
        int tarY = y - imgH / 2;
        matrix.translate(0.0, 0.0, 50.0);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
        RenderSystem.setShaderTexture(0, logo.getLight());
        System.out.println(width * percent);
        DrawableHelper.drawTexture(matrix, x, tarY, (int) (width * percent), height, 0.0F, 0.0F, (int) (imgW * percent), imgH, imgW, imgH);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
    public static void drawTexture(MatrixStack matrix, LogoProvider logo, float opacity, int x, int y, int width, int height) {
        drawTexture(matrix, logo, opacity, x, y, width, height, 1.0D);
    }

    public static void draw7ElementsBase(MatrixStack matrix, int xStart, int y) {
        drawTexture(matrix, LogoProvider.PICKAXE, 0.15F, xStart, y, 16, 16);
        drawTexture(matrix, LogoProvider.AXE, 0.15F, xStart + 16, y, 16, 16);
        drawTexture(matrix, LogoProvider.SWORD, 0.15F, xStart + 16*2, y, 16, 16);
        drawTexture(matrix, LogoProvider.COMPASS, 0.15F, xStart + 16*3, y, 16, 16);
        drawTexture(matrix, LogoProvider.BOW, 0.15F, xStart + 16*4, y, 16, 16);
        drawTexture(matrix, LogoProvider.ENDER_EYE, 0.15F, xStart + 16*5, y, 16, 16);
        drawTexture(matrix, LogoProvider.NETHER_STAR, 0.15F, xStart + 16*6, y, 16, 16);
    }

    public static void draw7Elements(MatrixStack matrix, int xStart, int y, double progress) {
        List<Double> a = splitProgress(progress);
        drawTexture(matrix, LogoProvider.PICKAXE, 0.5F, xStart, y, 16, 16, a.get(0));
        drawTexture(matrix, LogoProvider.AXE, 0.5F, xStart + 16, y, 16, 16, a.get(1));
        drawTexture(matrix, LogoProvider.SWORD, 0.5F, xStart + 16*2, y, 16, 16, a.get(2));
        drawTexture(matrix, LogoProvider.COMPASS, 0.5F, xStart + 16*3, y, 16, 16, a.get(3));
        drawTexture(matrix, LogoProvider.BOW, 0.5F, xStart + 16*4, y, 16, 16, a.get(4));
        drawTexture(matrix, LogoProvider.ENDER_EYE, 0.5F, xStart + 16*5, y, 16, 16, a.get(5));
        drawTexture(matrix, LogoProvider.NETHER_STAR, 0.5F, xStart + 16*6, y, 16, 16, a.get(6));
    }

    public static java.util.List<Double> splitProgress(double va) {
        List<Double> result = new Vector<>();
        double v = 1D / 7D;
        int fulled = (int) Math.floor(va / v);
        for (int ignored = 0; ignored < fulled; ignored++) result.add(1.0D);
        result.add((va / v) - fulled);
        for (int ignored2 = result.size(); ignored2 < 7; ignored2++) result.add(0.0D);
        return result;
    }
}
