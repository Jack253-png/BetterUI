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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Vector;

public abstract class ScreenHelper extends Screen {
    private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    protected ScreenHelper(Text title) {
        super(title);
    }
    public static void fillScreen(MatrixStack matrices, int opacity) {
        fill(matrices, 0, 0, CLIENT.getWindow().getWidth(), CLIENT.getWindow().getHeight(), getBackgroundColor(opacity));
    }
    public static boolean isNight() {
        int hour = LocalDateTime.now(ZoneId.systemDefault()).getHour();
        return hour >= 18 || hour <= 6;
    }
    public static int getBackgroundColor() {
        return getBackgroundColor(255);
    }
    public static int getBackgroundColor(int opacity) {
        return isNight() ?
                new Color(50, 50, 50, opacity).getRGB() :
                new Color(253, 253, 253, opacity).getRGB();
    }
    public static int getTextColor() {
        return getTextColor(255);
    }
    public static int getTextColor(int opacity) {
        return isNight() ?
                new Color(253, 253, 253, opacity).getRGB() :
                new Color(142, 149, 158, opacity).getRGB();
    }
    public static int getNarrationColor() {
        return getNarrationColor(255);
    }
    public static int getNarrationColor(int opacity) {
        return isNight() ?
                new Color(155, 155, 155, opacity).getRGB() :
                new Color(226, 226, 226, opacity).getRGB();
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
        drawTexture(matrix, LogoProvider.PICKAXE, 0.7F, xStart, y, 16, 16, a.get(0)); // 稿子 (挖矿)
        drawTexture(matrix, LogoProvider.AXE, 0.7F, xStart + 16, y, 16, 16, a.get(1)); // 斧头 (打怪（划掉）伐木)
        drawTexture(matrix, LogoProvider.SWORD, 0.7F, xStart + 16*2, y, 16, 16, a.get(2)); // 剑 (PVP)
        drawTexture(matrix, LogoProvider.COMPASS, 0.7F, xStart + 16*3, y, 16, 16, a.get(3)); // 指南针 (冒险)
        drawTexture(matrix, LogoProvider.BOW, 0.7F, xStart + 16*4, y, 16, 16, a.get(4)); // 弓 (boss空战)
        drawTexture(matrix, LogoProvider.ENDER_EYE, 0.7F, xStart + 16*5, y, 16, 16, a.get(5)); // 末影之眼 (深渊（)
        drawTexture(matrix, LogoProvider.NETHER_STAR, 0.7F, xStart + 16*6, y, 16, 16, a.get(6)); // 下界之星 (原石（误)
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
