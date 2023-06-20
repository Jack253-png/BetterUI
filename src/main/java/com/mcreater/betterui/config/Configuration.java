package com.mcreater.betterui.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mcreater.betterui.config.option.BooleanConfigOption;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import static com.mcreater.betterui.BetterUIClient.MOD_ID;
import static com.mcreater.betterui.util.SafeValue.safeBoolean;

public class Configuration {
    public static final File configFile = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json").toFile();
    public static final BooleanConfigOption OPTION_ENABLE_CHAT_ANIMATION_INTRO = new BooleanConfigOption("enable_chat_animation_intro", true);
    public static final BooleanConfigOption OPTION_ENABLE_CHAT_ANIMATION_OUTRO = new BooleanConfigOption("enable_chat_animation_outro", true);
    public static final BooleanConfigOption OPTION_ENABLE_CHAT_ANIMATION_VANILLA = new BooleanConfigOption("enable_chat_animation_vanilla", false);

    static {
        if (!configFile.exists()) {
            writeDefault();
        }
        else {
            if (!readConfig()) writeDefault();
        }
    }

    public static boolean readConfig() {
        try {
            Map<String, Object> map = new Gson().fromJson(
                    Files.readString(configFile.toPath()),
                    TypeToken.getParameterized(Map.class, String.class, Object.class).getType()
            );
            OPTION_ENABLE_CHAT_ANIMATION_INTRO.setValue(safeBoolean(map.get(OPTION_ENABLE_CHAT_ANIMATION_INTRO.getKey())));
            OPTION_ENABLE_CHAT_ANIMATION_OUTRO.setValue(safeBoolean(map.get(OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getKey())));
            OPTION_ENABLE_CHAT_ANIMATION_VANILLA.setValue(safeBoolean(map.get(OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getKey())));
            return true;
        }
        catch (Exception e) {
            LogManager.getLogger(Configuration.class).error("failed to read file", e);
            return false;
        }
    }

    public static void writeConfig() {
        Map<String, Object> maps = new HashMap<>();

        maps.put(OPTION_ENABLE_CHAT_ANIMATION_INTRO.getKey(), OPTION_ENABLE_CHAT_ANIMATION_INTRO.getValue());
        maps.put(OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getKey(), OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getValue());
        maps.put(OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getKey(), OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getValue());

        try {
            Files.delete(configFile.toPath());
            configFile.createNewFile();
            Files.writeString(configFile.toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(maps), StandardOpenOption.WRITE);
        }
        catch (Exception e) {
            LogManager.getLogger(Configuration.class).error("failed to write file", e);
        }
    }

    public static void writeDefault() {
        Map<String, Object> maps = new HashMap<>();

        maps.put(OPTION_ENABLE_CHAT_ANIMATION_INTRO.getKey(), OPTION_ENABLE_CHAT_ANIMATION_INTRO.getDefaultValue());
        maps.put(OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getKey(), OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getDefaultValue());
        maps.put(OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getKey(), OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getDefaultValue());

        try {
            Files.delete(configFile.toPath());
            configFile.createNewFile();
            Files.writeString(configFile.toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(maps), StandardOpenOption.WRITE);
        }
        catch (Exception e) {
            LogManager.getLogger(Configuration.class).error("failed to write file", e);
        }
    }
}
