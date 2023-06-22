package com.mcreater.betterui.config;

import com.mcreater.betterui.animation.AnimationProvider;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Optional;

import static com.mcreater.betterui.config.Configuration.*;
import static com.mcreater.betterui.util.SafeValue.safeBoolean;

class ConfigScreenFactoryImpl implements ConfigScreenFactory<Screen> {
    public Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("ui.config.title"))
                .setSavingRunnable(Configuration::writeConfig);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        builder.getOrCreateCategory(new TranslatableText("ui.config.main.title"))
                        .addEntry(
                                entryBuilder.startIntSlider(
                                        new TranslatableText("ui.config.main.animation_interval.text"),
                                        OPTION_ANIMATION_INTERVAL.getValue(),
                                        1, 100
                                )
                                        .setSaveConsumer(OPTION_ANIMATION_INTERVAL::setValue)
                                        .setDefaultValue(OPTION_ANIMATION_INTERVAL::getDefaultValue)
                                        .build()
                        );
        builder.getOrCreateCategory(new TranslatableText("ui.config.chat.title"))
                .addEntry(
                        entryBuilder.startBooleanToggle(
                                new TranslatableText("ui.config.chat.enable_animation_intro.text"),
                                OPTION_ENABLE_CHAT_ANIMATION_INTRO.getValue()
                                )
                                .setSaveConsumer(OPTION_ENABLE_CHAT_ANIMATION_INTRO::setValue)
                                .setDefaultValue(OPTION_ENABLE_CHAT_ANIMATION_INTRO::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startBooleanToggle(
                                        new TranslatableText("ui.config.chat.enable_animation_outro.text"),
                                        OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getValue()
                                )
                                .setSaveConsumer(OPTION_ENABLE_CHAT_ANIMATION_OUTRO::setValue)
                                .setDefaultValue(OPTION_ENABLE_CHAT_ANIMATION_OUTRO::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startBooleanToggle(
                                        new TranslatableText("ui.config.chat.enable_animation_vanilla.text"),
                                        OPTION_ENABLE_CHAT_ANIMATION_VANILLA.getValue()
                                )
                                .setSaveConsumer(OPTION_ENABLE_CHAT_ANIMATION_VANILLA::setValue)
                                .setDefaultValue(OPTION_ENABLE_CHAT_ANIMATION_VANILLA::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startBooleanToggle(
                                        new TranslatableText("ui.config.chat.use_vanilla"),
                                        OPTION_ENABLE_CHAT_VANILLA_RENDERING.getValue()
                                )
                                .setTooltipSupplier(aBoolean -> {
                                    if (safeBoolean(aBoolean)) return Optional.of(new Text[]{new TranslatableText("ui.config.chat.use_vanilla.desc.enable")});
                                    else return Optional.of(new Text[]{new TranslatableText("ui.config.chat.use_vanilla.desc.disable")});
                                })
                                .setSaveConsumer(OPTION_ENABLE_CHAT_VANILLA_RENDERING::setValue)
                                .setDefaultValue(OPTION_ENABLE_CHAT_VANILLA_RENDERING::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startEnumSelector(
                                new TranslatableText("ui.config.chat.animation_type.text"),
                                AnimationProvider.AnimationType.class,
                                OPTION_CHAT_ANIMATION_TYPE.getValue()
                                )
                                .setSaveConsumer(OPTION_CHAT_ANIMATION_TYPE::setValue)
                                .setDefaultValue(OPTION_CHAT_ANIMATION_TYPE::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startEnumSelector(
                                        new TranslatableText("ui.config.chat.animation_mode.text"),
                                        AnimationProvider.AnimationMode.class,
                                        OPTION_CHAT_ANIMATION_MODE.getValue()
                                )
                                .setSaveConsumer(OPTION_CHAT_ANIMATION_MODE::setValue)
                                .setDefaultValue(OPTION_CHAT_ANIMATION_MODE::getDefaultValue)
                                .build()
                );

        return builder.setParentScreen(parent).setTransparentBackground(true).build();
    }
}
