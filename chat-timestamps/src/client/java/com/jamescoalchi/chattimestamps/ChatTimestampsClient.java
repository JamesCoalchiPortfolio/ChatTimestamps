package com.jamescoalchi.chattimestamps;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ChatTimestampsClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("ChatTimestamps");

    @Override
    public void onInitializeClient() {
        LOGGER.info("onInitializeClient() is not doing anything (except for config initializing), because a small James is handling all the logic in ChatHudMixin.java!");
        AutoConfig.register(ChatTimestampsConfig.class, GsonConfigSerializer::new);
    }
}