package com.mcreater.betterui.config.option;

import org.jetbrains.annotations.NotNull;

public class EnumConfigOption<T extends Enum<T>> extends AbstractConfigOption<T> {
    public EnumConfigOption(@NotNull String key, @NotNull T defaultValue) {
        super(key, defaultValue);
    }
}
