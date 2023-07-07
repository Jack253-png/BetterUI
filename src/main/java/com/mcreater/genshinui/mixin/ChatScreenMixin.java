package com.mcreater.genshinui.mixin;

import com.mcreater.genshinui.animation.AnimationNode;
import com.mcreater.genshinui.animation.AnimationProvider;
import com.mcreater.genshinui.screens.EmptyScreen;
import com.mcreater.genshinui.screens.IgnoredScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Optional;
import java.util.function.Function;

import static com.mcreater.genshinui.config.Configuration.*;
import static com.mcreater.genshinui.util.SafeValue.safeBoolean;
import static com.mojang.blaze3d.systems.RenderSystem.recordRenderCall;

@Mixin(value = {ChatScreen.class}, priority = Integer.MAX_VALUE)
public abstract class ChatScreenMixin extends Screen {
    @Shadow protected TextFieldWidget chatField;

    protected ChatScreenMixin(Text title) {
        super(title);
    }

    @Shadow public abstract void render(MatrixStack matrices, int mouseX, int mouseY, float delta);

    private AnimationNode node;
    private Integer chatFieldPositionY = null;

    @Inject(at = @At("HEAD"), method = "render")
    public void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (safeBoolean(OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING.getValue())) return;

        if (node == null) {
            node = new AnimationNode(0, OPTION_CHAT_SCREEN_ANIMATION_LENGTH.getValue(), chatField.getHeight(), -chatField.getHeight());
            node.setOnAnimation(animationNode -> {
                if (animationNode.getAll() == animationNode.getIndex() && node.isBacked()) {
                    recordRenderCall(() -> {
                        Optional<IgnoredScreen> anno = Optional.ofNullable(MinecraftClient.getInstance().currentScreen)
                                .map(Object::getClass)
                                .map((Function<Class<?>, IgnoredScreen>) aClass -> aClass.getAnnotation(IgnoredScreen.class));
                        if (anno.isEmpty()) {
                            MinecraftClient.getInstance().setScreen(new EmptyScreen(Text.of("")));
                            MinecraftClient.getInstance().setScreen(null);
                            node = null;
                            chatFieldPositionY = null;
                        }
                    });
                }
            });
        }
        if (chatFieldPositionY == null) chatFieldPositionY = chatField.y;
        this.chatField.y = chatFieldPositionY + genOffset();
    }

    @Inject(at = @At("HEAD"), method = "removed")
    public void onRemoved(CallbackInfo ci) {
        if (safeBoolean(OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING.getValue())) return;

        node.back();
    }

    @ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;fill(Lnet/minecraft/client/util/math/MatrixStack;IIIII)V"), method = "render")
    public void modifyFillArg(Args args) {
        if (safeBoolean(OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING.getValue())) return;

        int y1 = args.get(2);
        int y2 = args.get(4);

        int offset = genOffset();

        args.set(2, y1 + offset);
        args.set(4, y2 + offset);
    }

    private int genOffset() {
        if (OPTION_ENABLE_CHAT_SCREEN_ANIMATION.getValue()) {
            return AnimationProvider.generateInteger(node, OPTION_CHAT_FIELD_ANIMATION_TYPE.getValue(), OPTION_CHAT_FIELD_ANIMATION_MODE.getValue());
        }
        return 0;
    }
}
