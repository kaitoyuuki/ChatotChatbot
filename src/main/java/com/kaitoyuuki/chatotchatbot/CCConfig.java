package com.kaitoyuuki.chatotchatbot;
import net.minecraftforge.common.config.Configuration;

import java.io.File;


public class CCConfig {

    private boolean ENABLED;
    private String TOKEN;
    private String GID;
    private String CID;
    private String PREFIX;
    private String DCFORMAT;
    private String MCFORMAT;
    private String DCtoMCFORMAT;
    private boolean override;

    public CCConfig(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();


        ENABLED = config.getBoolean("enabled", "general", false, "Will the Bot run?");
        TOKEN = config.getString("token", "JDA", "", "Bot token for the Discord Bot");
        GID = config.getString("guildID", "JDA", "", "the server ID of the discord server the Bot should work on");
        CID = config.getString("channelID", "JDA", "", "the channel for the discord Bot to work on");
        PREFIX = config.getString("prefix", "JDA", "!", "the command prefix for the discord Bot commands");
        DCFORMAT = config.getString("format", "JDA", "**%1$s:** %2$s", "formatting for minecraft messages" +
                " to be displayed on the discord server. "
         + "Tags: %1$s: minecraft display name %2$s: chat message");

        override = config.getBoolean("overrideMCChat","CHAT", false, "Should the Bot override default minecraft" +
                " chat formatting? note, this only applies to messages sent by players from within the game.");
        MCFORMAT = config.getString("format", "CHAT", "&f<&3%1$s&r&f>&r %2$s", "formatting "
            + "for messages sent within minecraft chat. Tags: %1$s: user display name "
            + "%2$s: for the message the user sent.");
        DCtoMCFORMAT = config.getString("discordConversion", "CHAT", "&7DC &6\\u300B&f<&9%1$s&f>&7:%2$s",
                "formatting for how discord messages will appear in minecraft. tags: %1$s: nickname {USERNAME}: username" +
                        "%2$s: the message that was sent.");
        config.save();
    }

    public boolean isENABLED() {
        return ENABLED;
    }

    public void setENABLED(boolean ENABLED) {
        this.ENABLED = ENABLED;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getGID() {
        return GID;
    }

    public void setGID(String GID) {
        this.GID = GID;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getPREFIX() {
        return PREFIX;
    }

    public void setPREFIX(String PREFIX) {
        this.PREFIX = PREFIX;
    }

    public String getDCFORMAT() {
        return DCFORMAT;
    }

    public void setDCFORMAT(String DCFORMAT) {
        this.DCFORMAT = DCFORMAT;
    }

    public String getMCFORMAT() {
        return MCFORMAT;
    }

    public void setMCFORMAT(String MCFORMAT) {
        this.MCFORMAT = MCFORMAT;
    }

    public String getDCtoMCFORMAT() {
        return DCtoMCFORMAT;
    }

    public void setDCtoMCFORMAT(String DCtoMCFORMAT) {
        this.DCtoMCFORMAT = DCtoMCFORMAT;
    }

    public boolean doesOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }
}
