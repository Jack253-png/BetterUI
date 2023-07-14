package com.mcreater.genshinui.command;

import com.mcreater.genshinui.GenshinUIClient;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.LiteralText;

public class DevNarrationPopCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        GenshinUIClient.NARRATION_WIDGET.popNarration();
        context.getSource().sendFeedback(new LiteralText("NARRATION_POP"));
        return 0;
    }
}
