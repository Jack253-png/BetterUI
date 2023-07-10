package com.mcreater.genshinui.screens;

import com.mcreater.genshinui.render.LogoProvider;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.Math.PI;

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
        int min = LocalDateTime.now(ZoneId.systemDefault()).getMinute();
        double mixed = hour + min / 60D;
        return mixed >= 17.5 || mixed <= 6.5;
    }
    public static int getBackgroundColor() {
        return getBackgroundColor(255);
    }
    public static int getBackgroundColor(int opacity) {
        return isNight() ?
                new Color(17, 18, 25, opacity).getRGB() :
                new Color(253, 253, 253, opacity).getRGB();
    }
    public static int getTextColor() {
        return getTextColor(255);
    }
    public static int getTextColor(int opacity) {
        return isNight() ?
                new Color(229, 200, 137, opacity).getRGB() :
//                new Color(235, 235, 235, opacity).getRGB() :
                new Color(142, 149, 158, opacity).getRGB();
    }
    public static int getNarrationColor() {
        return getNarrationColor(255);
    }
    public static int getNarrationColor(int opacity) {
        return isNight() ?
//                new Color(24, 25, 32, opacity).getRGB() :
                new Color(45, 45, 45, opacity).getRGB() :
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

    private static void fill(Matrix4f matrix, double x1, double y1, double x2, double y2, int color) {
        double i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }
        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float g = (float)(color >> 16 & 0xFF) / 255.0f;
        float h = (float)(color >> 8 & 0xFF) / 255.0f;
        float j = (float)(color & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, (float) x1, (float) y2, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float) x2, (float) y2, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float) x2, (float) y1, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, (float) x1, (float) y1, 0.0f).color(g, h, j, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public enum Side {
        TOP_LEFT(a -> a / 4 * 2, a -> a / 4 * 3, () -> 0x66000000),
        TOP_RIGHT(a -> a / 4 * 3, a -> a, () -> 0x66ffff00),
        BOTTOM_LEFT(a -> a / 4, a -> a / 4 * 2, () -> 0x66ffffff),
        BOTTOM_RIGHT(a -> 0, a -> a / 4, () -> 0x66ff00ff);
        public final Function<Integer, Integer> getStart;
        public final Function<Integer, Integer> getEnd;
        public final Supplier<Integer> devColor;
        Side(Function<Integer, Integer> getStart, Function<Integer, Integer> getEnd, Supplier<Integer> devColor) {
            this.getStart = getStart;
            this.getEnd = getEnd;
            this.devColor = devColor;
        }
    }

    public static void drawRoundedRect(MatrixStack matrix, int x1, int y1, int x2, int y2, double r, int color) {
        int width = Math.abs(x1 - x2);
        int height = Math.abs(y1 - y2);
        if (r * 2 > width || r * 2 > height) r = Math.max(width, height) / 2D;

        fill(matrix, (int) (x1 + r), (int) (y1 + r), (int) (x2 - r), (int) (y2 - r), color);
        fill(matrix, x1, (int) (y1 + r), (int) (x1 + r), (int) (y2 - r), color);
        fill(matrix, (int) (x2 - r), (int) (y1 + r), x2, (int) (y2 - r), color);
        fill(matrix, (int) (x1 + r), y1, (int) (x2 - r), (int) (y1 + r), color);
        fill(matrix, (int) (x1 + r), (int) (y2 - r), (int) (x2 - r), y2, color);

        drawCircle(matrix, (int) (x1 + r), (int) (y1 + r), r, color, Side.TOP_LEFT);
        drawCircle(matrix, (int) (x2 - r), (int) (y1 + r), r, color, Side.TOP_RIGHT);
        drawCircle(matrix, (int) (x1 + r), (int) (y2 - r), r, color, Side.BOTTOM_LEFT);
        drawCircle(matrix, (int) (x2 - r), (int) (y2 - r), r, color, Side.BOTTOM_RIGHT);
    }

    private static void fill(Matrix4f matrix, float x1, float y1, float x2, float y2, int color) {
        float i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }
        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float g = (float)(color >> 16 & 0xFF) / 255.0f;
        float h = (float)(color >> 8 & 0xFF) / 255.0f;
        float j = (float)(color & 0xFF) / 255.0f;
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(matrix, x1, y2, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, x2, y2, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, x2, y1, 0.0f).color(g, h, j, f).next();
        bufferBuilder.vertex(matrix, x1, y1, 0.0f).color(g, h, j, f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawCircle(MatrixStack matrix, int x, int y, double radius, int color, Side side) {
        drawCircle(matrix, x, y, radius, color, side, 100);
    }

    public static void drawCircle(MatrixStack matrix, int x, int y, double radius, int color, Side side, int rad) {
        for (int i = side.getStart.apply(rad); i < side.getEnd.apply(rad); i++) {
            double xtemp = (Math.cos(2 * PI * i / rad)) * radius + x;
            double ytemp = (Math.sin(2 * PI * i / rad)) * radius + y;
            double ytemp2 = (Math.sin(2 * PI * (i + 1) / rad)) * radius + y;
            fill(matrix.peek().getPositionMatrix(), xtemp, ytemp, x, ytemp2, color);
        }
    }
}
