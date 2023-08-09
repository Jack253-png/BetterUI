package com.mcreater.genshinui;

import com.mcreater.genshinui.config.Configuration;
import com.mcreater.genshinui.elements.GenshinEntitiesRenderer;
import com.mcreater.genshinui.render.InternalFonts;
import com.mcreater.genshinui.screens.widget.GenshinNarrationWidget;
import com.mcreater.genshinui.shaders.BoxBlurShader;
import com.mcreater.genshinui.shaders.MotionBlurShader;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenshinUIClient implements ClientModInitializer {

    public static final String MOD_ID = "genshinui";
    public static boolean isClientTick = false;
    public static final List<String> SPLASHES = Arrays.asList("craft_table", "pickaxe", "axe", "ender_eye", "compass", "map", "ender_pearl", "nether", "the_end", "elytra");
    public static int splash_index;
    public static GenshinNarrationWidget NARRATION_WIDGET = new GenshinNarrationWidget(MinecraftClient.getInstance());
    static {
        updateSplash(new Random().nextLong());
    }
    public static void updateSplash(long has) {
        splash_index = new Random(has).nextInt(0, SPLASHES.size());
    }
    public void onInitializeClient() {
        Configuration.readConfig();

        GenshinEntitiesRenderer.register();

        MotionBlurShader.init();
        BoxBlurShader.init();
        InternalFonts.loadFont();

        DevCommands.registerAll();
    }
}
