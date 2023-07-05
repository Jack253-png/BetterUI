package com.mcreater.betterui.mixin;

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

import static com.mcreater.betterui.render.InternalFonts.STANDARD;
import static com.mcreater.betterui.screens.ScreenHelper.*;

@Mixin(value = {DownloadingTerrainScreen.class}, priority = Integer.MAX_VALUE)
public class DownloadingTerrainScreenMixin extends Screen {
    private final MinecraftClient CLIENT = MinecraftClient.getInstance();
    private static final Text TEXT = new TranslatableText("multiplayer.downloadingTerrain");

    protected DownloadingTerrainScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        fillScreen(matrices, 255);
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
