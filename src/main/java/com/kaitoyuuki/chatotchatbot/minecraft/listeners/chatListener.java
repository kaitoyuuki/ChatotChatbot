package com.kaitoyuuki.chatotchatbot.minecraft.listeners;

import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.bot;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;


public class chatListener {




    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent event) {
        String player = event.getUsername();
        String message = event.getMessage();
        Logger lg = ChatotChatbot.log;

    String dcM = "**" + player + ":** " + message + "";
        lg.info(dcM);
        bot.instance.sendMessage(dcM);
}

}
