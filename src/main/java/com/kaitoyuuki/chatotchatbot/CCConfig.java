package com.kaitoyuuki.chatotchatbot;
import net.minecraftforge.common.config.Configuration;

import java.io.File;


public class CCConfig {

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        ChatotChatbot.ENABLED = config.getBoolean("enabled", "general", false, "Will the bot run?");
        ChatotChatbot.TOKEN = config.getString("token", "JDA", "", "Bot token for the Discord Bot");
        ChatotChatbot.GID = config.getString("guildID", "JDA", "", "the server ID of the discord server the bot should work on");
        ChatotChatbot.CID = config.getString("channelID", "JDA", "", "the channel for the discord bot to work on");
        ChatotChatbot.PREFIX = config.getString("prefix", "JDA", "!", "the command prefix for the discord bot commands");


        config.save();

    }
}
