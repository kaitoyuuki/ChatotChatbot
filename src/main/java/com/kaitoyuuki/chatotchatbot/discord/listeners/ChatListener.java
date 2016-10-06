package com.kaitoyuuki.chatotchatbot.discord.listeners;


import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.bot;
import com.kaitoyuuki.chatotchatbot.discord.handler.CDCommand;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.events.message.MessageEmbedEvent;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.events.guild.*;
import net.dv8tion.jda.events.guild.member.*;
import net.dv8tion.jda.events.guild.role.*;
import net.dv8tion.jda.hooks.ListenerAdapter;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.security.auth.login.LoginException;

public class ChatListener extends ListenerAdapter {


    static JDA jda;
    static com.kaitoyuuki.chatotchatbot.minecraft.handler.ChatHandler mcc = new com.kaitoyuuki.chatotchatbot.minecraft.handler.ChatHandler();
    public JDA getJDA() {

        return jda;
    }

    //Joined a new guild(server)
    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {

        ChatotChatbot.log.info("I joined a new Guild! Guild Name: " + event.getGuild().getName());

    }

    //left a guild
    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {

        ChatotChatbot.log.info("I left a guild... Hope I wasn't kicked.  Guild Name: " + event.getGuild().getName());

    }

    // private messages
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        ChatotChatbot.log.info(event.getAuthor().getUsername() + " " + event.getMessage().getContent());


    }

    //channel messages
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        ChatotChatbot.log.info(event.getGuild().getName() + " " + event.getChannel().getName() + " " +
                event.getAuthor().getUsername() + " " + event.getMessage().getContent());
        ChatotChatbot.log.info(event.getGuild().getId());
        ChatotChatbot.log.info(event.getChannel().getId());
        if (event.getGuild().getId().equals(ChatotChatbot.GID)) {
            if (event.getChannel().getId().equals(ChatotChatbot.CID)) {
                if(event.getMessage().getContent().startsWith(ChatotChatbot.PREFIX)) {
                    CDCommand.handle(event.getChannel(), event.getAuthor(), event.getMessage().getContent().substring(1));

                } else if (!(event.getAuthor().isBot())) {
                    mcc.sendMessage(event.getMessage());
                }
            }
        }


    }
}


