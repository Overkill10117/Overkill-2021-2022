package com.Overkill10117.command.commands.Fun;

import com.Overkill10117.Config;
import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SpamCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        final long guildID = ctx.getGuild().getIdLong();
        String prefix = Config.get("prefix");

        if (args.isEmpty()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Spam");
            embedBuilder.setColor(Color.cyan);
            embedBuilder.setDescription("click the button to spam");

            channel.sendMessageEmbeds(embedBuilder.build()).setActionRows(
                    ActionRow.of(
                            net.dv8tion.jda.api.interactions.components.Button.secondary(ctx.getMember().getUser().getId() + ":again", "Spam").withEmoji(Emoji.fromEmote("spam", Long.parseLong("862895295239028756"), true)))).queue();

            return;
        }
    }

    @Override
    public String getName() {
        return "spam";
    }

    @Override
    public String getHelp() {
        return "click the button to spam";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.FUN;
    }
}
