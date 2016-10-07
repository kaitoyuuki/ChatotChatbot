package com.kaitoyuuki.chatotchatbot;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;


public class CCConfig {

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        //DCFORMAT and MCFORMAT are currently unused. these may or may not get used later.
        ChatotChatbot.ENABLED = config.getBoolean("enabled", "general", false, "Will the bot run?");
        ChatotChatbot.TOKEN = config.getString("token", "JDA", "", "Bot token for the Discord Bot");
        ChatotChatbot.GID = config.getString("guildID", "JDA", "", "the server ID of the discord server the bot should work on");
        ChatotChatbot.CID = config.getString("channelID", "JDA", "", "the channel for the discord bot to work on");
        ChatotChatbot.PREFIX = config.getString("prefix", "JDA", "!", "the command prefix for the discord bot commands");
        ChatotChatbot.DCFORMAT = config.getString("format", "JDA", "**{DISPLAYNAME}:** {MESSAGE}", "formatting for discord messages. "
         + "Tags: {DISPLAYNAME}: minecraft display name {MESSAGE}: chat message");
        ChatotChatbot.MCFORMAT = config.getString("format", "CHAT", "&f<{PREFIX}{DISPLAYNAME}{SUFFIX}&r&f>&r {MESSAGE}", "formatting "
            + "for the minecraft chat. Tags: {PREFIX}: user prefix {SUFFIX}: user suffix {DISPLAYNAME}: user display name "
            + "{MESSAGE}: for the message the user sent.");


        config.save();

    }
}
