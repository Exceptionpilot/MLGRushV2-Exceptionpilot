package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:52
 * Class «» PlayerInteractAtEntityEventListener
 **/

public class PlayerInteractAtEntityEventListener implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if(event.getRightClicked() == null) return;
        if(event.getRightClicked() instanceof Player) {
            MLGRush.getInstance().getMlgrushUtils().handleSword(event.getPlayer(), ((Player) event.getRightClicked()).getPlayer());
        }
        if(event.getRightClicked().getCustomName() == null) return;
        RushPlayer rushPlayer = new RushPlayer(event.getPlayer());
        if(!rushPlayer.isLobby()) return;
        if(event.getRightClicked().getCustomName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().queueTitle)) {
            MLGRush.getInstance().getMlgrushUtils().handleQueue(rushPlayer);
        }
    }
}
