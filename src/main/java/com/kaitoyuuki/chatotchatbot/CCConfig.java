package com.kaitoyuuki.chatotchatbot;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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
        ChatotChatbot.DCFORMAT = config.getString("format", "JDA", "**%1$s:** %2$s", "formatting for minecraft messages" +
                " to be displayed on the discord server. "
         + "Tags: %1$s: minecraft display name %2$s: chat message");

        ChatotChatbot.override = config.getBoolean("overrideMCChat","CHAT", false, "Should the bot override default minecraft" +
                "chat formatting? note, this only applies to messages sent by players from within the game.");
        ChatotChatbot.MCFORMAT = config.getString("format", "CHAT", "&f<&3%1$s&r&f>&r %2$s", "formatting "
            + "for messages sent within minecraft chat. Tags: %1$s: user display name "
            + "%2$s: for the message the user sent.");
        ChatotChatbot.DCtoMCFORMAT = config.getString("discordConversion", "CHAT", "&7DC &6\\u300B&f<&9%1$s&f>&7:%2$s",
                "formatting for how discord messages will appear in minecraft. tags: %1$s: nickname {USERNAME}: username" +
                        "%2$s: the message that was sent.");



        config.save();

    }
}
