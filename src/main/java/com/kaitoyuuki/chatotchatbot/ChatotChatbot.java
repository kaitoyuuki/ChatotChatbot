package com.kaitoyuuki.chatotchatbot;


import com.kaitoyuuki.chatotchatbot.discord.bot;
import com.kaitoyuuki.chatotchatbot.discord.listeners.ChatListener;
import com.kaitoyuuki.chatotchatbot.minecraft.listeners.chatListener;
import net.dv8tion.jda.JDA;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ChatotChatbot.MODID, version = ChatotChatbot.VERSION, acceptableRemoteVersions = "*")
public class ChatotChatbot {

    public static final String MODID = "ChatotChatbot";
    public static final String VERSION = "1.0";

    public static boolean ENABLED;
    public static String TOKEN;
    public static String GID;
    public static String CID;
    public static String PREFIX;

    public static Logger log = LogManager.getLogger(MODID);


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        log.info("Chatot, cha chatot!");
        CCConfig.init(event.getSuggestedConfigurationFile());
        if (ENABLED) {
            MinecraftForge.EVENT_BUS.register(new chatListener());
            log.info("Cha Chatot tot! Connecting to Discord!");
            bot.runThread();

        } else {
            log.warn("Chatot has not been configured. Please edit the config file and reload.");
        }


    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        bot.shutdown();
    }


}
