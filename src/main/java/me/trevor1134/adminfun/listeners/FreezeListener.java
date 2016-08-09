package me.trevor1134.adminfun.listeners;

import me.trevor1134.adminfun.commands.FreezeCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (FreezeCommand.getFrozen().contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
