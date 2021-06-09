package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 18:11
 * Class «» EntityDamageEventListener
 **/

public class EntityDamageEventListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }
        RushPlayer rushPlayer = RushPlayer.getPlayer((Player) event.getEntity());
        if(rushPlayer.isLobby()) {
            event.setCancelled(true);
        }
        if(rushPlayer.isIngame()) {
            if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                event.setCancelled(true);
            }
        }
    }
}
