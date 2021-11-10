package com.Overkill10117.command.commands.Info;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.awt.*;

public class AvatarCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) {
            final Member target = ctx.getMember();
            final String avatarUrl = target.getUser().getAvatarUrl();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(target.getNickname());
            embedBuilder.setImage(avatarUrl);
            embedBuilder.setColor(Color.cyan);
            embedBuilder.setFooter(",help to get some help");

            ctx.getChannel().sendMessage(embedBuilder.build()).queue();

        }
        final Member target = ctx.getMessage().getMentionedMembers().get(0);
        final String avatarUrl = target.getUser().getAvatarUrl();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(target.getNickname());
        embedBuilder.setImage(avatarUrl);
        embedBuilder.setColor(Color.cyan);
        embedBuilder.setFooter(",help to get some help");


        ctx.getChannel().sendMessage(embedBuilder.build()).queue();

    }

    @Override
    public String getName() {
        return "avatar";
    }

    @Override
    public String getHelp() {
        return "avatar [metion]";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MOD;
    }
}


