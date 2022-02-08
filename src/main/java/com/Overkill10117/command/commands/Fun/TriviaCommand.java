package com.Overkill10117.command.commands.Fun;

import Requests.OpenTDB;
import com.Overkill10117.Config;
import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import com.Overkill10117.command.commands.Utils.UtilNum;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.selections.SelectionMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TriviaCommand implements ICommand {
    public static HashMap<User, String> storeAnswer = new HashMap<>();
    public static HashMap<User, String> storeQuestion = new HashMap<>();
    public static HashMap<User, String> storeDifficulty = new HashMap<>();

    @Override
    public void handle(CommandContext ctx) {
        try {
            OpenTDB obj = new OpenTDB();
            if (!ctx.getArgs().isEmpty()) {
                obj.setDifficulty(ctx.getArgs().get(0).toLowerCase());
            }

            obj.getTrivia();

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
            embedBuilder.setTitle("Trivia!!!");
            embedBuilder.addField("Category: ", obj.getCategory(), true);
            embedBuilder.addField("Difficulty: ", obj.getDifficulty(), true);
            embedBuilder.addField("Question: ", ctx.getAuthor().getAsMention() + " " + msg, false);
            embedBuilder.setColor(Color.cyan);
            embedBuilder.setFooter("A correct answer will give you at least 1,000 credits!!!");
            ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(menu.build()).queue();
            storeQuestion.put(ctx.getAuthor(), msg);
            storeDifficulty.put(ctx.getAuthor(), obj.getDifficulty());
            storeAnswer.put(ctx.getAuthor(), obj.getCorrectAnswer().replace("&quot;", "'").replace("&#039;", "'").replace("&Uuml;", "ü").replace("&amp;", "&"));
        } catch (Exception e) {
            ctx.getChannel().sendMessage("The only options are easy, medium, and hard!").queue();
        }
    }


    @Override
    public String getName() {
        return "trivia";
    }

    @Override
    public String getHelp() {
        return "Makes the bot send a trivia message!!!\n" +
                "Usage: `" + Config.get("prefix") + "trivia`";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.FUN;
    }
}