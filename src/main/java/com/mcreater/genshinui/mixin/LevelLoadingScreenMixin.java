package com.mcreater.genshinui.mixin;

import com.mcreater.genshinui.animation.AnimatedValue;
import com.mcreater.genshinui.animation.AnimationNode;
import com.mcreater.genshinui.animation.AnimationProvider;
import com.mcreater.genshinui.screens.ScreenHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mcreater.genshinui.render.InternalFonts.STANDARD;
import static com.mcreater.genshinui.render.InternalFonts.TITLE;
import static com.mcreater.genshinui.screens.ScreenHelper.*;

@Mixin(value = {LevelLoadingScreen.class}, priority = Integer.MAX_VALUE)
public class LevelLoadingScreenMixin extends Screen {
    @Shadow @Final private WorldGenerationProgressTracker progressProvider;
    private final MinecraftClient CLIENT = MinecraftClient.getInstance();
    private AnimatedValue value;
    private AnimationNode fadeNode;
    private static final List<String> SPLASHES = Arrays.asList("craft_table", "pickaxe", "axe", "ender_eye", "compass", "map", "ender_pearl", "nether", "the_end", "elytra");
    private static int splash_index = 0;

    protected LevelLoadingScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "<init>")
    private static void onInit(WorldGenerationProgressTracker progressProvider, CallbackInfo ci) {
        splash_index = new Random(progressProvider.hashCode()).nextInt(0, SPLASHES.size());
    }

    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        fillScreen(matrices, 255);

        if (value == null) {
            value = new AnimatedValue(0, 0, 200, n -> AnimationProvider.generate(n, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.EXPONENTIAL));
        }

        initFadeNode();

        if ((double) progressProvider.getProgressPercentage() / 100 != value.getExpectedValue()) {
            double excepted = (double) progressProvider.getProgressPercentage() / 100;
            value.setExpectedValue(excepted);
        }
        double progress = value == null ? 0 : value.getCurrentValue();
        if (progressProvider.getProgressPercentage() >= 100) {
            fadeNode.back();
        }
        renderInternal(matrices, height - 20, progress);
        ci.cancel();
    }
    private void initFadeNode() {
        if (fadeNode == null) fadeNode = new AnimationNode(0, 1000, 0, 255);
    }

    @Inject(at = @At("RETURN"), method = "removed")
    public void onRemove(CallbackInfo ci) {
        value = null;
        fadeNode = null;
    }
    private int getOpacity() {
        initFadeNode();
        return AnimationProvider.generateInteger(fadeNode, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.EXPONENTIAL);
    }

    public void renderInternal(MatrixStack matrix, int y, double progress) {
        RenderSystem.enableBlend();

        int mix_start = width / 2 - 16 * 7 / 2;
        int mix_end = width / 2 + 16 * 7 / 2;

        ScreenHelper.draw7ElementsBase(matrix, mix_start, y);
        ScreenHelper.draw7Elements(matrix, mix_start, y, progress);

        fill(matrix, 0, y, mix_start - 15, y + 1, getNarrationColor());
        fill(matrix, mix_end + 15, y, width, y + 1, getNarrationColor());

        drawTextWithoutShadow(
                matrix,
                CLIENT.textRenderer,
                new TranslatableText("ui.splash.core.world_load", Math.min((int) (progress * 100), 100) + "%").fillStyle(Style.EMPTY.withFont(STANDARD)),
                5,
                y - 10,
                getTextColor()
        );
        drawCenteredTextWithoutShadow(
                matrix,
                CLIENT.textRenderer,
                new TranslatableText("ui.tech.warn").fillStyle(Style.EMPTY.withFont(STANDARD)),
                width / 2,
                height - 10,
                getTextColor()
        );
        if (getOpacity() >= 5) {
            drawCenteredTextWithoutShadow(
                    matrix,
                    CLIENT.textRenderer,
                    new TranslatableText(String.format("ui.splash.%s.title", SPLASHES.get(splash_index))).fillStyle(Style.EMPTY.withFont(TITLE)),
                    width / 2,
                    y - 1 - 30 - 10,
                    getTextColor(getOpacity())
            );
            drawCenteredTextWithoutShadow(
                    matrix,
                    CLIENT.textRenderer,
                    new TranslatableText(String.format("ui.splash.%s.desc", SPLASHES.get(splash_index))).fillStyle(Style.EMPTY.withFont(STANDARD)),
                    width / 2,
                    y - 1 - 30,
                    getTextColor(getOpacity())
            );
        }
        RenderSystem.disableBlend();
        matrix.pop();
    }
}
