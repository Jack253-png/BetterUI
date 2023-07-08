package com.mcreater.genshinui.command;

import com.mcreater.genshinui.screens.TimeSelectionScreen;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

import static com.mojang.blaze3d.systems.RenderSystem.recordRenderCall;

public class DevTimeSelectionScreenCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) {
        new Thread("GUI Thread") {
            public void run() {
                try {
                    Thread.sleep(1000);
                    recordRenderCall(() -> MinecraftClient.getInstance().setScreen(new TimeSelectionScreen(new LiteralText("TIME_SELECTION"))));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        context.getSource().sendFeedback(new LiteralText("TIME_SELECTION"));
        return 0;
    }
}
