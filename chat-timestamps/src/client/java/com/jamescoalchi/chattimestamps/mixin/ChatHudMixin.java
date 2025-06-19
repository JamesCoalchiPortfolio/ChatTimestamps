package com.jamescoalchi.chattimestamps.mixin;

import com.jamescoalchi.chattimestamps.ChatTimestampsConfig;
import com.mojang.serialization.DataResult;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    // Targets the main `addMessage` method that handles all messages.
    @ModifyVariable(
            method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
            at = @At("HEAD"),
            argsOnly = true
    )

    private Text onAddMessage(Text message) {
        ChatTimestampsConfig config = AutoConfig.getConfigHolder(ChatTimestampsConfig.class).getConfig();

        if (!config.enabled) {
            return message;
        }

        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern(config.timestampFormat);
        } catch (Exception e) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Default
        }

        MutableText timestamp = Text.literal("[" + LocalTime.now().format(formatter) + "] ").formatted(Formatting.GRAY); // Formatting.GRAY default

        Optional<TextColor> parsedColor = TextColor.parse(config.timestampColor.toLowerCase().replace(" ", "_")).result();

        parsedColor.ifPresent(color -> {
            timestamp.fillStyle(Style.EMPTY.withColor(color));
        });

        // Create an empty parent text
        MutableText newRoot = Text.empty();
        // Append timestamp and the original message
        newRoot.append(timestamp);
        newRoot.append(message);
        // Return the final text
        return newRoot;
    }
}