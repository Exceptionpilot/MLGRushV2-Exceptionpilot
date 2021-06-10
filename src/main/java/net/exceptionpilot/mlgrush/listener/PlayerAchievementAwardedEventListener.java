package net.exceptionpilot.mlgrush.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 11:49
 * Class «» PlayerAchievementAwardedEventListener
 **/

public class PlayerAchievementAwardedEventListener implements Listener {

    @EventHandler
    public void onPlayerAchievementAwarded(PlayerAchievementAwardedEvent event) {
        event.setCancelled(true);
    }
}
