package com.mcreater.genshinui.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class DevEmojiC2SDebugCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {

        return 0;
    }
}
