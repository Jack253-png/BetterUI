package com.mcreater.genshinui.command;

import com.mcreater.genshinui.screens.widget.GenshinNarrationWidget;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.LiteralText;

import static com.mcreater.genshinui.GenshinUIClient.NARRATION_WIDGET;

public class DevNarrationDebugScreenCommand implements Command<FabricClientCommandSource> {
    public int run(CommandContext<FabricClientCommandSource> context) {
        NARRATION_WIDGET.pushNarration(
                new GenshinNarrationWidget.Narration(
                        new LiteralText("欸嘿~"),
                        GenshinNarrationWidget.NarrationCharacter.PAIMON
                )
        );
        context.getSource().sendFeedback(new LiteralText("NARRATION DEBUG"));
        return 0;
    }
}
