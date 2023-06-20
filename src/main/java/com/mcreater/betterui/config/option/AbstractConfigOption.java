package com.mcreater.betterui.config.option;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractConfigOption<T> {
    @NotNull
    private T value;
    @NotNull
    private final String key;
    @NotNull
    private final T defaultValue;

    public AbstractConfigOption(@NotNull String key, @NotNull T defaultValue) {
        this.key = key;
        this.defaultValue = this.value = defaultValue;
    }

    public void setValue(@NotNull T value) {
        this.value = value;
    }

    public @NotNull T getValue() {
        return value;
    }

    public @NotNull String getKey() {
        return key;
    }

    public @NotNull T getDefaultValue() {
        return defaultValue;
    }
}
