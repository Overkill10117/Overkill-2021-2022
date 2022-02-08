package com.Overkill10117.command.commands.currency;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import com.Overkill10117.command.commands.Utils.EmbedUtil;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UpdateCoinsCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) throws InterruptedException, IOException, SQLException {

        loadData();

        ctx.getChannel().sendMessageEmbeds(EmbedUtil.successEmbed("Successfully updated all users balance!")).queue();
    }

    @Override
    public String getName() {
        return "updatecoins";
    }

    @Override
    public String getHelp() {
        return "Updates all the balances of all the users!";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.FUN;
    }

    public static List<List<Object>> loadData() throws IOException {
        String APPLICATION_NAME = "Overkill10117";

        NetHttpTransport HTTP_TRANSPORT;
        // Build a new authorized API client service.
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            return null;
        }
        final String spreadsheetId = "1YHa3CJufkw0XujsQYBpR6fKZJmMQl-uNF_ixwbef9mI";
        final String range = "Ref Sheet TRIAL!B3:F156";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, SheetsQuickstart.JSON_FACTORY, SheetsQuickstart.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                String name = row.get(0).toString();
                String[] split = name.split("\\s+");
                if (split.length > 1) {

                    int length = split.length;

                    if (length == 3) {
                        name = split[0] + " " + split[2];
                    } else {
                        name = split[0] + " " + split[1];
                    }

                    if (Data.realNameUserUserOverkillHashMap.containsKey(name)) {
                        UserUserOverkill userPhoneUser = Data.realNameUserUserOverkillHashMap.get(name);
                        userPhoneUser.setBalance(Integer.parseInt(row.get(4).toString()));
                        Data.realNameUserUserOverkillHashMap.remove(name);
                        Data.realNameUserUserOverkillHashMap.put(name, userPhoneUser);
                    }
                }
            }
        }

        return values;
    }
}
