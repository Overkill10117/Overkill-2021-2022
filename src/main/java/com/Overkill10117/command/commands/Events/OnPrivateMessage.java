package com.Overkill10117.command.commands.Events;

import com.Overkill10117.Config;
import com.Overkill10117.command.Database.DatabaseManager;
import com.Overkill10117.command.commands.Info.InfoUserCommand;
import com.Overkill10117.command.commands.Utils.Util;
import com.Overkill10117.command.commands.currency.Data;
import com.Overkill10117.command.commands.currency.UpdateCoinsCommand;
import com.Overkill10117.command.commands.currency.UserUserOverkill;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OnPrivateMessage extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnPrivateMessage.class);
    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder().setColor(InfoUserCommand.randomColor());
        if (Data.progress.containsKey(event.getAuthor())) {
            Integer progress = Data.progress.get(event.getAuthor());
            if (progress == 1) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(event.getMessage().getContentRaw());
                Data.answers.put(event.getAuthor(), arrayList);
                Data.progress.put(event.getAuthor(), 2);

                ArrayList<String> oldAnswers = Data.answers.get(event.getAuthor());

                embedBuilder.setTitle("Account Summary");
                embedBuilder.setDescription("Name: ***" + oldAnswers.get(0) + "***\n" +
                        "Discord name: " + event.getAuthor().getAsMention() + "\n" +
                        "Account creation date: " + DateTimeFormatter.ofPattern("M/d/u h:m:s a").format(event.getAuthor().getTimeCreated()) + "\n" +
                        "Agreed to all rules: ✅").setFooter("Thank you for registering and 1000 credits have been added to your account!!");
                embedBuilder.setColor(InfoUserCommand.randomColor());
                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

                UserUserOverkill user = new UserUserOverkill(oldAnswers.get(0), event.getAuthor(), 1000);
                DatabaseManager.INSTANCE.setCredits(event.getAuthor().getIdLong(), 1000);
                Data.userUserUserOverkillHashMap.put(event.getAuthor(), user);
                Data.UserUserOverkill.add(user);

                DatabaseManager.INSTANCE.newInfo(event.getAuthor().getIdLong(), oldAnswers.get(0));

                try {
                    UpdateCoinsCommand.loadData();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Data.realNameUserUserOverkillHashMap.put(oldAnswers.get(0), user);

                LOGGER.info("New account created for " + user.getDiscordUser().getName() + " with the name of " + user.getRealName());
            }
            return;
        }

        if (event.getMessage().getContentRaw().startsWith("delete") && event.getAuthor().getId().equals(Config.get("owner_id"))) {
            String[] split = event.getMessage().getContentRaw().split("\\s++");
            String messageId = split[2];
            String channelId = split[1];
            event.getJDA().getTextChannelById(channelId).retrieveMessageById(messageId).queue((message -> {
                if (message != null) {
                    event.getChannel().sendMessage("Deleting this message now.....\n" + message.getContentDisplay()).queue();
                    message.delete().queue();
                    event.getChannel().sendMessage("Successfully deleted it!").queue();
                    return;
                }
                event.getChannel().sendMessage("The message was not found!").queue();
            }));
            return;
        }

        if (event.getAuthor().isBot()) return;
        Util.sendOwnerDM(event.getAuthor().getAsTag() + " sent me a private message with the following content.\n" +
                event.getMessage().getContentRaw());
    }
}
