package com.mcreater.betterui.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.terraformersmc.modmenu.config.option.BooleanConfigOption;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import static com.mcreater.betterui.BetterUIClient.MOD_ID;

public class Configuration {
    public static final File configFile = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json").toFile();
    public static final BooleanConfigOption OPTION_ENABLE_CHAT_ANIMATION = new BooleanConfigOption("enable_chat_animation", true);

    static {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                Files.writeString(configFile.toPath(), "{}", StandardOpenOption.WRITE);
            } catch (IOException e) {
                LogManager.getLogger(Configuration.class).error("failed to init file", e);
            }
        }
        else {
            if (!readConfig()) {
                try {
                    Files.delete(configFile.toPath());
                    configFile.createNewFile();
                    Files.writeString(configFile.toPath(), "{}", StandardOpenOption.WRITE);
                } catch (IOException e) {
                    LogManager.getLogger(Configuration.class).error("failed to reset file", e);
                }
            }
        }
    }

    public static boolean readConfig() {
        try {
            Map<String, Object> map = new Gson().fromJson(
                    Files.readString(configFile.toPath()),
                    TypeToken.getParameterized(Map.class, String.class, Object.class).getType()
            );
            OPTION_ENABLE_CHAT_ANIMATION.setValue((boolean) map.get("enable_chat_animation"));
            return true;
        }
        catch (Exception e) {
            LogManager.getLogger(Configuration.class).error("failed to read file", e);
            return false;
        }
    }

    public static void writeConfig() {
        Map<String, Object> maps = new HashMap<>();

        maps.put("enable_chat_animation", OPTION_ENABLE_CHAT_ANIMATION.getValue());

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
