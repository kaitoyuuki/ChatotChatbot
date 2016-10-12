package com.kaitoyuuki.chatotchatbot.minecraft;


import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {

    //patterns unabashedly stolen from Essentials. may or may not use them.
    static final transient Pattern VANILLA_PATTERN = Pattern.compile("\u00a7+[0-9A-FK-ORa-fk-or]?");
    static final transient Pattern VANILLA_COLOR_PATTERN = Pattern.compile("\u00a7+[0-9A-Fa-f]");
    static final transient Pattern VANILLA_MAGIC_PATTERN = Pattern.compile("\u00a7+[Kk]");
    static final transient Pattern VANILLA_FORMAT_PATTERN = Pattern.compile("\u00a7+[L-ORl-or]");
    static final transient Pattern REPLACE_ALL_PATTERN = Pattern.compile("(?<!&)&([0-9a-fk-orA-FK-OR])");
    static final transient Pattern REPLACE_PATTERN = Pattern.compile("&&(?=[0-9a-fk-orA-FK-OR])");


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
    static String stripColor(final String input, final Pattern pattern)
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
    public static String replaceFormat(final String input)
    {
        if (input == null)
        {
            return null;
        }
        return replaceColor(input, REPLACE_ALL_PATTERN);
    }

    static String replaceColor(final String input, final Pattern pattern)
    {
        return REPLACE_PATTERN.matcher(pattern.matcher(input).replaceAll("\u00a7$1")).replaceAll("&");
    }

    //used to prepare the config format strings for String.format()
    public static String prepareFormat(String input) {

        input.replace("{DISPLAYNAME}", "%1$");
        input.replace("{MESSAGE}", "%2$");


        return input;
    }
    //used to modify minecraft chat to match the formatting template stored in the config file.
    // first, pass formatting string through replaceFormat to get the templated color codes right
    // second, replace the tags with the proper thing
    public static String formatMCforMC(ServerChatEvent event) {
        String output;
        String author = event.getPlayer().getDisplayNameString();
        String message = event.getMessage();

        output = ChatotChatbot.MCFORMAT;
        output = prepareFormat(output);
        output = replaceFormat(output);


       // message = convertFormatting(message);

        output = String.format(output, author, message);



        return output;
    }

    public static String formatDCforMC(GuildMessageReceivedEvent event) {
        String output;
        String author = event.getAuthor().getUsername();
        String message = event.getMessage().getStrippedContent();
        //use event.getMessage().getEmotes() and event.getMessage().getRawContent() and
        //event.getMessage().getStrippedContent() in combination to display the full
        //text message in readible format.

        output = ChatotChatbot.DCtoMCFORMAT;
        //output = "&7DC &6\u300B&f<&9%1$s&f>&7:%2$s";
        output = prepareFormat(output);
        output = replaceFormat(output);


        //message = convertFormatting(message);
        message = stripFormat(message);

        output = String.format(output, author, message);


        return output;
    }

    /**
     * converts discord-style formatting tags in a string to pseudo-markup language in preparation for applying
     * minecraft-style formatting
     * @param input
     * the input string.
     * @return output
     * the processed string. if no formatting tags are found, returns the input string.
     */
   /* going to replace this with a parser. don't use it.
   public static String convertFormatting(String input) {
        String output;
        Pattern bold = Pattern.compile("(?<!\\\\)\\*\\*(([^*]|\\*(?!\\*)))+?\\*\\*");
        Pattern underline = Pattern.compile("(?<!\\\\)__([^_]|_(?!_))+?__");
        Pattern italics = Pattern.compile("(?<!\\\\)\\*(.+)\\*|_(.*)_");//DO NOT RUN THIS ONE BEFORE RUNNING BOTH BOLD AND UNDERLINE
        Pattern strike = Pattern.compile("(?<!\\\\)~~([^~]|~(?!~))+?~~");
        Pattern prep = Pattern.compile("(?<!\\\\)(\\{([bius]|\\/[bius])})");

        Matcher m = prep.matcher(input);
        if(m.find()) {
            input = m.replaceAll("\\$1");
        }
        m = bold.matcher(input);
        if(m.find()) {
            input = m.replaceAll("{b}$1{/b}");
        }

        m = underline.matcher(input);
        if(m.find()) {
            input = m.replaceAll("{u}$1{/u}");
        }
        m = strike.matcher(input);
        if(m.find()) {
            input = m.replaceAll("{s}$1{/s}");
        }
        m = italics.matcher(input);
        if(m.find()) {
            input = m.replaceAll("{i}$1{/i}");
        }

        output = input;//comment this out before you push an update, smartass
        return output;
    }*/



}
