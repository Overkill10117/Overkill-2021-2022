package com.Overkill10117;

import com.Overkill10117.command.commands.Utils.ReactionRoleData;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Listener extends ListenerAdapter {
    int seconds;
    Message message;
    String item;

    public void Giveawayy(int time, Message message, String item) {
        seconds = time;
        this.message = message;
        this.item = item;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager;

    public Listener(EventWaiter waiter) {
        manager = new CommandManager(waiter);
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String name = event.getMember().getEffectiveName().replace(" ", "%20");
        String guildName = event.getGuild().getName().replace(" ", "+");
        String pngUrl = "https://api.popcat.xyz/welcomecard?background=https://media.discordapp.net/attachments/773868615636222003/907439760363126794/stars-flag-costume-comic-stars-hd-wallpaper-preview.png&text1=" + name + "&text2=Welcome+To+" + guildName + "&text3=" + event.getGuild().getMembers().size() + "th+Member&avatar=" + event.getUser().getEffectiveAvatarUrl();
        String pngUrl1 = "https://api.popcat.xyz/welcomecard?background=https://media.discordapp.net/attachments/775266206357127179/907867171496030258/thumb-1920-200852.png&text1=" + name + "&text2=Welcome+To+" + guildName + "&text3=" + event.getGuild().getMembers().size() + "th+Member&avatar=" + event.getUser().getEffectiveAvatarUrl();
        double meh = Math.random()*10;

        switch ((int) meh) {
            case 1:
                event.getGuild().getDefaultChannel().sendMessage(pngUrl).queue();
                return;
            case 2:
                event.getGuild().getDefaultChannel().sendMessage(pngUrl1).queue();
                return;
            default:
                event.getGuild().getDefaultChannel().sendMessage(pngUrl).queue();
        }
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        final int memberCount = event.getGuild().getMemberCount();
        String lol = String.format("GoodBye %s we have " + memberCount + " members now",
                event.getUser().getAsMention(), event.getGuild().getName());
        String icon = event.getUser().getEffectiveAvatarUrl();
        if (lol.isEmpty()) {
            return;
        }
        final EmbedBuilder useGuildSpecificSettingsInstead = new EmbedBuilder()
                .setDescription(lol)
                .setThumbnail(icon);


        Objects.requireNonNull(event.getGuild().getSystemChannel()).sendMessage(useGuildSpecificSettingsInstead.build()).queue();

    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        final String useGuildSpecificSettingsInstead = String.format("Thank you for adding %s to %s!!!" +
                        "To know about this bot feel free to type ,help\n" +
                        "To know more about a command type ,help [command name]",
                event.getJDA().getSelfUser().getAsMention(), event.getGuild().getName());

        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(useGuildSpecificSettingsInstead).queue();
    }

    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if (ReactionRoleData.messages.contains(event.getMessageIdLong())) {
            final Role role = ReactionRoleData.roles.get(event.getMessageIdLong());
            final User user = event.getUser();
            if (!user.isBot()) {

                user.openPrivateChannel().queue(PrivateChannel ->
                        PrivateChannel.sendMessage(role.getName() + " has been successfully added.").queue(message -> {
                            ReactionRoleData.roles.put(message.getIdLong(), role);
                            event.getGuild().addRoleToMember(user.getIdLong(), role).queue();
                        }));
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        if (ReactionRoleData.messages.contains(event.getMessageIdLong())) {
            final Role role = ReactionRoleData.roles.get(event.getMessageIdLong());
            final User user = event.getUser();
            assert user != null;
            if (!user.isBot()) {
                user.openPrivateChannel().queue(PrivateChannel ->
                        PrivateChannel.sendMessage(role.getName() + " has been removed").queue(message -> {
                            ReactionRoleData.roles.put(event.getMessageIdLong(), role);
                            event.getGuild().removeRoleFromMember(user.getIdLong(), role).queue();
                        }));
            }
        }
    }
    @Override
    public void onSlashCommand(SlashCommandEvent event)
    {
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        switch (event.getName())
        {
            case "ban":
                Member member = event.getOption("user").getAsMember(); // the "user" option is required so it doesn't need a null-check here
                User user = event.getOption("user").getAsUser();
                ban(event, user, member);
                break;
            case "say":
                say(event, event.getOption("content").getAsString()); // content is required so no null-check here
                break;
            case "leave":
                leave(event);
                break;
            case "prune": // 2 stage command with a button prompt
                prune(event);
                break;
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }
    public void ban(SlashCommandEvent event, User user, Member member)
    {
        event.deferReply(true).queue(); // Let the user know we received the command before doing anything else
        InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
        hook.setEphemeral(true); // All messages here will now be ephemeral implicitly
        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("You do not have the required permissions to ban users from this server.").queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("I don't have the required permissions to ban users from this server.").queue();
            return;
        }

        if (member != null && !selfMember.canInteract(member))
        {
            hook.sendMessage("This user is too powerful for me to ban.").queue();
            return;
        }

        int delDays = 0;
        OptionMapping option = event.getOption("del_days");
        if (option != null) // null = not provided
            delDays = (int) Math.max(0, Math.min(7, option.getAsLong()));
        // Ban the user and send a success response
        event.getGuild().ban(user, delDays)
                .flatMap(v -> hook.sendMessage("Banned user " + user.getAsTag()))
                .queue();
    }

    public void say(SlashCommandEvent event, String content)
    {
        event.reply(content).queue(); // This requires no permissions!
    }

    public void leave(SlashCommandEvent event)
    {
        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS))
            event.reply("You do not have permissions to kick me.").setEphemeral(true).queue();
        else
            event.reply("Leaving the server... :wave:") // Yep we received it
                    .flatMap(v -> event.getGuild().leave()) // Leave server after acknowledging the command
                    .queue();
    }

    public void prune(SlashCommandEvent event)
    {
        OptionMapping amountOption = event.getOption("amount"); // This is configured to be optional so check for null
        int amount = amountOption == null
                ? 100 // default 100
                : (int) Math.min(200, Math.max(2, amountOption.getAsLong())); // enforcement: must be between 2-200
        String userId = event.getUser().getId();
        event.reply("This will delete " + amount + " messages.\nAre you sure?") // prompt the user with a button menu
                .addActionRow(// this means "<style>(<id>, <label>)" the id can be spoofed by the user so setup some kinda verification system
                        Button.secondary(userId + ":delete", "Nevermind!"),
                        Button.danger(userId + ":prune:" + amount, "Yes!")) // the first parameter is the component id we use in onButtonClick above
                .queue();
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        final long guildId = event.getGuild().getIdLong();
        String prefix = Config.get("prefix");
        String raw = event.getMessage().getContentRaw();

        User user = event.getAuthor();
        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        if(raw.equalsIgnoreCase(",ghelp"))
        {
            event.getChannel().sendMessage("<:tada:294906617378504704> Giveaway help: <:tada:294906617378504704>\n"
                    + "`,ghelp` - this message\n"
                    + "`,start <seconds> [item]` - starts a giveway. Ex: `!gstart 180` for a 3 minute giveaway\n"
                    + "`,reroll <messageid>` - rerolls a winner for the giveaway on the provided message\n\n"
                    + "Commands require Manage Server permission to use\n"
                    + "Don't include <> nor []; <> means required, [] means optional").queue();
        }
        else  if(raw.equalsIgnoreCase(",reroll"))
        {
            String id = event.getMessage().getContentRaw().substring(7).trim();
            if(!id.matches("\\d{17,22}"))
            {
                event.getChannel().sendMessage("Invalid message id").queue();
                return;
            }
            Message m = event.getChannel().getHistory().getMessageById(id);
            if(m==null)
            {
                event.getChannel().sendMessage("Message not found!").queue();
                return;
            }
            m.getReactions()
                    .stream().filter(mr -> mr.getReactionEmote().getName().equals("\uD83C\uDF89"))
                    .findAny().ifPresent(mr -> {
                        List<User> users = new LinkedList<>(mr.getJDA().getUsers());
                        users.remove(m.getJDA().getSelfUser());
                        String uid = users.get((int)(Math.random()*users.size())).getId();
                        event.getChannel().sendMessage("Congratulations to <@"+uid+">! You won the reroll!").queue();
                    });
        }
        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Shutting down");
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        }

        if (raw.equalsIgnoreCase(prefix + "Hello")) {
            event.getChannel().sendMessage("Hi, I am Overkill10117").queue();
            return;
        }

        if (raw.startsWith(prefix)) {
            try {
                manager.handle(event, prefix);
            } catch (SQLException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void shutdown(GuildMessageReceivedEvent event, boolean isOwner) {
        LOGGER.info("The bot " + event.getAuthor().getAsMention() + " is shutting down.\n" +
                "Thank you for using General_Hello's Code!!!");

        event.getChannel().sendMessage("Shutting down... " ).queue();
        event.getChannel().sendMessage("Bot successfully shutdown! ").queue();
        EmbedBuilder em = new EmbedBuilder().setTitle("Shutdown details!").setColor(Color.red).setFooter("Shutdown on ").setTimestamp(LocalDateTime.now());
        em.addField("Shutdown made by ", event.getAuthor().getName(), false);
        em.addField("Date", LocalDateTime.now().getMonthValue() + "/" + LocalDateTime.now().getDayOfMonth() + "/" + LocalDateTime.now().getYear(), false);
        event.getAuthor().openPrivateChannel().complete().sendMessageEmbeds(em.build()).queue();

        if (!isOwner) {
            User owner = event.getJDA().retrieveUserById(Config.get("owner_id")).complete();
            owner.openPrivateChannel().complete().sendMessageEmbeds(em.build()).queue();
        }


        event.getJDA().shutdown();
        BotCommons.shutdown(event.getJDA());
    }
}
