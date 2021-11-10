package com.Overkill10117.command.commands.Mod;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class AnnounceCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage(getHelp()).queue();
            return;
        } else {
            String content = String.format("%s", ctx.getMessage().getContentRaw().split("\\s+", 3)[2]);
            EmbedBuilder announce = new EmbedBuilder()
                    .setColor(Color.red)
                    .setAuthor(ctx.getAuthor().getAsTag(), null, ctx.getAuthor().getEffectiveAvatarUrl())
                    .setDescription(content)
                    .setFooter("Announcement")
                    .setTitle("Announcement!!");
            ctx.getMessage().getMentionedChannels().get(0).sendMessage(announce.build()).queue(message -> {
                message.addReaction("\tU+1F44D").queue();
                message.addReaction("\tU+1F44E").queue();
            });
        }
    }
    @Override
    public String getName() {
        return "announce";
    }

    @Override
    public String getHelp() {
        return "make a announcement" +
                "Usage: announce [channel] [content]";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MOD;
    }
}

