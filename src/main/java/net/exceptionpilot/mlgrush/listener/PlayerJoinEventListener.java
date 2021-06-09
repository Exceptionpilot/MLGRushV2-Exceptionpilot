package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.location.types.Locations;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.sql.user.SQLPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:02
 * Class «» PlayerJoinEventListener
 **/

public class PlayerJoinEventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        SQLPlayer sqlPlayer = new SQLPlayer(rushPlayer.getPlayer());
        rushPlayer.setLobby(true);
        rushPlayer.teleport(Locations.SPAWN);
        rushPlayer.setLobbyItems();
        rushPlayer.setScoreboard();
        Bukkit.getOnlinePlayers().forEach(all -> {
            rushPlayer.reloadVisibility(all);
        });
    }
}
