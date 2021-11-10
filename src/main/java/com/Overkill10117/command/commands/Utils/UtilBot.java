package com.Overkill10117.command.commands.Utils;

import net.dv8tion.jda.api.OnlineStatus;

public class UtilBot {

    /**
     * gt tje OnlineStatus Emoji
     *
     * @param stat
     * @return
     */
    public static String getStatusEmoji(OnlineStatus stat) {
        String status = "";
        switch (stat) {
            case ONLINE:
                status = Emoji.GUILD_ONLINE;
                break;
            case IDLE:
                status = Emoji.GUILD_IDLE;
                break;
            case DO_NOT_DISTURB:
                status = Emoji.GUILD_DND;
                break;
            case INVISIBLE:
                status = Emoji.GUILD_OFFLINE;
                break;
            case OFFLINE:
                status = Emoji.GUILD_OFFLINE;
                break;
            default:
                status = Emoji.GUILD_OFFLINE;
        }
        return status;
    }
}
