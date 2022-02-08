package com.Overkill10117.command.commands.currency.Levels;

import com.Overkill10117.command.Database.DatabaseManager;
import com.Overkill10117.command.commands.currency.GetData;
import net.dv8tion.jda.api.entities.User;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.function.Function;

public class LevelPointManager{

    private static final int POINTS_PER_MESSAGE = 15;
    //in seconds
    private static final int DELAY = 20;
    private static final Function<Long, Long> CALCULATE_LEVEL = ep -> (long) (1 / (float) (8) * Math.sqrt(ep));
    private static final Function<Long, Long> CALCULATE_EP = level -> (long) 64 * (long) Math.pow(level, 2);

    public static final HashMap<User, OffsetDateTime> accessMap = new HashMap<>();

    public static long calculateLevel(User member) throws SQLException {
        return calculateLevel(GetData.getLevelPoints(member));
    }

    public static long calculateLevel(long levelPoints){
        return CALCULATE_LEVEL.apply(levelPoints);
    }

    public static long calculateLevelMin(long level){
        return CALCULATE_EP.apply(level);
    }

    public static long calculateLevelMax(long level){
        return CALCULATE_EP.apply(level + 1);
    }

    public static LevelPointCard getLevelPointCard(User member) throws SQLException {
        return new LevelPointCard(member);
    }

    public static void trackMember(User member) {
        try {
            OffsetDateTime min = OffsetDateTime.MIN;

            accessMap.put(member, min);
        } catch (Exception ignore) {

        }
    }

    public static synchronized void feed(User member){
        try{
            if(!accessMap.containsKey(member)){
                trackMember(member);
            }

            OffsetDateTime last = accessMap.get(member);
            if(OffsetDateTime.now().isBefore(last.plusSeconds(DELAY))){
                return;
            }


            accessMap.put(member, OffsetDateTime.now());

            GetData.setLevelPoints(member, GetData.getLevelPoints(member) + POINTS_PER_MESSAGE);
        }
        catch(Exception ignore){
        }
    }

    public static synchronized void feed(User member, long xpToAdd){
        try{
            if(!accessMap.containsKey(member)){
                trackMember(member);
            }
            long level = calculateLevel(member);

            GetData.setLevelPoints(member, GetData.getLevelPoints(member) + xpToAdd);
            long nowlevel = calculateLevel(member);

            if (level < nowlevel) {
                member.openPrivateChannel().queue((privateChannel -> {
                    long nowlevel1 = nowlevel*1000;
                    privateChannel.sendMessage("Congratulation! You are now in level " + nowlevel + "!!! **" + nowlevel1 + " credits** has been added to you!!!").queue();
                    DatabaseManager.INSTANCE.setCredits(member.getIdLong(), (int) nowlevel1);
                }));
            }
        }
        catch(Exception ignore){
        }
    }
}
