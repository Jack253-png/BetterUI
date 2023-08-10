package com.mcreater.genshinui.command;

import com.mcreater.genshinui.network.ClientNetworkRegistry;
import com.mcreater.genshinui.network.NbtMessage;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class DevEmojiC2SDebugCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) {
        ClientNetworkRegistry.sendMessage(new NbtMessage("test"));
        return 0;
    }
}
