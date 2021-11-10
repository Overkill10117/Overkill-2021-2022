package com.Overkill10117.command.commands.General;

import com.Overkill10117.CommandManager;
import com.Overkill10117.Config;
import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        String prefix = Config.get("prefix");

        if (args.isEmpty()) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Category");
            embedBuilder.setColor(Color.cyan);
            embedBuilder.addField(" | General","Basic Commands", false);
            embedBuilder.addField(" | Music ","Basic music commands.", false);
            embedBuilder.addField(" | Fun","Fun stuff.", false);
            embedBuilder.addField(" | Mod","Moderation", false);

            embedBuilder.setFooter("Type " + prefix + " help [group name] to see their commands");

            boolean disableOrEnable = !ctx.getMember().hasPermission(Permission.MANAGE_SERVER) && !ctx.getMember().getRoles().contains(ctx.getGuild().getRoleById(888627140046749697L));

            channel.sendMessageEmbeds(embedBuilder.build()).setActionRows(
                    ActionRow.of(
                            Button.secondary(ctx.getMember().getUser().getId() + ":general", "General").withEmoji(Emoji.fromEmote("general", Long.parseLong("862895295239028756"), true)),
                            Button.secondary(ctx.getMember().getUser().getId() + ":music", "Music").withEmoji(Emoji.fromEmote("music", Long.parseLong("862895295239028756"), true)),
                            Button.secondary(ctx.getMember().getUser().getId() + ":fun", "Fun").withEmoji(Emoji.fromEmote("fun", Long.parseLong("862895295239028756"), true)),
                            Button.secondary(ctx.getMember().getUser().getId() + ":mod", "Mod").withEmoji(Emoji.fromEmote("mod", Long.parseLong("862895295239028756"), true)))).queue();
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if (command == null) {
            channel.sendMessage("No help info found for " + search).queue();
            return;
        }

        embedBuilder.setTitle("Help results for " + search);
        embedBuilder.setColor(Color.BLUE);
        embedBuilder.setDescription("LOL");
        embedBuilder.setTimestamp(OffsetDateTime.now());
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `" + Config.get("prefix") + "help [command]`";
    }

    @Override
    public List<String> getAliases() {
        List<String> strings = new java.util.ArrayList<>();
        strings.add("cmds");
        strings.add("commands");
        strings.add("commandlist");
        return strings;
    }

    @Override
    public CommandType getCategory() {
        return CommandType.GENERAL;
    }
}
