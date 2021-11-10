package com.Overkill10117.command.commands.Info;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import com.Overkill10117.command.commands.Utils.UtilString;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class InfoServerCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        Guild guild = ctx.getGuild();

        //Detects ID
        if(ctx.getArgs().size() > 0) {
            if("-m".equals(ctx.getArgs().get(0)) && ctx.getArgs().size() > 1 && ctx.getArgs().get(0).length() == 18)
                guild = ctx.getJDA().getGuildById(ctx.getArgs().get(0));
            else if(ctx.getArgs().get(0).length() == 18)
                guild = ctx.getJDA().getGuildById(ctx.getArgs().get(0));
        }

        if(guild == null) {
            ctx.getChannel().sendMessage("🛑 Cannot find this guild.\n"
                    + "Either I am not in this guild or the ID you provided is invalid.").queue();
            return;
        }

        String icon, name, id, owner, created, region, verify;
        int txtChannel, audioChannel, member, online = 0, human = 0, bot = 0;

        icon = guild.getIconUrl();

        /* Identity */
        name = guild.getName();
        id = guild.getId();
        owner = Objects.requireNonNull(guild.getOwner()).getAsMention();

        /* Channel */
        txtChannel = guild.getTextChannels().size();
        audioChannel = guild.getVoiceChannels().size();

        /* Time */
        created = UtilString.formatOffsetDateTime(guild.getTimeCreated());

        String memberInfo = String.format(
                "**Total Roles**: %s\n**Total Members**: %s\n**Online Members**: %s\n**Offline Members**: %s\n**Bot Count**: %s",
                guild.getRoleCache().size(),
                guild.getMemberCache().size(),
                guild.getMemberCache().stream().filter((m) -> m.getOnlineStatus() == OnlineStatus.ONLINE).count(),
                guild.getMemberCache().stream().filter((m) -> m.getOnlineStatus() == OnlineStatus.OFFLINE).count(),
                guild.getMemberCache().stream().filter((m) -> m.getUser().isBot()).count()
        );



        /* Other */
        verify = guild.getVerificationLevel().toString();
        region = guild.getRegion().toString();


        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(name, null, null).setColor(Color.blue).setThumbnail(icon).setTimestamp(Instant.now())
                .setFooter("Server Info", null);

        embed.addField("🚫 ID", id, true);
        embed.addField("😱 Owner", owner, true);

        embed.addField("⏱ Created In", created, true);

        embed.addField("📺 Channel", "Text `"+txtChannel+"` | Voice `"+audioChannel+"`", true);
        embed.addField("😱" + " Member", "User `"+memberInfo+"` |  `"+online+"`\nHuman `"+human+"` |Bot `"+bot+"`", true);
        embed.addField("😱 Other", "Region `" + region + "` | Verification `" + verify + "`", true);

        ctx.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "serverinfo";
    }

    @Override
    public String getHelp() {
        return "Shows info about that server\n" +
                "Usage: `,serverinfo`\n" +
                "Parameter: `-h | [ID] | null`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.INFO;
    }
}
