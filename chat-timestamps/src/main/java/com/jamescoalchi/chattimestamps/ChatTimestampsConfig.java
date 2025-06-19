package com.jamescoalchi.chattimestamps;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "chattimestamps")
public class ChatTimestampsConfig implements ConfigData {

    @Comment("Master switch to turn the entire mod on or off.")
    public boolean enabled = true;

    @Comment("The format of the timestamp (e.g., HH:mm, HH:mm:ss.SSS).")
    public String timestampFormat = "HH:mm:ss";

    @Comment("The color of the timestamp (e.g., 'red', 'gold', '#eb7f68').")
    public String timestampColor = "GRAY";

}