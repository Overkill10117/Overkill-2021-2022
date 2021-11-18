package com.Overkill10117;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.ICommand;
import com.Overkill10117.command.commands.Fun.*;
import com.Overkill10117.command.commands.General.HelpCommand;
import com.Overkill10117.command.commands.General.InviteMeCommand;
import com.Overkill10117.command.commands.Info.*;
import com.Overkill10117.command.commands.Mod.AnnounceCommand;
import com.Overkill10117.command.commands.Mod.GiveawayCommand;
import com.Overkill10117.command.commands.Mod.PollCommand;
import com.Overkill10117.command.commands.Mod.ReactionRoleCommand;
import com.Overkill10117.command.commands.Music.*;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;
import static net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();
    public static ArrayList<String> commandNames = new ArrayList<>();
    public static ArrayList<String> cmdNames = new ArrayList<>();

    public CommandManager(EventWaiter waiter) {
        addCommand(new HelpCommand(this));
        addCommand(new SpamCommand());
        addCommand(new TriviaCommand());
        addCommand(new AutoSpamCommand());
        addCommand(new EightBallCommand());
        addCommand(new JokeCommand());
        addCommand(new MemeCommand());
        addCommand(new TargetSpamCommand());
        addCommand(new AnnounceCommand());
        addCommand(new GiveawayCommand());
        addCommand(new PollCommand());
        addCommand(new ReactionRoleCommand());
        addCommand(new InviteMeCommand());
        addCommand(new AboutCommand());
        addCommand(new AvatarCommand());
        addCommand(new InfoServerCommand());
        addCommand(new InfoUserCommand());
        addCommand(new ModsCommand());
        addCommand(new FightCommand());

        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new PlayCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new SkipCommand());
        addCommand(new StopCommand());
        Bot.commands.addCommands(
                new CommandData("ban", "Ban a user from this server. Requires permission to ban users.")
                        .addOptions(new OptionData(USER, "user", "The user to ban") // USER type allows to include members of the server or other users by id
                                .setRequired(true)) // This command requires a parameter
                        .addOptions(new OptionData(INTEGER, "del_days", "Delete messages from the past days.")) // This is optional
        );

        // Simple reply commands
        Bot.commands.addCommands(
                new CommandData("say", "Makes the bot say what you tell it to")
                        .addOptions(new OptionData(STRING, "content", "What the bot should say")
                                .setRequired(true))
        );

        // Commands without any inputs
        Bot.commands.addCommands(
                new CommandData("leave", "Make the bot leave the server")
        );

        Bot.commands.addCommands(
                new CommandData("prune", "Prune messages from this channel")
                        .addOptions(new OptionData(INTEGER, "amount", "How many messages to prune (Default 100)"))
        );
        Bot.commands.addCommands(
                new CommandData("spam", "Make the bot spam alot use this wisely or else...")

        );
        Bot.commands.addCommands(
                new CommandData("meme", "meme")
        );

        // Send the new set of commands to discord, this will override any existing global commands with the new set provided here
        Bot.commands.queue();
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present, " + cmd.getName() + " in " + cmd.getClass());
        }

        cmdNames.add(cmd.getName());
        System.out.println(Bot.ANSI_BLUE + "Loaded the ign " + cmd.getName() + " -> " + cmd.getClass() + Bot.ANSI_RESET);
        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }

        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event, String prefix) throws SQLException, IOException, InterruptedException {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix),  "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }

}
