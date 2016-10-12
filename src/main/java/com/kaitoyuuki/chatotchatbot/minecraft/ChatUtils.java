package com.kaitoyuuki.chatotchatbot.minecraft;


import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;

import java.util.regex.Pattern;

public class ChatUtils {

    //patterns unabashedly stolen from Essentials. may or may not use them.
    private static final transient Pattern VANILLA_PATTERN = Pattern.compile("\u00a7+[0-9A-FK-ORa-fk-or]?");
    private static final transient Pattern VANILLA_COLOR_PATTERN = Pattern.compile("\u00a7+[0-9A-Fa-f]");
    private static final transient Pattern VANILLA_MAGIC_PATTERN = Pattern.compile("\u00a7+[Kk]");
    private static final transient Pattern VANILLA_FORMAT_PATTERN = Pattern.compile("\u00a7+[L-ORl-or]");
    private static final transient Pattern REPLACE_ALL_PATTERN = Pattern.compile("(?<!&)&([0-9a-fk-orA-FK-OR])");
    private static final transient Pattern REPLACE_PATTERN = Pattern.compile("&&(?=[0-9a-fk-orA-FK-OR])");


    //strip vanilla minecraft formatting codes. Going to use this for sanitizing discord chat
    //so that people can't use section signs to put colors and whatever on minecraft
    public static String stripFormat(final String input)
    {
        if (input == null)
        {
            return null;
        }
        return stripColor(input, VANILLA_PATTERN);
    }
    private static String stripColor(final String input, final Pattern pattern)
    {
        return pattern.matcher(input).replaceAll("");
    }

    public static String stripAmp(final String input) {
        if (input == null) {
            return null;
        }
        return stripColor(input, REPLACE_ALL_PATTERN);
    }

    //replaces & with the section sign; used to convert the chat formatting template to something workable
    //not applied to messages, as that would allow people to use color codes and such in chat.
    private static String replaceFormat(final String input)
    {
        if (input == null)
        {
            return null;
        }
        return replaceColor(input, REPLACE_ALL_PATTERN);
    }

    private static String replaceColor(final String input, final Pattern pattern)
    {
        return REPLACE_PATTERN.matcher(pattern.matcher(input).replaceAll("\u00a7$1")).replaceAll("&");
    }


    /**
     * reformats a Minecraft chat message based on the config file to modify the chat event
     * @param event
     * the ServerChatEvent which generated the message
     * @return
     * a string ready to be converted to a TextComponent
     */
    public static String formatMCforMC(ServerChatEvent event) {
        String output;
        String author = event.getPlayer().getDisplayNameString();
        String message = event.getMessage();

        output = ChatotChatbot.config.getMCFORMAT();
        output = replaceFormat(output);


       // message = convertFormatting(message);

        output = String.format(output, author, message);



        return output;
    }

    /**
     * converts discord messages to Minecraft formatting
     * @param event
     * the GuildMessageReceivedEvent which contains the discord message
     * @return
     * a string containing the formatted message, prepared for further use
     */
    public static String formatDCforMC(GuildMessageReceivedEvent event) {
        String output;
        String author = event.getAuthor().getUsername();
        String message = event.getMessage().getStrippedContent();

        //use event.getMessage().getEmotes() and event.getMessage().getRawContent() and
        //event.getMessage().getStrippedContent() in combination to display the full
        //text message in readible format.

        output = ChatotChatbot.config.getDCtoMCFORMAT();
        //output = "&7DC &6\u300B&f<&9%1$s&f>&7:%2$s";
        output = replaceFormat(output);


        message = stripFormat(message);

        output = String.format(output, author, message);


        return output;
    }


}
