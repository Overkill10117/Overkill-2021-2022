package com.Overkill10117.command.commands.General;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.entities.Emoji;

import java.io.IOException;
import java.sql.SQLException;

public class InviteMeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        ctx.getChannel().sendMessage("Invite me NOW!!!\n" +
                "https://discord.com/api/oauth2/authorize?client_id=906076911904636948&permissions=8&scope=bot%20applications.commands").queue();
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getHelp() {
        return "invite the bot now!!!";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.GENERAL;
    }
}
