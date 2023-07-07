package com.mcreater.genshinui.mixin;

import com.mcreater.genshinui.animation.AnimationNode;
import com.mcreater.genshinui.animation.AnimationProvider;
import com.mcreater.genshinui.screens.ScreenHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mcreater.genshinui.render.InternalFonts.STANDARD;
import static com.mcreater.genshinui.screens.ScreenHelper.*;

@Mixin(value = {DownloadingTerrainScreen.class}, priority = Integer.MAX_VALUE)
public class DownloadingTerrainScreenMixin extends Screen {
    private final MinecraftClient CLIENT = MinecraftClient.getInstance();
    private static final Text TEXT = new TranslatableText("multiplayer.downloadingTerrain");
    private AnimationNode node;
    private AnimationNode fakeProgress;

    public void removed() {
        node = null;
        fakeProgress = null;
    }

    protected DownloadingTerrainScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (node == null) node = new AnimationNode(0, 3000, 0, 255);
        if (fakeProgress == null) fakeProgress = new AnimationNode(0, 1000, 0, 1);

        fillScreen(matrices, AnimationProvider.generateInteger(node, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.EXPONENTIAL));
        int y = height - 20;
        int mix_start = width / 2 - 16 * 7 / 2;
        int mix_end = width / 2 + 16 * 7 / 2;

        ScreenHelper.draw7ElementsBase(matrices, mix_start, y);
        ScreenHelper.draw7Elements(matrices, mix_start, y, AnimationProvider.generateInteger(fakeProgress, AnimationProvider.AnimationType.EASE_OUT, AnimationProvider.AnimationMode.EXPONENTIAL));
        fill(matrices, 0, y, mix_start - 15, y + 1, getNarrationColor());
        fill(matrices, mix_end + 15, y, width, y + 1, getNarrationColor());

        drawCenteredTextWithoutShadow(
                matrices,
                CLIENT.textRenderer,
                TEXT instanceof MutableText ? ((MutableText) TEXT).fillStyle(Style.EMPTY.withFont(STANDARD)) : TEXT,
                width / 2,
                height - 20 - 1 - 30,
                getTextColor()
        );
        ci.cancel();
    }
}
