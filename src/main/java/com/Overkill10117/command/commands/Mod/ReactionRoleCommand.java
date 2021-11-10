package com.Overkill10117.command.commands.Mod;

import com.Overkill10117.Config;
import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import com.Overkill10117.command.commands.Utils.ReactionRoleData;

public class ReactionRoleCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getGuild().getId().equals("798716119665737768")) {

            if (!ctx.getAuthor().getId().equals(Config.get("owner_id")) || !ctx.getAuthor().getId().equals(Config.get("owner_id"))) {
                ctx.getChannel().sendMessage("Do I know you????").queue();
                return;
            }

            if (ctx.getMessage().getMentionedChannels().isEmpty()) {
                ctx.getChannel().sendMessage("Kindly mention a channel!!").queue();
                return;
            }

            ctx.getMessage().getMentionedChannels().get(0).sendMessage("React here to get verified!!!").queue((message -> {
                ReactionRoleData.messages.add(message.getIdLong());
                ReactionRoleData.roles.put(message.getIdLong(), ctx.getGuild().getRoleById("798721274787659797"));
                message.addReaction("✅").queue();
            }));

            ctx.getAuthor().openPrivateChannel().queue(
                    (PrivateChannel) -> PrivateChannel.sendMessage("Successful!!! The user will now receive the role of " + ctx.getMessage().getMentionedRoles().get(0).getName() + " once they react to the message.").queue()
            );
        }

        if (ctx.getMessage().getMentionedRoles().isEmpty()) {
            ctx.getChannel().sendMessage("Kindly mention a role!!").queue();
            return;
        }


        if (!ctx.getAuthor().getId().equals(Config.get("owner_id")) || !ctx.getAuthor().getId().equals(Config.get("owner_id"))) {
            ctx.getChannel().sendMessage("Do I know you????").queue();
            return;
        }

        if (ctx.getMessage().getMentionedChannels().isEmpty()) {
            ctx.getChannel().sendMessage("Kindly mention a channel!!").queue();
            return;
        }

        ctx.getMessage().getMentionedChannels().get(0).sendMessage("React here to get verified!!!").queue((message -> {
            ReactionRoleData.messages.add(message.getIdLong());
            ReactionRoleData.roles.put(message.getIdLong(), ctx.getMessage().getMentionedRoles().get(0));
            message.addReaction("✅").queue();
        }));

        ctx.getAuthor().openPrivateChannel().queue(
                (PrivateChannel) -> PrivateChannel.sendMessage("Successful!!! The user will now receive the role of " + ctx.getMessage().getMentionedRoles().get(0).getName() + " once they react to the message.").queue()
        );
    }

    @Override
    public String getName() {
        return "reactionrole";
    }

    @Override
    public String getHelp() {
        return "Make a reaction role message\n" +
                "Usage: ,reactrole [role] [channel]";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MOD;
    }

}

