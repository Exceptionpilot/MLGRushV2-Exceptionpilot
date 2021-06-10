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
            MLGRush.getInstance().getMlgrushUtils().leaveMatch(rushPlayer);
        }
        MLGRush.getInstance().getMlgrushUtils().reset(rushPlayer.getPlayer());
        MLGRush.getInstance().getMlgrushUtils().getQueueList().remove(rushPlayer.getPlayer());
    }
}
