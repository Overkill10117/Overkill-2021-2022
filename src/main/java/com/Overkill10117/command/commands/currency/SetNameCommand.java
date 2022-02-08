package com.Overkill10117.command.commands.currency;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;

import java.io.IOException;
import java.sql.SQLException;

public class SetNameCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("Kindly place your name after `,setname [name]`").queue();
            return;
        }

        String[] split = ctx.getArgs().toString().replace("[", "").replace("]", "").split(",");
        String name = split[0] + split[1];
        GetData getData = new GetData();
        getData.checkIfContainsData(ctx.getAuthor(), ctx);

        if (Data.userUserUserOverkillHashMap.containsKey(ctx.getAuthor())) {
            Data.userUserUserOverkillHashMap.get(ctx.getAuthor()).setUserUserOverkillName(name);
            GetData.setName(ctx.getAuthor(), name);
            ctx.getChannel().sendMessage("Successfully set the name to `" + name + "`").queue();
            return;
        }

        ctx.getChannel().sendMessage("You have not registered yet!").queue();
    }

    @Override
    public String getName() {
        return "setname";
    }

    @Override
    public String getHelp() {
        return "Sets you name if you **CANNOT FOLLOW INSTRUCTIONS**";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.FUN;
    }
}
