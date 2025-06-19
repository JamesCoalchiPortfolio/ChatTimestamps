package com.jamescoalchi.chattimestamps;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ChatTimestampsConfig config = AutoConfig.getConfigHolder(ChatTimestampsConfig.class).getConfig();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("Chat Timestamps Config"));

            builder.setSavingRunnable(() -> {
                AutoConfig.getConfigHolder(ChatTimestampsConfig.class).save();
            });

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // ON/OFF
            general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enable Timestamps"), config.enabled)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> config.enabled = newValue)
                    .build());

            // Timestamp format
            general.addEntry(entryBuilder.startStrField(Text.literal("Timestamp Format"), config.timestampFormat)
                    .setDefaultValue("HH:mm:ss")
                    .setTooltip(Text.literal("HH=hour, mm=minute, ss=second, SSS=millisecond"))
                    .setSaveConsumer(newValue -> config.timestampFormat = newValue)
                    .build());

            // Timestamp color
            general.addEntry(entryBuilder.startStrField(Text.literal("Timestamp Color"), config.timestampColor)
                    .setDefaultValue("GRAY")
                    .setTooltip(Text.literal("Color name (e.g., 'GOLD', 'AQUA')\nor Hex code (e.g., '#AAAAAA')"))
                    .setSaveConsumer(newValue -> config.timestampColor = newValue)
                    .build());

            return builder.build();
        };
    }
}