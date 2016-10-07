package com.kaitoyuuki.chatotchatbot.discord;


import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.listeners.DCChatListener;
import net.dv8tion.jda.JDA;
import net.dv8tion.jda.JDABuilder;
import net.dv8tion.jda.MessageBuilder;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.entities.MessageChannel;
import net.dv8tion.jda.entities.User;

import javax.security.auth.login.LoginException;


public class bot implements Runnable{

    public static DCChatListener listener;
    public static Thread thread;
    public static bot instance;

    public static MessageChannel channel;
    public static JDA jda;

    public static void runThread() {
        if (thread == null) {
            thread = new Thread(new bot());
            thread.start();
        } else {
            ChatotChatbot.log.error("JDA thread is already running!");
        }
    }


    public bot() {

        instance = this;

    }

    public static void shutdown() {

        ChatotChatbot.log.info("Chirp chirroo, time for me to sleep~!");
        jda.shutdown();

    }
    @Override
    public void run() {
        try {
            try {
                jda = new JDABuilder()
                        .setBulkDeleteSplittingEnabled(false)
                        .setBotToken(ChatotChatbot.TOKEN)
                        .addListener(listener = new DCChatListener())
                        .buildBlocking();
                channel = jda.getTextChannelById(ChatotChatbot.CID);
            } catch (LoginException e) {
                ChatotChatbot.log.error("Invalid login credentials for Discord, disabling Chatot", e);
                ChatotChatbot.ENABLED = false;
                return;
            } catch (IllegalArgumentException e) {
                ChatotChatbot.log.error("Invalid login credentials for Discord, disabling Chatot", e);
                ChatotChatbot.ENABLED = false;
                return;
            }catch (InterruptedException e) {
                ChatotChatbot.log.error("Couldn't complete login, disabling Chatot");
                e.printStackTrace();
                ChatotChatbot.ENABLED = false;
                return;
            }
            if (jda != null) {
                Guild server = jda.getGuildById(ChatotChatbot.GID);
                if (server == null) {
                    ChatotChatbot.log.error("Chirp chirrup, I can't seem to find that server. Maybe check the ID in the config file?");
                    ChatotChatbot.ENABLED = false;
                    return;
                }
            }
        } catch (Throwable t) {
            ChatotChatbot.log.error("Chirp chirrup, something went wrong...", t);
            ChatotChatbot.ENABLED = false;
        }

    }

    public void sendMessage(String player, String msg) {
        ChatotChatbot.log.info("dcc sendmessage 1");
        MessageBuilder mb = new MessageBuilder();
        mb.appendString("**" + player + ":**");
        mb.appendString("*" + msg + "*");
        channel.sendMessage(mb.build());

    }

    public void sendMessage(User user, String msg) {

        ChatotChatbot.log.info("dcc sendmessage 2");
        MessageBuilder mb = new MessageBuilder();
        mb.appendMention(user);
        mb.appendString(msg);
        Message message = mb.build();
        channel.sendMessage(message);
    }
    public void sendMessage(String msg) {

        ChatotChatbot.log.info("dcc sendmessage 3");
        MessageBuilder mb = new MessageBuilder();
        mb.appendString(msg);
        Message message = mb.build();
        channel.sendMessage(message);
    }

}
