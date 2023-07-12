package com.mcreater.genshinui;

import com.mcreater.genshinui.command.DevNarrationClearCommand;
import com.mcreater.genshinui.command.DevNarrationDebugScreenCommand;
import com.mcreater.genshinui.command.DevTimeSelectionScreenCommand;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class DevCommands {
    public static void registerAll() {
        ClientCommandManager.DISPATCHER.register(
                literal("genshinui_dev")
                        .then(literal("timeselection").executes(new DevTimeSelectionScreenCommand()))
                        .then(literal("narration_add").executes(new DevNarrationDebugScreenCommand()))
                        .then(literal("narration_clear").executes(new DevNarrationClearCommand()))
        );
    }
}
