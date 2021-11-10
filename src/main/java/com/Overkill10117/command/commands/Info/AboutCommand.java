package com.Overkill10117.command.commands.Info;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class AboutCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("About the Bot")
                .addField("Creator:", "`Overkill#9510`", true)
                .addField("About: ", "Bot of Overkill#9510 made for entertainment and fun ", true)
                .addField("Other Bots", "[Overkill](https://discord.com/api/oauth2/authorize?client_id=775257176050237480&permissions=8&scope=bot)[Overkill Beta](https://discord.com/api/oauth2/authorize?client_id=775266479725740032&permissions=8&redirect_uri=https%3A%2F%2Fmee6.xyz%2Fdashboard%2F769175521971798036%2Freaction_roles&scope=bot) [Spam Bot](https://discord.com/api/oauth2/authorize?client_id=795555997337780224&permissions=8&scope=bot) [Spam Bot 2](https://discord.com/api/oauth2/authorize?client_id=783925909987131402&permissions=8&scope=bot) [Ultimate Spam Bot](https://discord.com/api/oauth2/authorize?client_id=796183759128100864&permissions=8&scope=bot) [Spam Bot Special](https://discord.com/oauth2/authorize?client_id=816125801694756874&permissions=8&scope=bot)", true)
                .setDescription("BOT")
                .setAuthor("Overkill#9150", null, "https://images-ext-1.discordapp.net/external/_mIPz7hzzWKJmDEr8lJq0SEAssFR7H2knGgXnbMS3qw/%3Fsize%3D1024/https/cdn.discordapp.com/avatars/766501021144711178/4aa6cd65d0cb9c2f0ac2cc74318bdf71.png?width=325&height=325")
                .setThumbnail("https://images-ext-1.discordapp.net/external/_mIPz7hzzWKJmDEr8lJq0SEAssFR7H2knGgXnbMS3qw/%3Fsize%3D1024/https/cdn.discordapp.com/avatars/766501021144711178/4aa6cd65d0cb9c2f0ac2cc74318bdf71.png?width=325&height=325")
                .setColor(Color.blue);
        ctx.getChannel().sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getHelp() {
        return "About Stuff";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MOD;
    }
}