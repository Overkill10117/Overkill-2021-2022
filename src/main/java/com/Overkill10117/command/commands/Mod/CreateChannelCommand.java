package com.Overkill10117.command.commands.Mod;

import com.Overkill10117.Config;
import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CreateChannelCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        final TextChannel channel = ctx.getChannel();
        final List<String> args = ctx.getArgs();
        final Member member = ctx.getMember();
        String name = ctx.getMessage().getContentRaw();
        if (!member.hasPermission(Permission.MANAGE_SERVER) && !member.getId().equals(Config.get("owner_id"))) {
            channel.sendMessage("You must have the Manage Server permission to use his command").queue();
            return;
        }
        if (member.hasPermission(Permission.MANAGE_SERVER) && member.getId().equals(Config.get("owner_id"))) {
            if (ctx.getArgs().isEmpty()) {
                ctx.getChannel().sendMessage(getHelp()).queue();
                return;
            }
            if (ctx.getArgs().get(0).isBlank()) {
                ctx.getChannel().sendMessage(getHelp()).queue();
            }
            if (ctx.getArgs().get(1).isBlank()) {
                ctx.getChannel().sendMessage(getHelp()).queue();
            } else {
                ctx.getGuild().createTextChannel(name).queue();
                ctx.getChannel().sendMessage("Channel has been successfully created").queue();
            }
        }
    }

    @Override
    public String getName() {
        return "cc";
    }

    @Override
    public String getHelp() {
        return "creates a channel\n" +
                " Usage: ,cc [channel name]";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MOD;
    }
}
