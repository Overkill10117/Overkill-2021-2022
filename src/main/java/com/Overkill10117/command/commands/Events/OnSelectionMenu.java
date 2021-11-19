package com.Overkill10117.command.commands.Events;

import com.Overkill10117.command.commands.Fun.TriviaCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SelectionMenuEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class OnSelectionMenu extends ListenerAdapter {
    @Override
    public void onSelectionMenu(@NotNull SelectionMenuEvent event) {
        EmbedBuilder embedBuilder;
        System.out.println(event.getSelectedOptions().get(0).getValue());
        if (TriviaCommand.storeAnswer.containsKey(event.getUser())) {
            String answer = TriviaCommand.storeAnswer.get(event.getUser());
            String question = TriviaCommand.storeQuestion.get(event.getUser());
            String difficulty = TriviaCommand.storeDifficulty.get(event.getUser());

            if (event.getSelectedOptions().get(0).getValue().equals(answer)) {
                event.reply("Correct answer!!!!\n" +
                        "You got \uD83E\uDE99for getting the correct answer!\n" +
                        "Question: `" + question + "`").queue();
                event.deferEdit().queue();
                event.getMessage().delete().queue();
                TriviaCommand.storeAnswer.remove(event.getUser());
            } else {
                EmbedBuilder e = new EmbedBuilder();
                e.setTitle("Incorrect answer");
                e.setFooter("A correct answer gives you \uD83E\uDE99 ");
                e.addField("Question: `" + question + "`\n" + "Difficulty: **" + difficulty +
                        "**\nThe correct answer is " + TriviaCommand.storeAnswer.get(event.getUser()), "Better luck next time", false).setColor(Color.RED);
                event.replyEmbeds(e.build()).queue();
                event.getMessage().delete().queue();
                TriviaCommand.storeAnswer.remove(event.getUser());
                TriviaCommand.storeQuestion.remove(event.getUser());
                TriviaCommand.storeDifficulty.remove(event.getUser());

                event.deferEdit().queue();
            }
        }
    }
}