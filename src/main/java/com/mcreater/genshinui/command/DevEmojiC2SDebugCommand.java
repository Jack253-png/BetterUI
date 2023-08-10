package com.mcreater.genshinui.command;

import com.mcreater.genshinui.network.NbtMessage;
import com.mcreater.genshinui.network.packet.c2s.GenshinAdvancedMessageC2SPacket;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class DevEmojiC2SDebugCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        ClientPlayNetworking.getSender().sendPacket(new GenshinAdvancedMessageC2SPacket(
                new NbtMessage("test")
        ));
        return 0;
    }
}
