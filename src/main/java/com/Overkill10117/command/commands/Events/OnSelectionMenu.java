package com.Overkill10117.command.commands.Events;

import com.Overkill10117.command.Database.DatabaseManager;
import com.Overkill10117.command.commands.Fun.TriviaCommand;
import com.Overkill10117.command.commands.Info.InfoUserCommand;
import com.Overkill10117.command.commands.Utils.UtilNum;
import com.Overkill10117.command.commands.currency.Data;
import com.Overkill10117.command.commands.currency.Levels.LevelPointManager;
import com.Overkill10117.command.commands.currency.UserUserOverkill;
import com.Overkill10117.command.commands.currency.Work.WorkCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
public class OnSelectionMenu extends ListenerAdapter {
    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        EmbedBuilder embedBuilder;

        SelectionMenu menu = SelectionMenu.create("menu:class")
                .setPlaceholder("Choose the game you want to find help on") // shows the placeholder indicating what this menu is for
                .setRequiredRange(1, 1) // only one can be selected
                .addOption("Uno", "uno")
                .addOption("Blackjack", "bj")
                .addOption("Guess the number", "gn")
                .addOption("Trivia", "trivia")
                .addOption("Chess", "chess")
                .build();
        int x = 0;

        System.out.println(event.getSelectedOptions().get(0).getValue());
        if (TriviaCommand.storeAnswer.containsKey(event.getUser())) {
            String answer = TriviaCommand.storeAnswer.get(event.getUser());
            String question = TriviaCommand.storeQuestion.get(event.getUser());
            String difficulty = TriviaCommand.storeDifficulty.get(event.getUser());
            int reward = 2_000;

            int multiplier = difficulty.equals("medium") ? 3 : 1;
            multiplier = difficulty.equals("hard") ? 5 : multiplier;

            reward = reward * multiplier;

            if (event.getSelectedOptions().get(0).getValue().equals(answer)) {
                if (WorkCommand.job.containsKey(event.getUser())) {
                    UserUserOverkill bankUser = Data.userUserUserOverkillHashMap.get(event.getJDA().getSelfUser());
                    int bankCredits = bankUser.getCredits();

                    int minRobOrFine = 0;
                    int maxRobOrFine = 200_000;

                    if (maxRobOrFine > bankCredits) {
                        maxRobOrFine = bankCredits;
                    }

                    int randomNum = UtilNum.randomNum(minRobOrFine, maxRobOrFine);

                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), randomNum);
                    DatabaseManager.INSTANCE.setCredits(event.getJDA().getSelfUser().getIdLong(), (-randomNum));

                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("Great Work!");
                    e.setColor(Color.green);
                    e.setDescription("You were given " + " `" + formatter.format(randomNum) + "` for an hour of work.");
                    e.setFooter("Working as a teacher");
                    event.getHook().deleteOriginal().queue();
                    event.deferEdit().queue();
                    event.getChannel().sendMessageEmbeds(e.build()).setActionRow(event.getSelectionMenu().asDisabled()).queue();
                } else {
                    event.getChannel().sendMessage("Correct answer!!!!\n" +
                            "You got \uD83E\uDE99 " + reward + " for getting the correct answer!\n" +
                            "Question: `" + question + "`").queue();
                    LevelPointManager.feed(event.getUser(), 25);
                    DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), reward);
                    event.deferEdit().queue();
                    event.getMessage().delete().queue();
                }
                TriviaCommand.storeAnswer.remove(event.getUser());
            } else {
                if (WorkCommand.job.containsKey(event.getUser())) {

                    UserUserOverkill bankUser = Data.userUserUserOverkillHashMap.get(event.getJDA().getSelfUser());
                    int bankCredits = bankUser.getCredits();

                    int minRobOrFine = 0;
                    int maxRobOrFine = 80_000;

                    if (maxRobOrFine > bankCredits) {
                        maxRobOrFine = bankCredits;
                    }

                    int randomNum = UtilNum.randomNum(minRobOrFine, maxRobOrFine);

                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    DatabaseManager.INSTANCE.setCredits(event.getUser().getIdLong(), randomNum);
                    DatabaseManager.INSTANCE.setCredits(event.getJDA().getSelfUser().getIdLong(), (-randomNum));

                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("TERRIBLE Work!");
                    e.setColor(Color.red);
                    e.setDescription("You lost the mini-game because the answer you chose wasn't correct.\n" +
                            "You were given " + " `" + formatter.format(randomNum) + "` for a sub-par hour of work.");
                    e.setFooter("Working as a teacher");
                    event.getHook().deleteOriginal().queue();
                    event.deferEdit().queue();
                    event.getChannel().sendMessageEmbeds(e.build()).setActionRow(event.getSelectionMenu().asDisabled()).queue();
                } else {
                    EmbedBuilder e = new EmbedBuilder();
                    e.setTitle("Incorrect answer");
                    e.setFooter("A correct answer gives you \uD83E\uDE99 " + reward);
                    e.addField("Question: `" + question + "`\n" + "Difficulty: **" + difficulty +
                            "**\nThe correct answer is " + TriviaCommand.storeAnswer.get(event.getUser()), "Better luck next time", false).setColor(Color.RED);
                    event.getChannel().sendMessageEmbeds(e.build()).queue();
                    event.getMessage().delete().queue();
                    event.deferEdit().queue();
                }
                TriviaCommand.storeAnswer.remove(event.getUser());
                TriviaCommand.storeQuestion.remove(event.getUser());
                TriviaCommand.storeDifficulty.remove(event.getUser());
            }
        }
        while (x < event.getSelectedOptions().size()) {
            switch (event.getSelectedOptions().get(x).getValue()) {
                case "reject":
                    event.getUser().openPrivateChannel().complete().sendMessage("Sorry, you are too young to use this bot! (You shouldn't be on Discord!)").queue();
                    event.getMessage().delete().queue();
                    return;
                case "noice":
                case "oh":
                case "old":
                    embedBuilder = new EmbedBuilder().setTitle("Rules").setColor(InfoUserCommand.randomColor());
                    String arrow = "<a:arrow_1:862525611465113640>";
                    String message = arrow + " THIS IS A CHRISTIAN COMMUNITY SERVER. That means, we value the things Christ teaches us! " + " Let us try our best to exemplify Christlikness in all that we do here! " + "\n" +
                            "\n" +
                            arrow + " THIS GROUP IS FOR HIGH SCHOOL STUDENTS ONLY. High schoolers and Ignite friends (like your Ahyas and Achis) are the only ones allowed to join this server. This is to ensure your safety and security! " + "\n" +
                            "\n" +
                            arrow + " INVITE HIGH SCHOOLERS TO JOIN OUR PROGRAMS! COIL was made to provide an avenue for high school students to connect in a safe, Christian community! Let's help our community grow by inviting your friends to hangout with us! \n" +
                            "\n" +
                            arrow + " BE COURTEOUS IN YOUR SPEECH. Out of the overflow of the heart, the mouth speaks! Let's avoid saying words that hurt others and cause people to stumble! Instead, let us encourage and uplift one another!\n" +
                            "\n" +
                            arrow + " SHOW LOVE TO EVERYONE. In COIL, we do not tolerate bullying of any sort! Kindly make an effort to love one another, even in situations wherein our uniqueness makes it harder for us to do so! Let's make COIL a safe space for everyone to hang!";

                    embedBuilder.setDescription(message);
                    embedBuilder.setFooter("Press the Accept button if you accept the rules stated above!");

                    event.getUser().openPrivateChannel().complete().sendMessageEmbeds(embedBuilder.build()).setActionRow(
                            Button.primary("0000:accept", "Accept").withEmoji(Emoji.fromEmote("verify", 863204252188672000L, true))
                    ).queue();
                    event.getMessage().delete().queue();
                    return;

                case "credit":
                    try {
                        HashMap<User, UserUserOverkill> userUserUserOverkillHashMap = Data.userUserUserOverkillHashMap;
                        ArrayList<User> users = Data.users;
                        ArrayList<UserUserOverkill> userPhoneUsers = new ArrayList<>();
                        int z = 0;
                        while (z < users.size()) {
                            User user = users.get(z);
                            if (userUserUserOverkillHashMap.containsKey(user)) {
                                userPhoneUsers.add(userUserUserOverkillHashMap.get(user));
                            }
                            z++;
                        }

                        List<UserUserOverkill> collectedUsers = userPhoneUsers.stream().sorted(UserUserOverkill::compareTo).collect(Collectors.toList());

                        List<User> toFilter = new ArrayList<>();
                        DecimalFormat formatter = new DecimalFormat("#,###.00");

                        x = 0;
                        while (x < collectedUsers.size()) {
                            toFilter.add(collectedUsers.get(x).getDiscordUser());
                            x++;
                        }

                        Set<User> set = new LinkedHashSet<>(toFilter);

                        toFilter.clear();
                        toFilter.addAll(set);

                        embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("Leaderboard of Credits").setFooter("Who is the richest of the richest?").setTimestamp(OffsetDateTime.now());
                        StringBuilder stringBuilder = new StringBuilder();

                        int y = 0;
                        x = 0;
                        boolean userThere = false;
                        while (y < userUserUserOverkillHashMap.size()) {
                            UserUserOverkill credits = userUserUserOverkillHashMap.get(toFilter.get(y));
                            if (!credits.getDiscordUser().isBot()) {
                                int rank = x + 1;
                                String rankShow;

                                if (rank == 1) {
                                    rankShow = "🥇";
                                } else if (rank == 2) {
                                    rankShow = "🥈";
                                } else if (rank == 3) {
                                    rankShow = "🥉";
                                } else {
                                    rankShow = "🔹";
                                }
                                Integer credits1 = credits.getCredits();
                                stringBuilder.append(rankShow).append(" <:credit:905976767821525042> **").append(formatter.format(credits1)).append("** - ").append(credits.getDiscordUser().getAsTag()).append("\n");

                                if (credits.getDiscordUser().equals(event.getUser())) {
                                    userThere = true;
                                }
                                if (y == 15) {
                                    break;
                                }
                                x++;
                            }
                            y++;
                        }

                        x = 15;
                        y = 15;

                        while (!userThere) {
                            UserUserOverkill credit = userUserUserOverkillHashMap.get(toFilter.get(x));
                            if (!credit.getDiscordUser().isBot()) {
                                y++;
                            }
                            if (credit.getDiscordUser().equals(event.getUser())) {
                                int credits = credit.getCredits();
                                stringBuilder.append("**😎 ").append(y).append(" <:credit:905976767821525042> **").append(formatter.format(credits)).append("** - ").append(credit.getDiscordUser().getAsTag()).append("**");
                                userThere = true;
                            }
                            x++;
                        }

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    event.deferEdit().queue();
                    break;
            }
        }
    }
}