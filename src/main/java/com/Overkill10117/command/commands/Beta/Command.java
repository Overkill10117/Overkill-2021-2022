package com.Overkill10117.command.commands.Beta;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.sql.SQLException;

public class Command extends ListenerAdapter implements ICommand {
    String raw;
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        ctx.getChannel().sendMessage("The president deid").queue();
        ctx.getChannel().sendMessage("there are 5 suspects").queue();
        ctx.getChannel().sendMessage("Suspect 1: bystander").queue();
        ctx.getChannel().sendMessage("He said he was bystanding......").queue();
        ctx.getChannel().sendMessage("Suspect 2: Vice president").queue();
        ctx.getChannel().sendMessage("He said he is in his office.").queue();
        ctx.getChannel().sendMessage("Suspect 3: Secretary").queue();
        ctx.getChannel().sendMessage("He said he was in Vice's office.").queue();
        ctx.getChannel().sendMessage("Suspect 4: guard").queue();
        ctx.getChannel().sendMessage("He said he is walking around.").queue();
        ctx.getChannel().sendMessage("Suspect 5: A GAY.").queue();
        ctx.getChannel().sendMessage("He said he/She is annoyed.").queue();
        ctx.getChannel().sendMessage("type suspect number.").queue();

        if (raw.equalsIgnoreCase("1")) {
            ctx.getChannel().sendMessage("hello").queue();
        }
    }

    @Override
    public String getName() {
        return "a";
    }

    @Override
    public String getHelp() {
        return "a";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.FUN;
    }

}