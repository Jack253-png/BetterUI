package com.mcreater.genshinui.screens.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import static com.mcreater.genshinui.render.Icons.renderOKIcon;

public class GenshinNormalButtonWidget extends ButtonWidget {
    public GenshinNormalButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    public GenshinNormalButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress, TooltipSupplier tooltipSupplier) {
        super(x, y, width, height, message, onPress, tooltipSupplier);
    }

    public void renderIcon(MatrixStack stack) {
        renderOKIcon(stack, x, y);
    }

    protected void renderBackground(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
//        super.renderBackground(matrices, client, mouseX, mouseY);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        renderIcon(matrices);
    }
}
