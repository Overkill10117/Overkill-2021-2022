package com.Overkill10117.command.commands.Mod;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class GiveawayCommand implements ICommand {
    int seconds;
    Message message;
    String item;
    public void Giveawayy(int time, Message message, String item)
    {
        seconds = time;
        this.message = message;
        this.item = item;
    }


    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getMessage().getContentRaw().startsWith(",create"))
        {

            String str = ctx.getMessage().getContentRaw().substring(7).trim();
            String[] parts = str.split("\\s+",2);
            try{
                int sec = Integer.parseInt(parts[0]);
                String madeby = String.format("Made By %s", ctx.getMessage().getAuthor().getAsTag());
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(":tada:  **GIVEAWAY!**  :tada:\n")
                        .setDescription((parts.length>1 ? "\u25AB*`"+parts[1]+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter! Time Remaining: "+ secondsToTime(seconds))
                        .setFooter(madeby);
                ctx.getChannel().sendMessage(embed.build()).queue(m -> {
                    m.addReaction("\uD83C\uDF89").queue();
                    new Giveaway(sec,m,parts.length>1 ? parts[1] : null).start();
                });
                ctx.getMessage().delete().queue();
            } catch(NumberFormatException ex)
            {
                ctx.getChannel().sendMessage("Could not parse seconds from `"+parts[0]+"`").queue();
            }
        }
        else if(ctx.getAuthor().getId().equals("OWNERID"))
        {
            if(ctx.getMessage().getContentRaw().startsWith("!say"))
                ctx.getChannel().sendMessage(ctx.getMessage().getContentRaw().substring(4).trim()).queue();
            else if(ctx.getMessage().getContentRaw().startsWith("!ava"))
            {
                String loc = ctx.getMessage().getContentRaw().substring(4).trim();
                try{
                    ctx.getJDA().getSelfUser().getManager().setAvatar(Icon.from(new File(loc))).complete();
                    ctx.getChannel().sendMessage("Updated!").queue();
                }catch(Exception ex){
                    ctx.getChannel().sendMessage("Error: "+ex).queue();
                }
            }
        }
    }

    @Override
    public String getName() {
        return "create";
    }


    @Override
    public String getHelp() {
        return "<:tada:294906617378504704> Giveaway help: <:tada:294906617378504704>\n"
                + "`,create <seconds> [item]` - starts a giveway. Ex: `,create 180` for a 3 minute giveaway\n"
                + "`,reroll <messageid>` - rerolls a winner for the giveaway on the provided message\n\n"
                + "Commands require Manage Server permission to use\n"
                + "Don't include <> nor []; <> means required, [] means optional";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.MOD;
    }


    public static class Giveaway {

        int seconds;
        Message message;
        String item;
        public Giveaway(int time, Message message, String item)
        {
            seconds = time;
            this.message = message;
            this.item = item;
        }

        public void start()
        {
            new Thread(){

                @Override
                public void run() {
                    String madeby = String.format("Made By %s", message.getAuthor().getAsTag());
                    while(seconds>5)
                    {EmbedBuilder em = new EmbedBuilder()
                            .setTitle("<:tada:294906617378504704>  **GIVEAWAY!**  <:tada:294906617378504704>\n")
                            .setDescription((item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds))
                            .setFooter(madeby);
                        message.editMessage(em.build()).queue();
                        seconds-=5;
                        try{Thread.sleep(5000);}catch(Exception e){}
                    }
                    while(seconds>0)
                    {
                        EmbedBuilder em = new EmbedBuilder()
                                .setDescription((item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds))
                                .setTitle("<:tada:294906617378504704> **G I V E A W A Y!** <:tada:294906617378504704>\nLAST CHANCE TO ENTER!!!\n")
                                .setFooter(madeby);
                        message.editMessage(em.build()).queue();
                        seconds--;
                        try{Thread.sleep(1000);}catch(Exception e){}
                    }
                    message.getChannel().retrieveMessageById(message.getId()).complete().getReactions()
                            .stream().filter(mr -> mr.getReactionEmote().getName().equals("\uD83C\uDF89"))
                            .findAny().ifPresent(mr -> {
                                List<User> users = message.retrieveReactionUsers("\uD83C\uDF89").complete();
                                users.remove(message.getJDA().getSelfUser());
                                String id = users.get((int)(Math.random()*users.size())).getId();


                                EmbedBuilder em = new EmbedBuilder()
                                        .setDescription((item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"\nWinner: <@"+id+"> \uD83C\uDF89 in " + message.getGuild().getName())
                                        .setTitle("<:tada:294906617378504704> **GIVEAWAY ENDED!** <:tada:294906617378504704>\n")
                                        .setFooter(madeby);
                                message.editMessage(em.build()).queue();
                                EmbedBuilder emm = new EmbedBuilder()
                                        .setDescription("Congratulations to <@" + id + ">! You won" + (item == null ? "" : " the " + item) + "! in " + message.getGuild().getName())
                                        .setTitle("<:tada:294906617378504704> **GIVEAWAY ENDED!** <:tada:294906617378504704>\n")
                                        .setFooter(madeby);
                                message.getChannel().sendMessage(emm.build()).queue();
                            });
                }
            }.start();
        }
    }

    public static String secondsToTime(long timeseconds)
    {
        StringBuilder builder = new StringBuilder();
        int years = (int)(timeseconds / (60*60*24*365));
        if(years>0)
        {
            builder.append("**").append(years).append("** years, ");
            timeseconds = timeseconds % (60*60*24*365);
        }
        int weeks = (int)(timeseconds / (60*60*24*365));
        if(weeks>0)
        {
            builder.append("**").append(weeks).append("** weeks, ");
            timeseconds = timeseconds % (60*60*24*7);
        }
        int days = (int)(timeseconds / (60*60*24));
        if(days>0)
        {
            builder.append("**").append(days).append("** days, ");
            timeseconds = timeseconds % (60*60*24);
        }
        int hours = (int)(timeseconds / (60*60));
        if(hours>0)
        {
            builder.append("**").append(hours).append("** hours, ");
            timeseconds = timeseconds % (60*60);
        }
        int minutes = (int)(timeseconds / (60));
        if(minutes>0)
        {
            builder.append("**").append(minutes).append("** minutes, ");
            timeseconds = timeseconds % (60);
        }
        if(timeseconds>0)
            builder.append("**").append(timeseconds).append("** seconds");
        String str = builder.toString();
        if(str.endsWith(", "))
            str = str.substring(0,str.length()-2);
        if(str.equals(""))
            str="**No time**";
        return str;
    }
}
