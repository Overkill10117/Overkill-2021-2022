package com.Overkill10117.command.commands.currency;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.Database.DatabaseManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetData {
    public static ArrayList<User> blackListedUser = new ArrayList<>();
    public static HashMap<User, Long> xpMember = new HashMap<>();
    //get data from the db

    public void checkIfContainsData(User user, GuildMessageReceivedEvent ctx) {
        if (!Data.userUserUserOverkillHashMap.containsKey(user)) {
            retrieveData(user.getIdLong(), ctx);
        }

    }

    public void checkIfContainsData(User user, SlashCommandEvent ctx) {
        if (!Data.userUserUserOverkillHashMap.containsKey(user)) {
            retrieveData(user.getIdLong(), ctx);
        }
    }

    public void checkIfContainsData(User user, CommandContext ctx) {
        if (!Data.userUserUserOverkillHashMap.containsKey(user)) {
            retrieveData(user.getIdLong(), ctx);
        }
    }

    public void checkIfContainsData(Guild guild, GuildMessageReceivedEvent ctx) {
        int x = 0;
        List<Member> members = guild.getMembers();

        while (x < members.size()) {
            Member user = members.get(x);
            Data.users.add(members.get(x).getUser());

            if (!Data.userUserUserOverkillHashMap.containsKey(user)) {
                retrieveData(user.getIdLong(), ctx);
            }

            x++;
        }
    }

    public static long getLevelPoints(User member) throws SQLException {
        if (xpMember.containsKey(member)) {
            return xpMember.get(member);
        }

        return DatabaseManager.INSTANCE.getXpPoints(member.getIdLong());
    }

    public static void setLevelPoints(User user, long points) {
        DatabaseManager.INSTANCE.setXpPoints(user.getIdLong(), points);
        xpMember.put(user, points);
    }

    public static void setName(User user, String name) {
        DatabaseManager.INSTANCE.setName(user.getIdLong(), name);
    }
    private int retrieveData(Long userId, GuildMessageReceivedEvent ctx) {
        if (blackListedUser.contains(ctx.getAuthor())) return -1;

        String name = DatabaseManager.INSTANCE.getName(userId);

        return addData(userId, name, ctx.getJDA());
    }

    private int retrieveData(Long userId, CommandContext ctx) {
        String name = DatabaseManager.INSTANCE.getName(userId);

        return addData(userId, name, ctx.getJDA());
    }

    private int addData(Long userId, String name, JDA jda) {
        if (name != null) {
            User userById = jda.getUserById(userId);

            int credits = DatabaseManager.INSTANCE.getCredits(userId);
            UserUserOverkill user = new UserUserOverkill(name, userById, credits);
            Data.userUserUserOverkillHashMap.put(userById, user);
            Data.realNameUserUserOverkillHashMap.put(name, user);
            Data.UserUserOverkill.add(user);
            try {
                List<List<Object>> lists = UpdateCoinsCommand.loadData();
                if (lists == null) return -1;

                UpdateCoinsCommand.loadData();
            } catch (IOException e) {
                return -1;
            }
            return 0;
        }

        return -1;
    }

    private int retrieveData(Long userId, SlashCommandEvent ctx) {
        if (blackListedUser.contains(ctx.getUser())) return -1;

        String name = DatabaseManager.INSTANCE.getName(userId);


        return addData(userId, name, ctx.getJDA());
    }
}
