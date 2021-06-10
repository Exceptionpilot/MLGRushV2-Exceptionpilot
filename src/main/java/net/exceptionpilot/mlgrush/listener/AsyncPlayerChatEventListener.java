package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 20:22
 * Class «» AsyncPlayerChatEventListener
 **/

public class AsyncPlayerChatEventListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(MLGRush.getInstance().getSetupManager().setup.contains(event.getPlayer())) {
            int i = MLGRush.getInstance().getSetupManager().setupList.get(event.getPlayer());
            if(i == 0) {
                MLGRush.getInstance().getSetupManager().setMap(event.getMessage());
                MLGRush.getInstance().getMapLocations().addMap(event.getMessage());
                MLGRush.getInstance().getSetupManager().push(event.getPlayer());
            }
            if(i == 1) {
                if(event.getMessage().equalsIgnoreCase("fertig")) {
                    MLGRush.getInstance().getMapLocations().setLocation("spawn.rot." + MLGRush.getInstance().getSetupManager().getMap(), event.getPlayer().getLocation());
                    MLGRush.getInstance().getSetupManager().push(event.getPlayer());
                }
            }
            if(i == 2) {
                if(event.getMessage().equalsIgnoreCase("fertig")) {
                    MLGRush.getInstance().getMapLocations().setLocation("spawn.blau." + MLGRush.getInstance().getSetupManager().getMap(), event.getPlayer().getLocation());
                    MLGRush.getInstance().getSetupManager().push(event.getPlayer());
                }
            }
            if(i == 7) {
                if(event.getMessage().equalsIgnoreCase("fertig")) {
                    MLGRush.getInstance().getMapLocations().setLocation("high." + MLGRush.getInstance().getSetupManager().getMap(), event.getPlayer().getLocation());
                    MLGRush.getInstance().getSetupManager().push(event.getPlayer());
                }
            }
            if(i == 8) {
                if(event.getMessage().equalsIgnoreCase("fertig")) {
                    MLGRush.getInstance().getMapLocations().setLocation("low." + MLGRush.getInstance().getSetupManager().getMap(), event.getPlayer().getLocation());
                    MLGRush.getInstance().getSetupManager().push(event.getPlayer());
                }
            }
            if(i == 9) {
                if(event.getMessage().equalsIgnoreCase("fertig")) {
                    MLGRush.getInstance().getSetupManager().push(event.getPlayer());
                }
            }
            event.setCancelled(true);
        }

        event.setFormat(event.getPlayer().getDisplayName() + " §8|§7 " +  event.getMessage());
    }
}
