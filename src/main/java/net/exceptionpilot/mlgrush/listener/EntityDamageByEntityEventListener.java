package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 18:12
 * Class «» EntityDamageByEntityEventListener
 **/

public class EntityDamageByEntityEventListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Golem) {
            RushPlayer rushPlayer = RushPlayer.getPlayer((Player) event.getDamager());
            if (rushPlayer.isLobby()) {
                MLGRush.getInstance().getQueueUtils().handleQueue(rushPlayer);
                event.setCancelled(true);
                return;
            }
        }
        if(event.getEntity() instanceof Player) {
            RushPlayer rushPlayer = RushPlayer.getPlayer((Player) event.getDamager());
            if(rushPlayer.isLobby()) {
                MLGRush.getInstance().getQueueUtils().handleSword((Player) event.getDamager(), ((Player) event.getEntity()).getPlayer());
                return;
            }
        }
    }
}
