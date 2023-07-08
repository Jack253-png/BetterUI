package com.mcreater.genshinui;

import com.mcreater.genshinui.config.Configuration;
import com.mcreater.genshinui.screens.TimeSelectionScreen;
import com.mcreater.genshinui.shaders.MotionBlurShader;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mcreater.genshinui.render.InternalFonts.STANDARD;
import static com.mcreater.genshinui.render.InternalFonts.TITLE;
import static com.mojang.blaze3d.systems.RenderSystem.recordRenderCall;
import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public class GenshinUIClient implements ClientModInitializer {
    public static final String MOD_ID = "genshinui";
    public static boolean isClientTick = false;
    public static final List<String> SPLASHES = Arrays.asList("craft_table", "pickaxe", "axe", "ender_eye", "compass", "map", "ender_pearl", "nether", "the_end", "elytra");
    public static int splash_index;
    static {
        updateSplash(new Random().nextLong());
    }
    public static void updateSplash(long has) {
        splash_index = new Random(has).nextInt(0, SPLASHES.size());
    }
    public void onInitializeClient() {
        Configuration.readConfig();
        MotionBlurShader.init();
        TITLE.getNamespace();
        STANDARD.getNamespace();

        ClientCommandManager.DISPATCHER.register(
                literal("genshinui_dev")
                        .then(literal("timeselection")
                                .executes(
                                        context -> {
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
                                        })
                        )
        );
    }
}
