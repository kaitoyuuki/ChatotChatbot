package com.kaitoyuuki.chatotchatbot.minecraft.listeners;

import com.kaitoyuuki.chatotchatbot.ChatotChatbot;
import com.kaitoyuuki.chatotchatbot.discord.Bot;
import com.kaitoyuuki.chatotchatbot.minecraft.ChatUtils;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.apache.logging.log4j.Logger;

import static com.kaitoyuuki.chatotchatbot.minecraft.ChatUtils.*;
import static net.minecraft.command.CommandBase.getChatComponentFromNthArg;

@SuppressWarnings("unused")
public class MCChatListener {

    private Logger lg = ChatotChatbot.log;

    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent event) {
        String player = event.getPlayer().getDisplayNameString();
        String message = event.getMessage();

        //TODO pass chat through ChatUtils to modify appearance appropriately
        if (ChatotChatbot.config.doesOverride()) {
            event.setComponent(new TextComponentString(formatMCforMC(event)));
        }
        //removes all minecraft formatting sets (ampersand and section sign) before passing to discord.
        //this should eventually be replaced with discord formatting conversion, and probably won't be helpful
        message = stripFormat(message);
        message = stripAmp(message);
        String dcM = "**" + player.replace("_", "\\_") + ":** " + message + "";
        lg.debug(dcM);
        Bot.instance.sendMessage(dcM);

    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        String player = event.player.getDisplayNameString();
        String dcM = "_**" + player.replace("_", "\\_") + "** has joined the game_";
        lg.debug(dcM);
        Bot.instance.sendMessage(dcM);
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerEvent.PlayerLoggedOutEvent event) {
        String player = event.player.getDisplayNameString();
        String dcM = "_**" + player.replace("_", "\\_") + "** has left the game_";
        lg.debug(dcM);
        Bot.instance.sendMessage(dcM);
    }


    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayerMP) {
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            String cadaver = player.getDisplayNameString();
            String deathMsg = player.getCombatTracker().getDeathMessage().getUnformattedText();
            String dcM = "_**" + cadaver.replace("_", "\\_") + "** " + deathMsg + "_";
            lg.debug(dcM);
            Bot.instance.sendMessage(dcM);


        }
    }

    @SubscribeEvent
    public void onCommand(CommandEvent event) {


        if(event.getCommand().getCommandName().equals("me")) {
            try {
                //TODO figure out how to escape the player name for discord because people have stupid underscores
                ITextComponent itextcomponent = getChatComponentFromNthArg(event.getSender(), event.getParameters(), 0, !(event.getSender() instanceof EntityPlayer));
                TextComponentTranslation tct = new TextComponentTranslation("chat.type.emote", event.getSender().getDisplayName(), itextcomponent);
                String emote = tct.getUnformattedText();

                String dcM = "_" + emote + "_";
                lg.debug(dcM);
                Bot.instance.sendMessage(dcM);

            } catch (PlayerNotFoundException e) {
                //do nothing. because it's not impossible.
            }
        }
    }

}
