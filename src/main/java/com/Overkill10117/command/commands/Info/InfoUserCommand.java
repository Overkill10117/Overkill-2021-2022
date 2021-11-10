package com.Overkill10117.command.commands.Info;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import com.Overkill10117.command.commands.Utils.Emoji;
import com.Overkill10117.command.commands.Utils.UtilString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class InfoUserCommand implements ICommand {

    public final static String GUILD_ONLINE = ":green_circle:";
    public final static String GUILD_IDLE = " :crescent_moon: ";
    public final static String GUILD_DND = ":no_entry:";
    public final static String GUILD_OFFLINE = ":black_circle:";

    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().isEmpty()) {
            embedUser(ctx.getAuthor(), ctx.getGuild().getMember(ctx.getSelfUser()), ctx.getEvent());
        } else {
            List<User> userMention = ctx.getMessage().getMentionedUsers();
            for(User user : userMention) {
                embedUser(user, ctx.getGuild().getMember(user), ctx.getEvent());
            }
        }
    }

    @Override
    public String getName() {
        return "user";
    }

    @Override
    public String getHelp() {
        return "Get info about the specified user\n" +
                "`,user [mentioned member]`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.INFO;
    }


    public static String getStatusEmoji(OnlineStatus stat)
    {
        String status = "";
        switch (stat) {
            case ONLINE:
                status = GUILD_ONLINE;
                break;
            case IDLE:
                status = GUILD_IDLE;
                break;
            case DO_NOT_DISTURB:
                status = GUILD_DND;
                break;
            default:
                status = GUILD_OFFLINE;
        }
        return status;
    }

    public static Color randomColor() {
        Random colorpicker = new Random();
        int red = colorpicker.nextInt(255) + 1;
        int green = colorpicker.nextInt(255) + 1;
        int blue = colorpicker.nextInt(255) + 1;
        return new Color(red, green, blue);
    }

    private void embedUser(User user, Member member, GuildMessageReceivedEvent e) {
        String name, id, dis, nickname, icon, status, statusEmoji, game, join, register;

        icon = user.getEffectiveAvatarUrl();

        /* Identity */
        name = user.getName();
        id = user.getId();
        dis = user.getDiscriminator();
        nickname = member == null || member.getNickname() == null ? "N/A" : member.getEffectiveName();

        /* Status */
        OnlineStatus stat = member == null ? null : member.getOnlineStatus();
        status = stat == null ? "N?A" : UtilString.VariableToString("_", stat.getKey());
        statusEmoji = stat == null ? "" : getStatusEmoji(stat);
        game = stat == null ? "N/A" : member.getActivities().isEmpty() ? "N/A" : member.getActivities().get(0).getName();

        /* Time */
        join = member == null ? "N?A" : member.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        register = user.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(name, null, icon)
                .setColor(randomColor()).setThumbnail(icon).setTimestamp(Instant.now())
                .setFooter("User Info", null);

        embed.addField("Identity", Emoji.INFORMATION+"ID `"+id+"`\n"+
                "Nickname `"+nickname+"` | Discriminator `"+dis+"`", true);

        embed.addField("Status", Emoji.SCREAM+" `"+game+"`\n"
                +statusEmoji+" `"+status+"`\n", true);

        embed.addField("Time" ,  Emoji.ERROR+"Join `" + join+"`\n"+
                "Register `"+register+"`\n", true);


        e.getChannel().sendMessage(embed.build()).queue();
    }
}

