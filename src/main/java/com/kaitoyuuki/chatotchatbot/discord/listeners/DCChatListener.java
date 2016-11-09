package com.kaitoyuuki.chatotchatbot.discord.listeners;


import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.Bot;
import com.kaitoyuuki.chatotchatbot.discord.handler.CDCommand;
import com.kaitoyuuki.chatotchatbot.minecraft.handler.MCChatHandler;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;


public class DCChatListener extends ListenerAdapter {

    private static MCChatHandler mcc = new MCChatHandler();

    //channel messages
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getGuild().getId().equals(ChatotChatbot.config.getGID())) {
            if (event.getChannel().getId().equals(ChatotChatbot.config.getCID())) {
                if (!(event.getAuthor().equals(Bot.instance.getMe()))) {
                    if(event.getMessage().getContent().startsWith(ChatotChatbot.config.getPREFIX())) {
                        if (!(event.getAuthor().isBot())) {
                            CDCommand.handle(event.getChannel(), event.getAuthor(), event.getMessage().getContent().substring(1));
                        }
                    } else {

                        mcc.sendMessage(event);
                    }
                }
            }
        }


    }
}


