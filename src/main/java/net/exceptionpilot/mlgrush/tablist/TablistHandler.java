package net.exceptionpilot.mlgrush.tablist;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 19:13
 * Class «» TablistHandler
 **/

public class TablistHandler {

    Scoreboard scoreboard;

    public TablistHandler() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        if(scoreboard.getTeam("010Spieler") == null) {
            scoreboard.registerNewTeam("010Spieler");
        }
        scoreboard.getTeam("010Spieler").setPrefix("§7");
    }

    public void intIngameTablist(Player player) {
        scoreboard.getTeam("010Spieler").addPlayer(player);
        Bukkit.getOnlinePlayers().forEach(all -> {
            all.setScoreboard(scoreboard);
        });
    }
}
