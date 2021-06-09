package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:02
 * Class «» PlayerQuitEventListener
 **/

public class PlayerQuitEventListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        if(rushPlayer.isIngame()) {
            MLGRush.getInstance().getQueueUtils().leaveMatch(rushPlayer);
        }
        MLGRush.getInstance().getQueueUtils().reset(rushPlayer.getPlayer());
        MLGRush.getInstance().getQueueUtils().getQueueList().remove(rushPlayer.getPlayer());
    }
}
