package com.Overkill10117.command.commands.currency.Work;

import Requests.OpenTDB;
import com.Overkill10117.Bot;
import com.Overkill10117.command.commands.Fun.TriviaCommand;
import com.Overkill10117.command.commands.Utils.UtilNum;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;
import org.jline.utils.Colors;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Job {
    private final QuestionType questionType;
    private final TextChannel textChannel;
    private final User user;

    public Job(User user, TextChannel textChannel) {
        this.user = user;
        this.questionType = chooseQuestionType();
        this.textChannel = textChannel;
    }

    public TextChannel getTextChannel() {
        return textChannel;
    }

    public User getUser() {
        return user;
    }

    private QuestionType chooseQuestionType() {
        int randomNum = UtilNum.randomNum(0, 100);

        return randomNum < 50 ? QuestionType.TRIVIA : QuestionType.COLOR_MATCHING;
    }

    public void makeAndSendQuestion() {
        if (this.questionType.equals(QuestionType.TRIVIA)) {
            trivia(this.textChannel);
            return;
        }

        colorGame(this.textChannel);
    }

    private void trivia(TextChannel textChannel) {
        while (true) {
            try {
                OpenTDB obj = new OpenTDB();
                obj.setDifficulty("hard");
                obj.getTrivia();
                while (!obj.getType().equals("multiple")) {
                    obj.getTrivia();
                }

                String[] incorrectAnswers = obj.incorrectAnswers;

                SelectionMenu.Builder menu = SelectionMenu.create("menu:class")
                        .setPlaceholder("Choose the correct answer") // shows the placeholder indicating what this menu is for
                        .setRequiredRange(1, 1);

                int x = 0;
                ArrayList<String> arrayList = new ArrayList<>();

                while (x < incorrectAnswers.length) {
                    arrayList.add(incorrectAnswers[x]);
                    x++;
                }

                x = 0;

                arrayList.add(obj.getCorrectAnswer());
                int size = arrayList.size();
                while (x < size) {
                    int random = UtilNum.randomNum(0, size - 1 - (x));
                    String choice = arrayList.get(random).replace("&quot;", "'").replace("&#039;", "'").replace("&Uuml;", "ü").replace("&amp;", "&");

                    menu.addOption(choice, choice);
                    arrayList.remove(choice);

                    x++;
                }

                String msg = obj.getQuestion().replace("&quot;", "'").replace("&#039;", "'").replace("&Uuml;", "ü").replace("&amp;", "&");
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setAuthor(this.user.getName() + "'s trivia question", null, this.user.getAvatarUrl());
                embedBuilder.setDescription("**" + msg + "**");
                embedBuilder.addField("Difficulty", "`" + obj.getDifficulty() + "`", true);
                embedBuilder.addField("Category", "`" + obj.getCategory() + "`", true);
                embedBuilder.setColor(Color.pink);
                embedBuilder.setFooter("Giving a correct answer will make this hour's work a success!!!").setTimestamp(OffsetDateTime.now());
                textChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(menu.build()).queue();

                TriviaCommand.storeQuestion.put(this.user, msg);
                TriviaCommand.storeDifficulty.put(this.user, obj.getDifficulty());
                TriviaCommand.storeAnswer.put(this.user, obj.getCorrectAnswer().replace("&quot;", "'").replace("&#039;", "'").replace("&Uuml;", "ü").replace("&amp;", "&"));
                return;
            } catch (Exception ignored) {
            }
        }
    }

    private void colorGame(TextChannel textChannel) {
        try {
            int x = 0;
            StringBuilder question = new StringBuilder();
            ArrayList<Button> buttons = new ArrayList<>();
            ArrayList<com.Overkill10117.command.commands.currency.Work.Colors> colors = new ArrayList<>();
            String color = null;

            int a = UtilNum.randomNum(0, 3);
            while (x < 4) {
                com.Overkill10117.command.commands.currency.Work.Colors randomColor = randomColor();
                while (true) {
                    if (colors.contains(randomColor)) {
                        randomColor = randomColor();
                    } else {
                        break;
                    }
                }

                colors.add(randomColor);
                String[] randomWords = randomWord().split("\\s+");
                String randomWord = randomWords[0];
                question.append(randomColor.getEmoji()).append(" ")
                        .append(randomColor.getName()).append(" - ")
                        .append(randomWord).append("\n");

                if (a == x) {
                    buttons.add(Button.secondary("0000:color:correct", randomWord));
                    color = randomColor.getName();
                } else {
                    buttons.add(Button.secondary("0000:color:wrong:" + x, randomWord));
                }
                x++;
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Memory Game");
            embedBuilder.setDescription(question);
            embedBuilder.setFooter("Remember this text!").setColor(Color.ORANGE);
            String finalColor = color;
            textChannel.sendMessageEmbeds(embedBuilder.build()).queue(
                    (message -> {
                        EmbedBuilder embedBuilder1 = new EmbedBuilder();
                        embedBuilder1.setAuthor("Which among the words below matches the color of " + finalColor, null, Bot.jda.getSelfUser().getAvatarUrl());
                        message.editMessageEmbeds(embedBuilder1.build()).setActionRow(buttons).queueAfter(5, TimeUnit.SECONDS);
                    })
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String randomWord() {
        OpenTDB obj = new OpenTDB();
        obj.getTrivia();

        while (!obj.getType().equals("multiple")) {
            obj.getTrivia();
        }

        String[] incorrectAnswers = obj.getIncorrectAnswers();

        int randomNumber = UtilNum.randomNum(0, incorrectAnswers.length-1);
        return incorrectAnswers[randomNumber].replace("&quot;", "'").replace("&#039;", "'").replace("&Uuml;", "ü").replace("&amp;", "&");
    }

    private com.Overkill10117.command.commands.currency.Work.Colors randomColor() {
        int randomNum = UtilNum.randomNum(1, 6);

        switch (randomNum) {
            case 1:
                return com.Overkill10117.command.commands.currency.Work.Colors.RED;
            case 2:
                return com.Overkill10117.command.commands.currency.Work.Colors.BLUE;
            case 3:
                return com.Overkill10117.command.commands.currency.Work.Colors.GREEN;
            case 4:
                return com.Overkill10117.command.commands.currency.Work.Colors.YELLOW;
            case 5:
                return com.Overkill10117.command.commands.currency.Work.Colors.BLACK;
            case 6:
                return com.Overkill10117.command.commands.currency.Work.Colors.WHITE;
        }

        return com.Overkill10117.command.commands.currency.Work.Colors.BLACK;
    }
}
