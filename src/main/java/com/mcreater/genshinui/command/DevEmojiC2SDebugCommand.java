package com.mcreater.genshinui.command;

import com.mcreater.genshinui.network.EmojiHandler;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class DevEmojiC2SDebugCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        EmojiHandler.sendC2S(MinecraftClient.getInstance().player);
        return 0;
    }
}
