package com.Overkill10117;

import com.Overkill10117.command.commands.Beta.SlashCommands;
import com.Overkill10117.command.commands.Events.OnButtonClick;
import com.Overkill10117.command.commands.Events.OnSelectionMenu;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class Bot {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static CommandListUpdateAction commands;


    private Bot() throws LoginException {
        JDA jda = JDABuilder.createLight(Config.get("token"), EnumSet.noneOf(GatewayIntent.class))
                .addEventListeners(new SlashCommands())
                .build();// slash commands don't need any intents;
        commands = jda.updateCommands();
        WebUtils.setUserAgent("Overkill10117");
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(0x3883d9)
                        .setFooter("type ,help")
        );


        EventWaiter waiter = new EventWaiter();
        JDABuilder.createDefault(Config.get("token"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.DIRECT_MESSAGE_TYPING,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_EMOJIS,
                        GatewayIntent.DIRECT_MESSAGES,
                        GatewayIntent.GUILD_INVITES
                )
                .disableCache(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOTE
                ))
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new Listener(waiter), waiter)
                .addEventListeners(new OnButtonClick())
                .addEventListeners(new OnSelectionMenu())
                .addEventListeners(new SlashCommands())
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setActivity(Activity.playing("Being Programmed || type ,help"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new Bot();
    }

}