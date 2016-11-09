package com.kaitoyuuki.chatotchatbot.discord.handler;

import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.Bot;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CDCommand {

    private static Map<String, Handler> commands = new HashMap<>();

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
                    if( ChatotChatbot.config.doesOverride()) {
                        ChatotChatbot.config.setOverride(false);
                        ChatotChatbot.log.error("Chirpy chirp! Something must have gone wrong, as " + sender.getUsername() +
                                " has asked me to disable minecraft chat formatting. Maybe take a look inside me to see what happened.");
                        ChatotChatbot.log.error("In order to turn minecraft chat formatting back on, please edit the config file" +
                                " and restart.");
                        Bot.instance.sendMessage(sender, ", I've stopped formatting minecraft chat, chirrup! Everything is...");
                        Bot.instance.sendMessage("so *plain...*:cry:");
                    } else {
                        Bot.instance.sendMessage(sender, ":bird:");
                    }
                } else {
                    Bot.instance.sendMessage(sender, ":bird:");
                }

            }
        });
        register("help", new Handler() {
            @Override
            public void handle(TextChannel channel, User sender, String[] args) {
                Bot.instance.sendMessage(sender, ":mailbox_with_mail:");
                Bot.instance.sendPM(sender, "Current commands: ");
                Bot.instance.sendPM(sender, "online: tells you who is on the minecraft server");
                if(channel.checkPermission(sender, Permission.BAN_MEMBERS)) {
                    Bot.instance.sendPM(sender, "stopformat: emergency stop for the minecraft formatting override." +
                            " This only effects the formatting for in-game messages, not for messages sent from discord."
                            );
                }
                Bot.instance.sendPM(sender, "That's all I can do right now, chirp chirrup! :bird:");


            }

        });
    }

    private static void register(String name, Handler handler) {
        commands.put(name, handler);
    }

    public static void handle(TextChannel channel, User sender, String command) {
        String[] bits = command.split(" ");
        if(bits.length <= 0) return;
        String name = bits[0];
        if(commands.containsKey(name.toLowerCase())) {
            String[] args = Arrays.copyOfRange(bits, 1, bits.length);
            commands.get(name).handle(channel, sender, args);
        }
    }

    interface Handler {
        void handle(TextChannel channel, User sender, String[] args);
    }

}

