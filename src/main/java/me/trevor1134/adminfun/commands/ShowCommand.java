package me.trevor1134.adminfun.commands;

import me.trevor1134.adminfun.AdminFun;
import me.trevor1134.adminfun.command.PlayerCommandBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ShowCommand extends PlayerCommandBase {

    public static List<UUID> waiting = new ArrayList<>();
    private static ItemStack showAxe = new ItemStack(Material.WOOD_AXE);
    private static ItemMeta axeMeta = getShowAxe().getItemMeta();

    public ShowCommand(AdminFun pl) {
        super(pl, "show", "show");

        // Configure Show Axe
        getAxeMeta().setDisplayName("Show Selector");
        getAxeMeta().setLore(Arrays.asList("Select Show Location!"));
        getShowAxe().setItemMeta(getAxeMeta());

    }

    public static ItemMeta getAxeMeta() {
        return axeMeta;
    }

    public static ItemStack getShowAxe() {
        return showAxe;
    }

    public static List<UUID> getWaiting() {
        return waiting;
    }

    @Override
    public boolean onPlayerCommand(CommandSender s, String[] args) {
        if (isAuthorized(s)) {
            if (args.length == 0) {
                Player p = (Player) s;
                if (waiting.contains(p.getUniqueId())) {
                    waiting.remove(p.getUniqueId());
                    p.getInventory().remove(getShowAxe());
                    p.sendMessage(ChatColor.AQUA + "You are no longer starting a show.");
                    return true;
                } else {
                    waiting.add(p.getUniqueId());
                    p.getInventory().addItem(getShowAxe());
                    p.sendMessage(ChatColor.AQUA + "Right click with the selector to start the show!");
                    return true;
                }
            } else {
                s.sendMessage(getUsageMessage());
            }
        } else {
            denyAccess(s);
        }
        return false;
    }

}