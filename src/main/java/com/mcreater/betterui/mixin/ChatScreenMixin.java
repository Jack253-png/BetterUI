package com.mcreater.betterui.mixin;

import com.mcreater.betterui.animation.AnimationNode;
import com.mcreater.betterui.animation.AnimationProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import static com.mcreater.betterui.config.Configuration.OPTION_CHAT_ANIMATION_MODE;
import static com.mcreater.betterui.config.Configuration.OPTION_CHAT_ANIMATION_TYPE;

@Mixin(value = {ChatScreen.class}, priority = Integer.MAX_VALUE)
public class ChatScreenMixin {
    @Shadow protected TextFieldWidget chatField;
    private AnimationNode node;

    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (node == null) node = new AnimationNode(0, 1000, chatField.getHeight(), -chatField.getHeight());
        if (node.isStopped()) node.setStopped(false);
        this.chatField.y = MinecraftClient.getInstance().getWindow().getHeight() - 12 + genOffset();
    }

    @Inject(at = @At("HEAD"), method = "removed")
    public void onRemoved(CallbackInfo ci) {
        node.setStopped(true);
        node.reset();
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;fill(Lnet/minecraft/client/util/math/MatrixStack;IIIII)V"), method = "render")
    public void modifyFillArg(Args args) {
        int y1 = args.get(2);
        int y2 = args.get(4);

        int offset = genOffset();

        args.set(2, y1 + offset);
        args.set(4, y2 + offset);
    }

    private int genOffset() {
        return AnimationProvider.generateInteger(node, OPTION_CHAT_ANIMATION_TYPE.getValue(), OPTION_CHAT_ANIMATION_MODE.getValue());
    }
}
