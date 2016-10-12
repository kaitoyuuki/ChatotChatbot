package com.kaitoyuuki.chatotchatbot.minecraft.handler;

import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.events.message.guild.GuildMessageReceivedEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import static com.kaitoyuuki.chatotchatbot.minecraft.ChatUtils.formatDCforMC;

public class MCChatHandler {

    public void sendMessage(Message msg) {

        ChatotChatbot.log.debug("mcc sendmessage 1");
        sendMessage(msg.getAuthor().getUsername(), msg.getRawContent());

    }
    public void sendMessage(GuildMessageReceivedEvent event) {
        String message = formatDCforMC(event);
        sendMessage(message);

    }
    public void sendMessage(String message) {
        sendMessage(new TextComponentString(message));
    }
    public static void sendMessage(ITextComponent chatComponent) {
        ChatotChatbot.log.debug("mcc sendmessage 3");
        ChatotChatbot.log.debug(chatComponent.getUnformattedComponentText());
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(chatComponent);
    }

    public void sendMessage(String author, String msg) {

        ChatotChatbot.log.debug("mcc sendmessage 2");
        //TODO use ChatUtils to modify format appropriately

        sendMessage(new TextComponentString("<" + author + "> : " + msg));

    }


}
