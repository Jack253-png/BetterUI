package com.mcreater.betterui.config.option;

import org.jetbrains.annotations.NotNull;

public class IntegerConfigOption extends AbstractConfigOption<Integer> {
    public IntegerConfigOption(@NotNull String key, @NotNull Integer defaultValue) {
        super(key, defaultValue);
    }
}
