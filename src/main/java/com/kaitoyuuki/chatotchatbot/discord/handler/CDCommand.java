package com.kaitoyuuki.chatotchatbot.discord.handler;

import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.bot;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CDCommand {

    private static Map<String, Handler> commands = new HashMap<String, Handler>();

    static {
        register("online", new Handler() {
            @Override
            public void handle(TextChannel channel, User sender, String[] args) {
                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                int online = server.getCurrentPlayerCount();
                channel.sendMessage(String.format("There %s %d player%s online", online == 1 ? "is" : "are", online, online == 1 ? "" : "s"));
                if(online != 0) {
                    String players = String.join(", ", server.getPlayerList().getAllUsernames());
                    channel.sendMessage("Online: " + players);
                }
            }
        });
        register("stopformat", new Handler() {
            @Override
            public void handle(TextChannel channel, User sender, String[] args) {
                if(channel.checkPermission(sender, Permission.BAN_MEMBERS)) {
                    if( ChatotChatbot.override) {
                        ChatotChatbot.override = false;
                        ChatotChatbot.log.error("Chirpy chirp! Something must have gone wrong, as " + sender.getUsername() +
                                " has asked me to disable minecraft chat formatting. Maybe take a look inside me to see what happened.");
                        ChatotChatbot.log.error("In order to turn minecraft chat formatting back on, please edit the config file" +
                                " and restart.");
                        bot.instance.sendMessage(sender, ", I've stopped formatting minecraft chat, chirrup! Everything is...");
                        bot.instance.sendMessage("so *plain...*:cry:");
                    } else {
                        bot.instance.sendMessage(sender, ":bird:");
                    }
                } else {
                    bot.instance.sendMessage(sender, ":bird:");
                }

            }
        });
        register("help", new Handler() {
            @Override
            public void handle(TextChannel channel, User sender, String[] args) {
                bot.instance.sendMessage(sender, ":mailbox_with_mail:");
                bot.instance.sendPM(sender, "Current commands: ");
                bot.instance.sendPM(sender, "online: tells you who is on the minecraft server");
                if(channel.checkPermission(sender, Permission.BAN_MEMBERS)) {
                    bot.instance.sendPM(sender, "stopformat: emergency stop for the minecraft formatting override." +
                            " This only effects the formatting for in-game messages, not for messages sent from discord."
                            );
                }
                bot.instance.sendPM(sender, "That's all I can do right now, chirp chirrup! :bird:");


            }

        });
    }

    public static void register(String name, Handler handler) {
        commands.put(name, handler);
    }

    public static void handle(TextChannel channel, User sender, String command) {
        String[] bits = command.split(" ");
        if(bits.length <= 0) return;
        String name = bits[0];
        if(commands.containsKey(name)) {
            String[] args = Arrays.copyOfRange(bits, 1, bits.length);
            commands.get(name).handle(channel, sender, args);
        }
    }

    public interface Handler {
        void handle(TextChannel channel, User sender, String[] args);
    }

}

