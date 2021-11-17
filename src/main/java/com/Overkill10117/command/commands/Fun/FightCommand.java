package com.Overkill10117.command.commands.Fun;

import com.Overkill10117.command.CommandContext;
import com.Overkill10117.command.CommandType;
import com.Overkill10117.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.concurrent.TimeUnit;

public class FightCommand implements ICommand {
    int healthChallenger = 100;
    int healthVictim = 100;
    TextChannel channel;
    User userChallenger;
    User userVictim;
    String victim;
    String challenger;


    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        userChallenger = ctx.getAuthor();
        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            ctx.getChannel().sendMessage("You did not mention who to fight with").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().get(0).equals(ctx.getMember())) {
            ctx.getChannel().sendMessage("How do you fight yourself?").queue();
            return;
        }

        userVictim = ctx.getMessage().getMentionedMembers().get(0).getUser();

        challenger = ctx.getMember().getAsMention();
        victim = ctx.getMessage().getMentionedMembers().get(0).getAsMention();
        ctx.getChannel().sendMessage("Fight begins in 3").queue((message -> {
            String content = "Fight begins in 2";
            message.editMessage(content).queueAfter(1, TimeUnit.SECONDS);
            content = "Fight begins in 1";
            message.editMessage(content).queueAfter(2, TimeUnit.SECONDS);
            content = "Fight begins in 0";
            message.editMessage(content).queueAfter(3, TimeUnit.SECONDS);
            int x = 4;
            if (random(0,1) == 1) {
                content = challenger + randomAttack(healthVictim, victim, false);
                if (message.editMessage(content).queueAfter(x, TimeUnit.SECONDS).isDone()) {
                    x += 0;

                    if (win(healthVictim, healthChallenger, challenger, true)) {
                        content = victim + randomAttack(healthChallenger, challenger, true);
                        if (message.editMessage(content).queueAfter(0, TimeUnit.SECONDS).isDone()) {
                            x += 0;
                            if (win(healthChallenger, healthVictim, victim, false)) {
                                content = challenger + randomAttack(healthVictim, victim, false);
                                message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                                x += 0;
                                if (win(healthVictim, healthChallenger, challenger, true)) {
                                    content = victim + randomAttack(healthChallenger, challenger, true);
                                    message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                                    x += 0;
                                    if (win(healthChallenger, healthVictim, victim, false)) {
                                        content = challenger + randomAttack(healthVictim, victim, false);
                                        message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                                        x += 0;
                                        if (win(healthVictim, healthChallenger, challenger, true)) {
                                            content = victim + randomAttack(healthChallenger, challenger, true);
                                            message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                                            x += 0;
                                            if (win(healthChallenger, healthVictim, victim, false)) {
                                                if (healthVictim > healthChallenger) {
                                                    channel.sendMessage(victim + " wins with " + (healthVictim - healthChallenger) + " health more than " + challenger).queue();
                                                } else if (healthVictim < healthChallenger) {
                                                    channel.sendMessage(challenger + " wins with " + (healthChallenger - healthVictim) + " health more than " + victim).queue();
                                                } else {
                                                    channel.sendMessage("It's a tie!!!").queue();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                content = victim + randomAttack(healthChallenger, challenger, true);
                message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                x += 3;

                if (win(healthChallenger, healthVictim, victim, false)) {
                    content = challenger + randomAttack(healthVictim, victim, false);
                    message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                    x += 3;
                    if (win(healthVictim, healthChallenger, challenger, true)) {
                        content = victim + randomAttack(healthChallenger, challenger, true);
                        message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                        x += 3;
                        if (win(healthChallenger, healthVictim, victim, false)) {
                            content = challenger + randomAttack(healthVictim, victim, false);
                            message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                            x += 3;
                            if (win(healthVictim, healthChallenger, challenger, true)) {
                                content = victim + randomAttack(healthChallenger, challenger, true);
                                message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                                x += 3;
                                if (win(healthChallenger, healthVictim, victim, false)) {
                                    content = challenger + randomAttack(healthVictim, victim, false);
                                    message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                                    x += 3;
                                    if (win(healthVictim, healthChallenger, challenger, true)) {
                                        content = victim + randomAttack(healthChallenger, challenger, true);
                                        message.editMessage(content).queueAfter(x, TimeUnit.SECONDS);
                                        x += 3;
                                        if (win(healthChallenger, healthVictim, victim, false)) {
                                            if (healthVictim > healthChallenger) {
                                                channel.sendMessage(victim + " wins with " + (healthVictim - healthChallenger) + " health more than " + challenger).queue();
                                            } else if (healthVictim < healthChallenger) {
                                                channel.sendMessage(challenger + " wins with " + (healthChallenger - healthVictim) + " health more than " + victim).queue();
                                            } else {
                                                channel.sendMessage("It's a tie!!!").queue();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }));
    }


    public int random (int start, int finish) {
        return (int)(Math.random() * (finish - start + 1) + start);
    }

    public String randomAttack (int health, String victim, boolean isChallenger) {
        String message = "Error";
        switch (random(1, 8)) {
            case 1:
                health = health - 8;
                message =  " did a awesome punch at " + victim + "'s face that subtracted 8 from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;
            case 2:
                health = health - 11;
                message =  " did a whooping kick at " + victim + "'s stomach that subtracted 11 from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;
            case 3:
                message =  " did a flop at " + victim + "'s the floor that subtracted nothing from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;
            case 4:
                health = health - 14;
                message =  " drove a car over " + victim + " that subtracted 14 from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;
            case 5:
                health = health - 20;
                message =  " threw C-14 at " + victim + "'s legs that subtracted 20 from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;
            case 6:
                health = health - 4;
                message =  " did a slap at " + victim + "'s face that subtracted 4 from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;

            case 7:
                health = health - 39;
                message =  " drove a tank over " + victim + "'s body that subtracted 39 from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;


            default:
                health = health - 8;
                message =  " did a awesome punch at " + victim + "'s face that subtracted 8 from " + victim + "'s health.\n" +
                        victim + "'s health is now " + health;
                break;
        }

        if (health < 0) {
            health = 0;
        }

        if (isChallenger) {
            healthChallenger = health;
        } else {
            healthVictim = health;
        }

        return message;
    }

    private boolean win (int healthVictim, int healthChallenger, String fighter, boolean challenger) {
        if (healthVictim > 0) {
            return true;
        }

        if (challenger) {
            channel.sendMessage( this.challenger + " wins with " + healthChallenger + " left.").queue();
            return false;
        }

        channel.sendMessage( fighter + " wins with " + healthChallenger + " left.").queue();
        return false;
    }

    @Override
    public String getName() {
        return "fight";
    }

    @Override
    public String getHelp() {
        return "Fights with user\n" +
                "Usage: ,fight [person]";
    }

    @Override
    public CommandType getCategory() {
        return CommandType.FUN;
    }
}
