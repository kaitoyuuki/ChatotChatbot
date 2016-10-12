package com.kaitoyuuki.chatotchatbot.discord;


import com.kaitoyuuki.chatotchatbot.CCConfig;
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


public class Bot implements Runnable{

    public static DCChatListener listener;
    public static Thread thread;
    public static Bot instance;

    public static MessageChannel channel;
    public static JDA jda;
    private static User me;
    private static CCConfig config;

    public static void runThread(CCConfig c) {
        if (thread == null) {
            thread = new Thread(new Bot());
            thread.start();
            config = c;
        } else {
            ChatotChatbot.log.error("JDA thread is already running!");
        }
    }


    public Bot() {

        instance = this;

    }


    @Override
    public void run() {
        try {
            try {
                jda = new JDABuilder()
                        .setBulkDeleteSplittingEnabled(false)
                        .setBotToken(ChatotChatbot.config.getTOKEN())
                        .addListener(listener = new DCChatListener())
                        .buildBlocking();
                channel = jda.getTextChannelById(ChatotChatbot.config.getCID());
            } catch (LoginException | IllegalArgumentException e) {
                ChatotChatbot.log.error("Invalid login credentials for Discord, disabling Chatot", e);
                config.setENABLED(false);
                return;
            } catch (InterruptedException e) {
                ChatotChatbot.log.error("Couldn't complete login, disabling Chatot");
                e.printStackTrace();
                config.setENABLED(false);
                return;
            }
            if (jda != null) {
                Guild server = jda.getGuildById(ChatotChatbot.config.getGID());
                if (server == null) {
                    ChatotChatbot.log.error("Chirp chirrup, I can't seem to find that server. Maybe check the ID in the config file?");
                    ChatotChatbot.config.setENABLED(false);
                    return;
                }
                me = jda.getUserById(jda.getSelfInfo().getId());
            }
        } catch (Throwable t) {
            ChatotChatbot.log.error("Chirp chirrup, something went wrong...", t);
            config.setENABLED(false);
        }

    }

    public void sendMessage(String player, String msg) {
        ChatotChatbot.log.debug("dcc sendmessage 1");
        MessageBuilder mb = new MessageBuilder();
        mb.appendString("**" + player + ":**");
        mb.appendString("*" + msg + "*");
        channel.sendMessage(mb.build());

    }

    public void sendMessage(User user, String msg) {

        ChatotChatbot.log.debug("dcc sendmessage 2");
        MessageBuilder mb = new MessageBuilder();
        mb.appendMention(user);
        mb.appendString(msg);
        Message message = mb.build();
        channel.sendMessage(message);
    }
    public void sendMessage(String msg) {

        ChatotChatbot.log.debug("dcc sendmessage 3");
        MessageBuilder mb = new MessageBuilder();
        mb.appendString(msg);
        Message message = mb.build();
        channel.sendMessage(message);
    }
    public void sendPM(User user, String msg) {
        MessageBuilder mb = new MessageBuilder();
        mb.appendString(msg);
        Message message = mb.build();
        user.getPrivateChannel().sendMessage(message);
    }

    public User getMe() {
        return me;
    }

    public void shutdown() {

        ChatotChatbot.log.info("Chirp chirroo, time for me to sleep~!");
        jda.shutdown();

    }

}
