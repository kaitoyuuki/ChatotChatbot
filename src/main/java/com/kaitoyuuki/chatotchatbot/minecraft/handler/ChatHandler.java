package com.kaitoyuuki.chatotchatbot.minecraft.handler;

import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import net.dv8tion.jda.entities.Message;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ChatHandler {

    public void sendMessage(Message msg) {

        ChatotChatbot.log.info("mcc sendmessage 1");
        sendMessage(msg.getAuthor().getUsername(), msg.getRawContent());

    }
    public static void sendMessage(ITextComponent chatComponent) {
        ChatotChatbot.log.info("mcc sendmessage 3");
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(chatComponent);
    }

    public void sendMessage(String author, String msg) {

        ChatotChatbot.log.info("mcc sendmessage 2");
        sendMessage(new TextComponentString("<" + author + "> : " + msg));

    }
}
