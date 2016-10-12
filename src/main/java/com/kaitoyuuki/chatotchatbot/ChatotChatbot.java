package com.kaitoyuuki.chatotchatbot;


import com.kaitoyuuki.chatotchatbot.discord.bot;
import com.kaitoyuuki.chatotchatbot.minecraft.listeners.MCChatListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
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
    public static String DCFORMAT;
    public static String MCFORMAT;
    public static String DCtoMCFORMAT;
    public static boolean override;

    public static Logger log = LogManager.getLogger(MODID);


    //TODO test user mentions, role mentions, channel mentions, and emoji

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        log.info("Chatot, cha chatot!");
        CCConfig.init(event.getSuggestedConfigurationFile());
        if (ENABLED) {
            MinecraftForge.EVENT_BUS.register(new MCChatListener());
            log.info("Cha Chatot tot! Connecting to Discord!");
            bot.runThread();

        } else {
            log.warn("Chatot has not been configured. Please edit the config file and reload.");
        }


    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        //should really move the bot startup here, I think
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {

        //may or may not be important, but I'll have my cleanup code, damnit!
        bot.shutdown();
    }


}
