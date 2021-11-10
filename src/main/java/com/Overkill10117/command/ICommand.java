package com.Overkill10117.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException;

    String getName();

    String getHelp();

    CommandType getCategory();

    default List<String> getAliases() {
        return new ArrayList<>();
    }
}
