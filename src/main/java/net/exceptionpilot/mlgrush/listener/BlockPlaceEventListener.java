package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 21:41
 * Class «» BlockPlaceEventListener
 **/

public class BlockPlaceEventListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        if(rushPlayer.isIngame()) {
            if(event.getBlock().getLocation().getY() <= MLGRush.getInstance().getMapLocations().getLocation("high." + rushPlayer.getMap()).getY()) {
                MLGRush.getInstance().getBlockUtils().add(rushPlayer.getMap(), event.getBlock());
            } else {
                event.setCancelled(true);
            }
        }
    }
}
