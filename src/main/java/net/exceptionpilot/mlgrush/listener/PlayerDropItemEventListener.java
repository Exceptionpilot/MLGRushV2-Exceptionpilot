package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 23:19
 * Class «» PlayerDropItemEventListener
 **/

public class PlayerDropItemEventListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        if(!rushPlayer.isBuildMode()) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }
}
