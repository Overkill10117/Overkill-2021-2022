package com.Overkill10117.command.commands.Events;

import com.Overkill10117.command.Database.DatabaseManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnApiRequest extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getChannel().getName().equalsIgnoreCase("api")) {
            if (event.getAuthor().isBot()) {
                String contentRaw = event.getMessage().getContentRaw().toLowerCase();
                String[] args = contentRaw.split("\\s+");
                String firstCmd = args[0];

                switch (firstCmd) {
                    case "addcredit":
                    case "minuscredit":
                        try {
                            long userId = Long.parseLong(args[1]);
                            String amount = args[2];

                            DatabaseManager.INSTANCE.setCredits(userId, Integer.parseInt(amount));
                            replyAPIRequest(event.getChannel());
                        } catch (Exception e) {
                            replyAPIRequest(event.getChannel(), e);
                        }
                        break;
                }
            }
        }
    }

    private void replyAPIRequest(TextChannel textChannel) {
        textChannel.sendMessage("SUCCESS ON OPERATION: CODE SUCCESS").queue();
    }
    private void replyAPIRequest(TextChannel textChannel, Exception error) {
        textChannel.sendMessage("ERROR " + error.getLocalizedMessage() + " EXIT CODE WITH ERROR").queue();
    }
}
