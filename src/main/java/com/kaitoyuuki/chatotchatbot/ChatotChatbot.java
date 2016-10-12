package com.kaitoyuuki.chatotchatbot;


import com.kaitoyuuki.chatotchatbot.discord.Bot;
import com.kaitoyuuki.chatotchatbot.minecraft.listeners.MCChatListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ChatotChatbot.MODID, version = ChatotChatbot.VERSION, acceptableRemoteVersions = "*")
public class ChatotChatbot {

    public static final String MODID = "chatotchatbot";
    public static final String VERSION = "1.0";

    public static CCConfig config;

    public static Logger log = LogManager.getLogger(MODID);



    //TODO test user mentions, role mentions, channel mentions, and emoji

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        log.info("Chatot, cha chatot!");
        config = new CCConfig(event.getSuggestedConfigurationFile());
        if(config.isENABLED()) {
            MinecraftForge.EVENT_BUS.register(new MCChatListener());
            log.info("Registering listeners, chacha!");

        } else {
            log.warn("Chatot has not been configured. Please edit the config file and reload.");
        }


    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        if(config.isENABLED()) {
            log.info("Cha Chatot tot! Connecting to Discord!");
            Bot.runThread(config);

        } else {
            log.warn("Chatot has not been configured. Please edit the config file and reload.");
        }
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {

        //may or may not be important, but I'll have my cleanup code, damnit!
        Bot.instance.shutdown();
    }


}
