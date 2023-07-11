package com.mcreater.genshinui.mixin;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationProvider;
import com.mcreater.genshinui.mixin.interfaces.PressableWidgetInvoker;
import com.mcreater.genshinui.screens.ScreenHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(value = {ClickableWidget.class}, priority = Integer.MAX_VALUE)
public abstract class ClickableWidgetMixin extends DrawableHelper {
    @Shadow protected abstract int getYImage(boolean hovered);

    @Shadow protected float alpha;

    @Shadow public abstract boolean isHovered();

    @Shadow protected int width;

    @Shadow protected int height;

    @Shadow public abstract Text getMessage();

    @Shadow public int x;

    @Shadow public int y;

    @Shadow public boolean active;

    @Shadow protected abstract void renderBackground(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY);

    @Shadow public abstract void render(MatrixStack matrices, int mouseX, int mouseY, float delta);

    private final AnimatedValue opacity = new AnimatedValue(155, 155, 100, a -> AnimationProvider.generate(a, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.CIRCULAR));
    private boolean clicked = false;

    @Inject(at = @At("HEAD"), method = "onClick")
    public void onClick(CallbackInfo ci) {
        clicked = true;
    }

    @Inject(at = @At("HEAD"), method = "onRelease")
    public void onRelease(CallbackInfo ci) {
        clicked = false;
        try {
            ((PressableWidgetInvoker) this).invokeOnPress();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(at = @At("HEAD"), method = "onDrag")
    public void onDrag(double mouseX, double mouseY, double deltaX, double deltaY, CallbackInfo ci) {
        if (!isHovered()) clicked = false;
    }

    private int getOpacity() {
        if (!this.active) return 185;
        if (!this.isHovered()) return 205;
        if (!clicked) return 225;
        return 255;
    }

    @Inject(at = @At("HEAD"), method = "renderButton", cancellable = true)
    public void onRenderButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        double value = getOpacity();
        if (opacity.getExpectedValue() != value) opacity.setExpectedValue(value);

        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        ScreenHelper.drawRoundedRect(matrices, x, y, x + width, y + height, 10, new Color(212, 213, 204, (int) opacity.getCurrentValue()).getRGB());
        int j = this.active ? 0x555962 : 0x121212;
        int centerX = this.x + this.width / 2;
        int y = this.y + (this.height - 8) / 2;
        textRenderer.draw(matrices, this.getMessage().asOrderedText(), (float)(centerX - textRenderer.getWidth(this.getMessage().asOrderedText()) / 2), (float)y, j | MathHelper.ceil(this.alpha * 255.0f) << 24);
        ci.cancel();
    }
}
