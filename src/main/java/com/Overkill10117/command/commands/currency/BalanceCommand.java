package com.Overkill10117.command.commands.currency;

import com.Overkill10117.Config;
import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.ButtonStyle;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BalanceCommand implements ICommand {
    public static HashMap<Long, Integer> dataInTheSky = new HashMap<>();
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {
        User user = ctx.getAuthor();


        if (!ctx.getMessage().getMentionedMembers().isEmpty()) {
            user = ctx.getMessage().getMentionedUsers().get(0);
        }

        GetData getData = new GetData();
        getData.checkIfContainsData(user, ctx);

        UpdateCoinsCommand.loadData();

        UserUserOverkill userPhoneUser = Data.userUserUserOverkillHashMap.get(user);

        if (userPhoneUser == null) {
            ctx.getChannel().sendMessage("Kindly register with the bot by using `,register`").queue();
            return;
        }

        Integer balance;

        try {
            balance = userPhoneUser.getBalance();
        } catch (Exception e) {
            balance = null;
        }
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);
        embedBuilder.setThumbnail("https://images-ext-2.discordapp.net/external/dSmBwljIOT0xUFDxjc5_KNUZx9g3dNviWTCkCZ6oleY/https/cdn.discordapp.com/emojis/718138332982280272.gif");
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        embedBuilder.setTitle(user.getName() + "'s Balance").setFooter("Hohoho Merry Christmas 🎄");
        embedBuilder.setDescription(" Ignite Coins: **" + (balance == null ? "You are not in the coin database**\n" :"Press the button for your ignite coins**\n") +
                 " Credits: ** " + formatter.format(userPhoneUser.getCredits()) + " credits**\n");
        ctx.getChannel().sendMessageEmbeds(embedBuilder.build()).setActionRow(Button.of(ButtonStyle.PRIMARY, user.getIdLong() + ":balance", "View Coins").withEmoji(Emoji.fromEmote("ignt_coins", 905999722374905857L, false)).withDisabled(balance == null)).queue();

        if (balance != null) {
            dataInTheSky.put(user.getIdLong(), balance);
        }
    }

    @Override
    public CommandType getCategory() {
        return CommandType.FUN;
    }

    @Override
    public String getName() {
        return "bal";
    }

    @Override
    public String getHelp() {
        return "Shows your ignite coins!\n" +
                "Example: `" + Config.get("prefix") + getName() + "`";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();
        aliases.add("balance");
        aliases.add("coins");
        aliases.add("coin");
        return aliases;
    }
}
