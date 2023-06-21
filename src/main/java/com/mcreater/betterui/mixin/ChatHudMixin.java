package com.mcreater.betterui.mixin;

import com.google.common.collect.Queues;
import com.mcreater.betterui.animation.AnimationGenerator;
import com.mcreater.betterui.animation.AnimationNode;
import com.mcreater.betterui.patches.ChatHeadsPatch;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.compress.utils.Lists;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

import static com.mcreater.betterui.config.Configuration.*;

@Mixin(value = {ChatHud.class}, priority = Integer.MAX_VALUE)
public abstract class ChatHudMixin extends DrawableHelper {
    @Shadow
    protected abstract boolean isChatHidden();
    @Shadow
    protected abstract void processMessageQueue();
    @Shadow
    public abstract int getVisibleLineCount();
    @Shadow
    protected abstract boolean isChatFocused();
    @Shadow
    public abstract double getChatScale();
    @Shadow
    public abstract int getWidth();
    @Shadow
    private static double getMessageOpacityMultiplier(int age) {
        return 0;
    }

    @Shadow
    @Final
    @Mutable
    private final List<ChatHudLine<OrderedText>> visibleMessages = Lists.newArrayList();
    @Shadow
    @Final
    @Mutable
    private final MinecraftClient client = MinecraftClient.getInstance();
    @Shadow
    private int scrolledLines;
    @Shadow
    @Final
    private final Deque<Text> messageQueue = Queues.newArrayDeque();
    @Shadow
    private boolean hasUnreadNewMessages;

