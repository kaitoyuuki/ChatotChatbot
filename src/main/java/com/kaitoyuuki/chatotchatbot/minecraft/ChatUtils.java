package com.kaitoyuuki.chatotchatbot.minecraft;


import java.util.regex.Pattern;

public class ChatUtils {

    static final transient Pattern REPLACE_ALL_PATTERN = Pattern.compile("(?<!&)&([0-9a-fk-orA-FK-OR])");
    static final transient Pattern REPLACE_PATTERN = Pattern.compile("&&(?=[0-9a-fk-orA-FK-OR])");

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


}
