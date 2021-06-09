package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 22:11
 * Class «» PlayerMoveEventListener
 **/

public class PlayerMoveEventListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(!(event.getPlayer() instanceof Player)) {
            return;
        }
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        if(rushPlayer.isIngame()) {
            if(rushPlayer.getPlayer().getLocation().getY() <= MLGRush.getInstance().getMapLocations().getLocation("low." + rushPlayer.getMap()).getY()) {
                rushPlayer.teleportToIngameSpawn();
            }
        }
    }
}
