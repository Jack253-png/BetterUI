package com.mcreater.betterui.mixin;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {GameMenuScreen.class}, priority = Integer.MAX_VALUE)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    public void onInit(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 102, this.height / 4 + 148 + -16, 204, 20, new LiteralText("Switch world"), button -> {
            System.out.println("button pressed!");
        }));
    }
}
