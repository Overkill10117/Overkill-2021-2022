package com.Overkill10117.command.commands.currency;

import com.Overkill10117.command.Database.DatabaseManager;
import com.Overkill10117.command.Database.SQLiteDataSource;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserUserOverkill implements Comparable <UserUserOverkill>{
    private String realName;
    private Integer balance;
    private Integer credits;

    private final User discordUser;

    public String getRealName() {
        return realName;
    }

    public UserUserOverkill (String userUserUserOverkillName, User discordUser, Integer credits) {
        this.realName = userUserUserOverkillName;
        this.discordUser = discordUser;
        this.credits = credits;
    }

    public String getUserUserOverkillName() {
        return realName;
    }

    public void setUserUserOverkillName(String userPhoneUserName) {
        this.realName = userPhoneUserName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getCredits() {
        int credits = DatabaseManager.INSTANCE.getCredits(discordUser.getIdLong());
        this.credits = credits;
        return credits;
    }

    public static int getCredits2(long userId) {
        try (Connection connection = SQLiteDataSource.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT Shekels FROM RPGData WHERE UserId = ?")) {

            preparedStatement.setString(1, String.valueOf(userId));

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Shekels");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void addShekels(long userId, int credits) {
        int total = (credits) + getCredits2(userId);

        try (final PreparedStatement preparedStatement = SQLiteDataSource.getConnection()
                .prepareStatement("UPDATE RPGData SET Shekels=? WHERE UserId=?"
                )) {

            preparedStatement.setString(2, String.valueOf(userId));
            preparedStatement.setString(1, String.valueOf(total));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public User getDiscordUser() {
        return discordUser;
    }

    public int compareTo(UserUserOverkill upu){
        return upu.credits - this.credits;
    }
}

