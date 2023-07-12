package com.mcreater.genshinui.command;

import com.mcreater.genshinui.GenshinUIClient;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.LiteralText;

public class DevNarrationClearCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        GenshinUIClient.NARRATION_WIDGET.clearNarration();
        context.getSource().sendFeedback(new LiteralText("NARRATION_CLEAR"));
        return 0;
    }
}
