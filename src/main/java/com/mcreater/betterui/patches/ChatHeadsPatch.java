package com.mcreater.betterui.patches;

import com.mojang.blaze3d.systems.RenderSystem;
import dzwdz.chat_heads.ChatHeads;
import dzwdz.chat_heads.mixinterface.GuiMessageOwnerAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

public class ChatHeadsPatch {
    public static void callBeforeRenderingText(MatrixStack matrices, int tickDelta, CallbackInfo ci, int lineBase) {
        if (FabricLoader.getInstance().isModLoaded("chat_heads")) {
            try {
                if (ChatHeads.lastGuiMessage != null) {
                    PlayerListEntry owner = ((GuiMessageOwnerAccessor) ChatHeads.lastGuiMessage).chatheads$getOwner();
                    if (owner != null) {
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, ChatHeads.lastOpacity);
                        RenderSystem.setShaderTexture(0, owner.getSkinTexture());
                        DrawableHelper.drawTexture(matrices, lineBase, ChatHeads.lastY, 8, 8, 8.0F, 8.0F, 8, 8, 64, 64);
                        DrawableHelper.drawTexture(matrices, lineBase, ChatHeads.lastY, 8, 8, 40.0F, 8.0F, 8, 8, 64, 64);
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    }
                }
            } catch (Exception ignored) {

            }
        }
    }

    public static float modifyTextRenderArg2(MatrixStack poseStack, OrderedText formattedCharSequence, float x, float y, int color, int lineBase, boolean fixedColor) {
        if (FabricLoader.getInstance().isModLoaded("chat_heads")) {
            try {
                ChatHeads.lastY = (int) y;
                ChatHeads.lastOpacity = fixedColor ? 254 : (float) (((color >> 24) + 256) % 256) / 255.0F;
                return (float) ChatHeads.lastChatOffset;
            } catch (Exception ignored) {
            }
        }
        return y;
    }

    public static void onAddingMessage(List<ChatHudLine<OrderedText>> visibleMessages) {
        if (FabricLoader.getInstance().isModLoaded("chat_heads")) {
            try {
                ChatHeads.lastGuiMessage = visibleMessages.get(visibleMessages.size() - 1);
                ChatHeads.lastChatOffset = ChatHeads.getChatOffset(visibleMessages.get(visibleMessages.size() - 1));
            } catch (Exception ignored) {

            }
        }
    }
}
