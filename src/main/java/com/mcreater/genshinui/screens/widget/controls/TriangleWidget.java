package com.mcreater.genshinui.screens.widget.controls;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec2f;

public class TriangleWidget extends ShapeWidget {
    public Vec2f point1;
    public Vec2f point2;
    public Vec2f point3;
    public TriangleWidget(Vec2f point1, Vec2f point2, Vec2f point3) {
        super(0, 0, 0, 0, new LiteralText(""));
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public State getState() {
        return State.ACTIVE;
    }

    public void renderShape(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), point1.x, point1.y, 0.0F)
                .color(0.0F, 0.0F, 0.0F, 1F)
                .next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), point2.x, point2.y, 0.0F)
                .color(0.0F, 0.0F, 0.0F, 1F)
                .next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), point3.x, point3.y, 0.0F)
                .color(0.0F, 0.0F, 0.0F, 1F)
                .next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), point1.x, point1.y, 0.0F)
                .color(0.0F, 0.0F, 0.0F, 1F)
                .next();

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }
}
