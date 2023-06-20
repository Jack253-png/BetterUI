package com.mcreater.betterui.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

import static com.mcreater.betterui.config.Configuration.OPTION_ENABLE_CHAT_ANIMATION_INTRO;
import static com.mcreater.betterui.config.Configuration.OPTION_ENABLE_CHAT_ANIMATION_OUTRO;

class ConfigScreenFactoryImpl implements ConfigScreenFactory<Screen> {
    public Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("ui.config.title"))
                .setSavingRunnable(Configuration::writeConfig);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        builder.getOrCreateCategory(new TranslatableText("ui.config.chat.title"))
                .addEntry(
                        entryBuilder.startBooleanToggle(
                                new TranslatableText("ui.config.chat.enable_animation_intro.text"),
                                OPTION_ENABLE_CHAT_ANIMATION_INTRO.getValue()
                        )
                                .setSaveConsumer(OPTION_ENABLE_CHAT_ANIMATION_INTRO::setValue)
                                .setDefaultValue(OPTION_ENABLE_CHAT_ANIMATION_INTRO.getDefaultValue())
                                .build()
                )
                .addEntry(
                        entryBuilder.startBooleanToggle(
                                        new TranslatableText("ui.config.chat.enable_animation_outro.text"),
                                        OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getValue()
                                )
                                .setSaveConsumer(OPTION_ENABLE_CHAT_ANIMATION_OUTRO::setValue)
                                .setDefaultValue(OPTION_ENABLE_CHAT_ANIMATION_OUTRO.getDefaultValue())
                                .build()
                );


        return builder.setParentScreen(parent).setTransparentBackground(true).build();
    }
}
