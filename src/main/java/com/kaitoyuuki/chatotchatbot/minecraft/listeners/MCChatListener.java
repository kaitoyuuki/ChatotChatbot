package com.kaitoyuuki.chatotchatbot.minecraft.listeners;

import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.bot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.Logger;

import static com.kaitoyuuki.chatotchatbot.minecraft.ChatUtils.formatMCforMC;
import static com.kaitoyuuki.chatotchatbot.minecraft.ChatUtils.stripAmp;
import static com.kaitoyuuki.chatotchatbot.minecraft.ChatUtils.stripFormat;

@SuppressWarnings("unused")
public class MCChatListener {

    private Logger lg = ChatotChatbot.log;

    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent event) {
        String player = event.getPlayer().getDisplayNameString();
        String message = event.getMessage();

        //TODO pass chat through ChatUtils to modify appearance appropriately
        if (ChatotChatbot.override) {
            event.setComponent(new TextComponentString(formatMCforMC(event)));
        }
        //removes all minecraft formatting sets (ampersand and section sign) before passing to discord.
        //this should eventually be replaced, and probably won't be helpful
        message = stripFormat(message);
        message = stripAmp(message);
        String dcM = "**" + player + ":** " + message + "";
        lg.debug(dcM);
        bot.instance.sendMessage(dcM);

    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        String player = event.player.getDisplayNameString();
        String dcM = "_**" + player + "** has joined the game_";
        lg.debug(dcM);
        bot.instance.sendMessage(dcM);
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerEvent.PlayerLoggedOutEvent event) {
        String player = event.player.getDisplayNameString();
        String dcM = "_**" + player + "** has left the game_";
        lg.debug(dcM);
        bot.instance.sendMessage(dcM);
    }


    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            String ded = player.getDisplayNameString();
            String death = player.getCombatTracker().getDeathMessage().getUnformattedText();
            String dcM = "_**" + ded + "** " + death + "_";
            lg.debug(dcM);
            bot.instance.sendMessage(dcM);

        }
    }

}
