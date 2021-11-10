package com.Overkill10117.command.commands.Events;

import com.Overkill10117.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class OnButtonClick extends ListenerAdapter {
    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        // users can spoof this id so be careful what you do with this
        String[] id = event.getComponentId().split(":"); // this is the custom id we specified in our button
        String authorId = id[0];
        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (id.length == 1) {
            return;
        }

        String type = id[1];
        // When storing state like this is it is highly recommended to do some kind of verification that it was generated by you, for instance a signature or local cache

        if (!authorId.equals("0000") && !authorId.equals(event.getUser().getId())) {
            System.out.println("oof");
            return;
        }

        boolean disableOrEnable = !event.getMember().hasPermission(Permission.MANAGE_SERVER) && !event.getMember().getRoles().contains(event.getGuild().getRoleById(888627140046749697L));

        switch (type) {
            case "end":
                event.getMessage().delete().queue();
                break;
            case "general":
                String prefix = Config.get("prefix");
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(
                        embedBuilder
                        .setTitle("General Commands")
                .setColor(Color.ORANGE)
                                .addField(Config.get("prefix") + "help", "help command", true)
                                .addField(Config.get("prefix") + "invite", "Invite the bot to your server NOW!!", true)

                .setFooter("Type " + prefix + " help [command name] to see what they do")
                        .build()).setActionRows(
                        ActionRow.of(
                                Button.secondary(event.getMember().getUser().getId() + ":general", "General").withEmoji(Emoji.fromEmote("general", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":music", "Music").withEmoji(Emoji.fromEmote("music", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":fun", "Fun").withEmoji(Emoji.fromEmote("fun", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":mod", "Mod").withEmoji(Emoji.fromEmote("mod", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":end", "Cancel").withEmoji(Emoji.fromEmote("end", Long.parseLong("862895295239028756"), true)))).queue();
                break;
            case "music":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(
                        embedBuilder
                                .setTitle("Music Commands")
                                .setColor(Color.ORANGE)
                                .addField(Config.get("prefix") + "join", "Puts the bot in a voice channel", true)
                                .addField(Config.get("prefix") + "leave", "leaves the current voice channel", true)
                                .addField(Config.get("prefix") + "nowplaying", "Shows the currently playing song", true)
                                .addField(Config.get("prefix") + "play", "Plays a song\n" +
                                        "Usage: `,play <youtube link or song name>`", true)
                                .addField(Config.get("prefix") + "queue", "shows the queued up songs", true)
                                .addField(Config.get("prefix") + "repeat", "Loops the current song", true)
                                .addField(Config.get("prefix") + "skip", "skips the current track", true)
                                .addField(Config.get("prefix") + "stop", "Stops the current song and clears the queue", true)


                                .setFooter("Type " + Config.get("prefix") + " help [command name] to see what they do")
                                .build()).setActionRows(
                        ActionRow.of(
                                Button.secondary(event.getMember().getUser().getId() + ":general", "General").withEmoji(Emoji.fromEmote("general", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":music", "Music").withEmoji(Emoji.fromEmote("music", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":fun", "Fun").withEmoji(Emoji.fromEmote("fun", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":mod", "Mod").withEmoji(Emoji.fromEmote("mod", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":end", "Cancel").withEmoji(Emoji.fromEmote("end", Long.parseLong("862895295239028756"), true)))).queue();
                break;
            case "fun":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(
                        embedBuilder
                                .setTitle("Fun Commands")
                                .setColor(Color.ORANGE)
                                .addField(Config.get("prefix") + "meme", "Shows a random meme", true)
                                .addField(Config.get("prefix") + "joke", "Shows a random joke", true)
                                .addField(Config.get("prefix") + "spam", "spam", true)
                                .addField(Config.get("prefix") + "trivia", "a trivia message!!!\n" +
                                        "Usage: `" + Config.get("prefix") + "trivia`", true)
                                .addField(Config.get("prefix") + "8ball", "8ball [question]", true)
                                .addField(Config.get("prefix") + "autospam", "autospam automatically spams on the amount of spams\n" +
                                        "Usage: ,autospam [number] [word]", true)
                                .addField(Config.get("prefix") + "targetspam", "Targets the mentioned user on the dm" +
                                        "Usage: `" + Config.get("prefix") + "targetspam`\n"
                                        + "Parameter: `-h | [Number] @Mention(s)`\n"
                                        + "[Number]: Spam an amount of spams.\n"
                                        + "@Mention(s): Spam spams to mentioned member(s) in dm.\n", true)


                                .setFooter("Type " + Config.get("prefix") + " help [command name] to see what they do")
                                .build()).setActionRows(
                        ActionRow.of(
                                Button.secondary(event.getMember().getUser().getId() + ":general", "General").withEmoji(Emoji.fromEmote("general", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":music", "Music").withEmoji(Emoji.fromEmote("music", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":fun", "Fun").withEmoji(Emoji.fromEmote("fun", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":mod", "Mod").withEmoji(Emoji.fromEmote("mod", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":end", "Cancel").withEmoji(Emoji.fromEmote("end", Long.parseLong("862895295239028756"), true)))).queue();
                break;
            case "mod":
                event.getMessage().delete().queue();
                event.getChannel().sendMessageEmbeds(
                        embedBuilder
                                .setTitle("Moderation Commands")
                                .setColor(Color.ORANGE)
                                .addField(Config.get("prefix") + "about", "About the bot", true)
                                .addField(Config.get("prefix") + "avatar", "giveaway help", true)
                                .addField(Config.get("prefix") + "serverinfo", "Shows info about that server\n" +
                                        "Usage: `,serverinfo`\n" +
                                        "Parameter: `-h | [ID] | null`", true)
                                .addField(Config.get("prefix") + "user", "Get info about the specified user\n" +
                                        "`,user [mentioned member]`", true)
                                .addField(Config.get("prefix") + "mods", "This command is for getting a list of mods in this server."
                                        + "Command Usage: `,mods`\n", true)

                                .setFooter("Type " + Config.get("prefix") + " help [command name] to see what they do")
                                .build()).setActionRows(
                        ActionRow.of(
                                Button.secondary(event.getMember().getUser().getId() + ":general", "General").withEmoji(Emoji.fromEmote("general", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":music", "Music").withEmoji(Emoji.fromEmote("music", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":fun", "Fun").withEmoji(Emoji.fromEmote("fun", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":mod", "Mod").withEmoji(Emoji.fromEmote("mod", Long.parseLong("862895295239028756"), true)),
                                Button.secondary(event.getMember().getUser().getId() + ":end", "Cancel").withEmoji(Emoji.fromEmote("end", Long.parseLong("862895295239028756"), true)))).queue();
                break;
            case "spam":
                event.getChannel().sendMessageEmbeds(
                        embedBuilder
                                .setTitle("spamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspam")
                                .setColor(Color.BLUE)
                                .setDescription("spamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspam")

                                .setFooter("spamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspamspam")
                                .build()).setActionRows(
                        ActionRow.of(
                                Button.secondary(event.getMember().getUser().getId() + ":spam", "spam").withEmoji(Emoji.fromEmote("spam", Long.parseLong("862895295239028756"), true)))).queue();
                break;

        }
    }
    }



