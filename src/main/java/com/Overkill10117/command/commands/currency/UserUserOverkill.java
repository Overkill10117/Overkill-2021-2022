package com.Overkill10117.command.commands.currency;

import com.Overkill10117.command.Database.DatabaseManager;
import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

public class UserUserOverkill implements Comparable <UserUserOverkill>{
    private String userPhoneUserName;
    private Integer balance;
    private Integer credits;

    private final User discordUser;

    public UserUserOverkill (String userPhoneUserName, User discordUser, Integer credits) {
        this.userPhoneUserName = userPhoneUserName;
        this.discordUser = discordUser;
        this.credits = credits;
    }

    public String getUserUserOverkillName() {
        return userPhoneUserName;
    }

    public void setUserUserOverkillName(String userPhoneUserName) {
        this.userPhoneUserName = userPhoneUserName;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getCredits() {
        int credits = DatabaseManager.INSTANCE.getCredits(discordUser.getIdLong());
        this.credits = credits;
        return credits;
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
