package me.trevor1134.adminfun.commands;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.CommandBase;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RocketCommand extends CommandBase {

    private int task = -1;

    public RocketCommand(AdminFun pl) {
        super(pl, "rocket", "rocket <player>");

    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        if (isAuthorized(s)) {

            if (args.length == 1) {
                final Player p = getPlayer(args[0]);

                if (isValidPlayer(p)) {
                    boolean isOutside = true;
                    int yPos = (int) p.getLocation().getY();

                    for (int i = yPos + 1; i < p.getWorld().getMaxHeight(); i++) {
                        Location yLoc = new Location(p.getLocation().getWorld(), (int) p.getLocation().getX(), i,
                                (int) p.getLocation().getZ());

                        if (yLoc.getBlock() != null) {
                            if (yLoc.getBlock().getType() != Material.AIR) {
                                isOutside = false;
                                break;
                            }
                        }
                    }

                    if (isOutside) {

                        Vector vel = p.getVelocity().setY(10);
                        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1F, 1F);
                        p.setVelocity(vel);

                        s.sendMessage(ChatColor.BLUE + "You launched " + ChatColor.DARK_BLUE + p.getName()
                                + ChatColor.BLUE + " into the air!");

                        p.sendMessage(ChatColor.DARK_RED + "You have been launched into the air!");

                        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), new Runnable() {

                            @Override
                            public void run() {
                                if (!p.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)) {

                                    RocketCommand.this.stopTask();
                                }
                            }
                        }, 4, 2);
                        return true;
                    } else {
                        s.sendMessage(ChatColor.RED + "Player " + ChatColor.DARK_RED + p.getName() + ChatColor.RED
                                + " is not outside!");
                        return true;
                    }
                } else {
                    s.sendMessage(getNotFoundMessage(args[0]));
                }
            } else {
                s.sendMessage(getUsageMessage());
            }

        } else {
            denyAccess(s);
        }
        return false;
    }

    private void stopTask() {
        Bukkit.getScheduler().cancelTask(task);
    }
}
