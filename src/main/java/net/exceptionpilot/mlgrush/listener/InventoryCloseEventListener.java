package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 23:27
 * Class «» InventoryCloseEventListener
 **/

public class InventoryCloseEventListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getInventory() == null) return;
        if(event.getInventory().getName() == null) return;
        if(event.getInventory().getName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().getInventoryName().get("sort"))) {
            RushPlayer rushPlayer = RushPlayer.getPlayer((Player) event.getPlayer());
            Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
                rushPlayer.getPlayer().getInventory().clear();
                rushPlayer.setLobbyItems();
            }, 2L);
        }
    }
}
