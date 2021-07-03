package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.types.Locations;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.sql.user.SQLStats;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

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
                MLGRush.getInstance().getMlgrushUtils().addPotions(rushPlayer.getPlayer());
                rushPlayer.teleportToIngameSpawn();

                rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.BAT_DEATH, 10000, 1);

                rushPlayer.add("DEATHS");
                if(MLGRush.getInstance().getGameUtils().getLastHitter().get(event.getPlayer()) != null && MLGRush.getInstance().getGameUtils().getLastHitter().containsKey(event.getPlayer())) {
                    SQLStats sqlStats = new SQLStats(MLGRush.getInstance().getGameUtils().getLastHitter().get(rushPlayer.getPlayer()).getName());
                    sqlStats.add("KILLS");
                    sqlStats.add("POINTS");
                    MLGRush.getInstance().getGameUtils().getLastHitter().remove(rushPlayer.getPlayer(), MLGRush.getInstance().getGameUtils().getLastHitter().get(rushPlayer.getPlayer()));
                }
            }
        }
        if(rushPlayer.isLobby()) {
            if(event.getPlayer().getLocation().getY() <= 0) {
                rushPlayer.teleport(Locations.SPAWN);
            }
        }
        if(rushPlayer.isPlayerSpec()) {
            if(event.getPlayer().getLocation().getY() <= 0) {
                String map = MLGRush.getInstance().getGameUtils().getPlayerSpecList().get(rushPlayer.getPlayer());
                int i = new Random().nextInt(2);
                if(i == 1) {
                    rushPlayer.getPlayer().teleport(MLGRush.getInstance().getMapLocations().getLocation("spawn." + "rot" + "." + map));
                } else {
                    rushPlayer.getPlayer().teleport(MLGRush.getInstance().getMapLocations().getLocation("spawn." + "blau" + "." + map));
                }
            }
        }
    }
}
