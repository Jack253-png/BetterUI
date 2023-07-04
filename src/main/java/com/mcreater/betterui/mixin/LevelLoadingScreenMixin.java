package com.mcreater.betterui.mixin;

import com.mcreater.betterui.animation.AnimationNode;
import com.mcreater.betterui.animation.AnimationProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

import static com.mcreater.betterui.render.InternalFonts.STANDARD;
import static com.mcreater.betterui.render.InternalFonts.TITLE;
import static com.mcreater.betterui.screens.ScreenHelper.*;

@Mixin(value = {LevelLoadingScreen.class}, priority = Integer.MAX_VALUE)
public class LevelLoadingScreenMixin extends Screen {
    @Shadow @Final private WorldGenerationProgressTracker progressProvider;
    private final MinecraftClient CLIENT = MinecraftClient.getInstance();
    private double excepted = 0;
    private double progress = 0;
    private AnimationNode node;
    private AnimationNode fadeNode;
    private boolean hided = false;

    protected LevelLoadingScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        fillScreen(matrices, 255);

        if (node == null) {
            excepted = 0;
            progress = 0;
            hided = false;
        }

        if (fadeNode == null) {
            fadeNode = new AnimationNode(0, 1000, 0, 255);
        }

        if ((double) progressProvider.getProgressPercentage() / 100 != excepted) {
            excepted = (double) progressProvider.getProgressPercentage() / 100;
            node = new AnimationNode(0, 200, progress, excepted - progress);
        }
        progress = node == null ? 0 : AnimationProvider.generate(node, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.EXPONENTIAL);
        if (progressProvider.getProgressPercentage() >= 100) {
            fadeNode.back();
        }
        drawProgressBar(matrices, width / 2, height - 20, progress);
        ci.cancel();
    }

    @Inject(at = @At("RETURN"), method = "removed")
    public void onRemove(CallbackInfo ci) {
        node = null;
        fadeNode = null;
        hided = false;
    }
    private int getOpacity() {
        int va = fadeNode == null ? 0 : AnimationProvider.generateInteger(fadeNode, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.EXPONENTIAL);
        if (va < 5) hided = true;
        return va;
    }

    public void drawProgressBar(MatrixStack matrix, int x, int y, double progress) {
        int barLength = width;
        int barHeight = 2;
        int x1 = x - barLength / 2;
        int y1 = y - barHeight / 2;
        int x2 = (int) (x1 + barLength * progress);
        int y2 = y + barHeight / 2;
        fill(matrix, x1, y1, x2, y2, new Color(50, 50, 50).getRGB());
        drawTextWithoutShadow(
                matrix,
                CLIENT.textRenderer,
                new LiteralText(Math.min((int) (progress * 100), 100) + "%").fillStyle(Style.EMPTY.withFont(STANDARD)),
                0,
                y1 - 10,
                new Color(100, 100, 100).getRGB()
        );
        if (!hided) {
            drawCenteredTextWithoutShadow(
                    matrix,
                    CLIENT.textRenderer,
                    new TranslatableText("ui.splash.craft_table.title").fillStyle(Style.EMPTY.withFont(TITLE)),
                    width / 2,
                    y1 - 30 - 20,
                    new Color(100, 100, 100, getOpacity()).getRGB()
            );
            drawCenteredTextWithoutShadow(
                    matrix,
                    CLIENT.textRenderer,
                    new TranslatableText("ui.splash.craft_table.desc").fillStyle(Style.EMPTY.withFont(STANDARD)),
                    width / 2,
                    y1 - 30,
                    new Color(100, 100, 100, getOpacity()).getRGB()
            );
        }
    }
}