    private final Map<ChatHudLine<OrderedText>, AnimationNode> animationMap = new HashMap<>();
    private final List<ChatHudLine<OrderedText>> animatedVisibleMessages = Lists.newArrayList();

    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    public void onRender(MatrixStack matrices, int tickDelta, CallbackInfo ci) {
        if (OPTION_ENABLE_CHAT_VANILLA_RENDERING.getValue()) return;

        if (!this.isChatHidden()) {
            this.processMessageQueue();
            int visibleLineCount = this.getVisibleLineCount();
            int visibleMessagesCount = this.visibleMessages.size();
            if (visibleMessagesCount > 0) {
                boolean chatFocused = this.isChatFocused();

                float chatScale = (float)this.getChatScale();
                int scaledWidth = MathHelper.ceil((float)this.getWidth() / chatScale);
                matrices.push();
                matrices.translate(4.0, 8.0, 0.0);
                matrices.scale(chatScale, chatScale, 1.0F);
                double chatOpacity = this.client.options.chatOpacity * 0.8999999761581421 + 0.10000000149011612;
                double textBackgroundOpacity = this.client.options.textBackgroundOpacity;
                double lineSpacing = 9.0 * (this.client.options.chatLineSpacing + 1.0);
                double lineSpacing2 = -8.0 * (this.client.options.chatLineSpacing + 1.0) + 4.0 * this.client.options.chatLineSpacing;
                int linesCount = 0;
                int index;
                int timeTick;
                int opacityText;
                int opacityBg;
                for (index = 0; index + this.scrolledLines < this.visibleMessages.size() && index < visibleLineCount; ++index) {
                    ChatHudLine<OrderedText> chatHudLine = this.visibleMessages.get(index + this.scrolledLines);
                    if (chatHudLine != null) {
                        timeTick = tickDelta - chatHudLine.getCreationTick();
                        if (timeTick < 200 || chatFocused) {
                            double opacity = chatFocused ? 1.0 : getMessageOpacityMultiplier(timeTick);
                            opacityText = (int)(255.0 * opacity * chatOpacity);
                            opacityBg = (int)(255.0 * opacity * textBackgroundOpacity);
                            ++linesCount;
                            if (opacityText > 3) {
                                if (!animationMap.containsKey(chatHudLine) && !animatedVisibleMessages.contains(chatHudLine)) {
                                    animationMap.put(chatHudLine, new AnimationNode(OPTION_ENABLE_CHAT_ANIMATION_INTRO.getValue() ? 0 : 1000, 1000, -scaledWidth, scaledWidth));
                                    animatedVisibleMessages.add(chatHudLine);
                                }

                                AnimationNode node = animationMap.get(chatHudLine);
                                int lineBase = node != null ? (int) AnimationGenerator.SINUSOIDAL_EASEOUT.applyAsDouble(node) : 0;

                                if (node != null && opacity < 1.0 && !node.isBacked() && OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getValue()) node.back();
                                if (!OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getValue()) {
                                    opacityText = (int)(255.0 * chatOpacity);
                                    opacityBg = (int)(255.0 * textBackgroundOpacity);
                                }

                                double s = (double)(-index) * lineSpacing;
                                matrices.push();
                                matrices.translate(0.0, 0.0, 50.0);
                                fill(
                                        matrices, 
                                        -4 + lineBase, 
                                        (int)(s - lineSpacing), 
                                        scaledWidth + 4 + lineBase, 
                                        (int)s, 
                                        opacityBg << 24
                                );
                                RenderSystem.enableBlend();
                                matrices.translate(0.0, 0.0, 50.0);

                                float newX = ChatHeadsPatch.modifyTextRenderArg2(matrices,
                                        chatHudLine.getText(),
                                        0.0F + lineBase,
                                        (float)((int)(s + lineSpacing2)),
                                        16777215 + (opacityText << 24),
                                        lineBase,
                                        !OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getValue()
                                );
                                ChatHeadsPatch.callBeforeRenderingText(matrices, tickDelta, ci, lineBase);
                                this.client.textRenderer.drawWithShadow(
                                        matrices, 
                                        chatHudLine.getText(),
                                        newX + lineBase,
                                        (float)((int)(s + lineSpacing2)),
                                        16777215 + (opacityText << 24)
                                );
                                RenderSystem.disableBlend();
                                matrices.pop();
                            }
                            else animationMap.remove(chatHudLine);
                        }
                        else animationMap.remove(chatHudLine);
                    }
                }

                int textBgOpacityTemp2;
                if (!this.messageQueue.isEmpty()) {
                    index = (int)(128.0 * chatOpacity);
                    textBgOpacityTemp2 = (int)(255.0 * textBackgroundOpacity);
                    matrices.push();
                    matrices.translate(0.0, 0.0, 50.0);
                    fill(matrices, -2, 0, scaledWidth + 4, 9, textBgOpacityTemp2 << 24);
                    RenderSystem.enableBlend();
                    matrices.translate(0.0, 0.0, 50.0);
                    this.client.textRenderer.drawWithShadow(matrices, new TranslatableText("chat.queue", this.messageQueue.size()), 0.0F, 1.0F, 16777215 + (index << 24));
                    matrices.pop();
                    RenderSystem.disableBlend();
                }

                if (chatFocused) {
                    Objects.requireNonNull(this.client.textRenderer);
                    int m2 = 9;
                    textBgOpacityTemp2 = visibleMessagesCount * m2;
                    timeTick = linesCount * m2;
                    int lineBase = this.scrolledLines * timeTick / visibleMessagesCount;
                    int lineHeight = timeTick * timeTick / textBgOpacityTemp2;
                    if (textBgOpacityTemp2 != timeTick) {
                        opacityText = lineBase > 0 ? 170 : 96;
                        opacityBg = this.hasUnreadNewMessages ? 13382451 : 3355562;
                        matrices.translate(-4.0, 0.0, 0.0);
                        fill(matrices, 0, -lineBase, 2, -lineBase - lineHeight, opacityBg + (opacityText << 24));
                        fill(matrices, 2, -lineBase, 1, -lineBase - lineHeight, 13421772 + (opacityText << 24));
                    }
                }

                matrices.pop();
            }
        }

        ci.cancel();
    }

    @Inject(at = @At("RETURN"), method = "addMessage(Lnet/minecraft/text/Text;IIZ)V")
    public void onAddingMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ci) {
        ChatHeadsPatch.onAddingMessage(visibleMessages);
    }
}
