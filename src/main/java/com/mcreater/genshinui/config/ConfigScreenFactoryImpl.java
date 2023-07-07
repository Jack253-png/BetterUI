package com.mcreater.genshinui.config;

import com.mcreater.genshinui.animation.AnimationProvider;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Optional;

import static com.mcreater.genshinui.config.Configuration.*;
import static com.mcreater.genshinui.util.SafeValue.safeBoolean;

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
                                        new TranslatableText("ui.config.main.motion_blur.text"),
                                        OPTION_MOTION_BLUR_FACTOR.getValue(),
                                        0, 99
                                )
                                        .setSaveConsumer(OPTION_MOTION_BLUR_FACTOR::setValue)
                                        .setDefaultValue(OPTION_MOTION_BLUR_FACTOR::getDefaultValue)
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
                                entryBuilder.startBooleanToggle(
                                                new TranslatableText("ui.config.chat.screen.vanilla"),
                                                OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING.getValue()
                                        )
                                        .setTooltipSupplier(aBoolean -> {
                                            if (safeBoolean(aBoolean)) return Optional.of(new Text[]{new TranslatableText("ui.config.chat.screen.use_vanilla.desc.enable")});
                                            else return Optional.of(new Text[]{new TranslatableText("ui.config.chat.screen.use_vanilla.desc.disable")});
                                        })
                                        .setSaveConsumer(OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING::setValue)
                                        .setDefaultValue(OPTION_ENABLE_CHAT_SCREEN_VANILLA_RENDERING::getDefaultValue)
                                        .build()
                        );
        builder.getOrCreateCategory(new TranslatableText("ui.config.chat.title"))
                .addEntry(
                        entryBuilder.startIntSlider(
                                new TranslatableText("ui.config.chat.hud.animation_length.text"),
                                OPTION_CHAT_HUD_ANIMATION_LENGTH.getValue(),
                                100, 1000
                        )
                                .setSaveConsumer(OPTION_CHAT_HUD_ANIMATION_LENGTH::setValue)
                                .setDefaultValue(OPTION_CHAT_HUD_ANIMATION_LENGTH::getDefaultValue)
                                .build()
                )
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

        builder.getOrCreateCategory(new TranslatableText("ui.config.chat.screen.title"))
                .addEntry(
                        entryBuilder.startBooleanToggle(
                                        new TranslatableText("ui.config.chat.enable_chat_screen_animation.text"),
                                        OPTION_ENABLE_CHAT_SCREEN_ANIMATION.getValue()
                                )
                                .setSaveConsumer(OPTION_ENABLE_CHAT_SCREEN_ANIMATION::setValue)
                                .setDefaultValue(OPTION_ENABLE_CHAT_SCREEN_ANIMATION::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startIntSlider(
                                        new TranslatableText("ui.config.chat.screen.animation_length.text"),
                                        OPTION_CHAT_SCREEN_ANIMATION_LENGTH.getValue(),
                                        100, 1000
                                )
                                .setSaveConsumer(OPTION_CHAT_SCREEN_ANIMATION_LENGTH::setValue)
                                .setDefaultValue(OPTION_CHAT_SCREEN_ANIMATION_LENGTH::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startEnumSelector(
                                new TranslatableText("ui.config.chat.field.animation_type.text"),
                                AnimationProvider.AnimationType.class,
                                OPTION_CHAT_FIELD_ANIMATION_TYPE.getValue()
                        )
                                .setSaveConsumer(OPTION_CHAT_FIELD_ANIMATION_TYPE::setValue)
                                .setDefaultValue(OPTION_CHAT_FIELD_ANIMATION_TYPE::getDefaultValue)
                                .build()
                )
                .addEntry(
                        entryBuilder.startEnumSelector(
                                new TranslatableText("ui.config.chat.field.animation_mode.text"),
                                AnimationProvider.AnimationMode.class,
                                OPTION_CHAT_FIELD_ANIMATION_MODE.getValue()
                        )
                                .setSaveConsumer(OPTION_CHAT_FIELD_ANIMATION_MODE::setValue)
                                .setDefaultValue(OPTION_CHAT_FIELD_ANIMATION_MODE::getDefaultValue)
                                .build()
                );

        return builder.setParentScreen(parent).setTransparentBackground(true).build();
    }
}
