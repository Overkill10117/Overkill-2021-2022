package com.Overkill10117.command.commands.currency;

import net.dv8tion.jda.api.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class MoneyData {
    public static HashMap<User, Double> money = new HashMap<>();
    public static HashMap<User, LocalDateTime> time = new HashMap<>();
    public static ArrayList<User> users = new ArrayList<>();
}
