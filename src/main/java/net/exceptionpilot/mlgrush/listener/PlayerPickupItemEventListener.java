package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 23:19
 * Class «» PlayerPickupItemEventListener
 **/

public class PlayerPickupItemEventListener implements Listener {

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        if(rushPlayer.isIngame()) {
            event.setCancelled(false);
        } else if(!rushPlayer.isBuildMode()) {
            event.setCancelled(true);
        }
    }
}
