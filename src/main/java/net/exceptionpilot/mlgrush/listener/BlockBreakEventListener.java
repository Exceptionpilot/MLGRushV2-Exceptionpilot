package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 20:30
 * Class «» BlockBreakEventListener
 **/

public class BlockBreakEventListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (MLGRush.getInstance().getSetupManager().setup.contains(event.getPlayer())) {
            Location location = event.getBlock().getLocation();
            int i = MLGRush.getInstance().getSetupManager().setupList.get(event.getPlayer());
            if (i == 3) {
                MLGRush.getInstance().getMapLocations().setLocation("bed.rot.oben." + MLGRush.getInstance().getSetupManager().getMap(), location);
                MLGRush.getInstance().getSetupManager().push(event.getPlayer());
            }
            if (i == 4) {
                MLGRush.getInstance().getMapLocations().setLocation("bed.rot.unten." + MLGRush.getInstance().getSetupManager().getMap(), location);
                MLGRush.getInstance().getSetupManager().push(event.getPlayer());
            }
            if (i == 5) {
                MLGRush.getInstance().getMapLocations().setLocation("bed.blau.oben." + MLGRush.getInstance().getSetupManager().getMap(), location);
                MLGRush.getInstance().getSetupManager().push(event.getPlayer());
            }
            if (i == 6) {
                MLGRush.getInstance().getMapLocations().setLocation("bed.blau.unten." + MLGRush.getInstance().getSetupManager().getMap(), location);
                MLGRush.getInstance().getSetupManager().push(event.getPlayer());
            }
            event.setCancelled(true);
        }
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        if(rushPlayer.isIngame()) {
            if(event.getBlock().getType() == Material.BED_BLOCK) {
                Location location = event.getBlock().getLocation();
                if(location.equals(MLGRush.getInstance().getMapLocations().getLocation("bed.rot.oben." + rushPlayer.getMap()))) {
                    if(!rushPlayer.getTeam().equalsIgnoreCase("rot")) {
                        MLGRush.getInstance().getQueueUtils().addPoint(rushPlayer.getPlayer());
                    } else {
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cDu darfst dein eigenes Bett nicht abbauen!");
                    }
                }
                if(location.equals(MLGRush.getInstance().getMapLocations().getLocation("bed.rot.unten." + rushPlayer.getMap()))) {
                    if(!rushPlayer.getTeam().equalsIgnoreCase("rot")) {
                        MLGRush.getInstance().getQueueUtils().addPoint(rushPlayer.getPlayer());
                    } else {
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cDu darfst dein eigenes Bett nicht abbauen!");
                    }
                }
                if(location.equals(MLGRush.getInstance().getMapLocations().getLocation("bed.blau.oben." + rushPlayer.getMap()))) {
                    if(!rushPlayer.getTeam().equalsIgnoreCase("blau")) {
                        MLGRush.getInstance().getQueueUtils().addPoint(rushPlayer.getPlayer());
                    } else {
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cDu darfst dein eigenes Bett nicht abbauen!");
                    }
                }
                if(location.equals(MLGRush.getInstance().getMapLocations().getLocation("bed.blau.unten." + rushPlayer.getMap()))) {
                    if(!rushPlayer.getTeam().equalsIgnoreCase("blau")) {
                        MLGRush.getInstance().getQueueUtils().addPoint(rushPlayer.getPlayer());
                    } else {
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cDu darfst dein eigenes Bett nicht abbauen!");
                    }
                }
                event.setCancelled(true);
            } else {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }
}
