package net.exceptionpilot.mlgrush.tablist;

import org.bukkit.Bukkit;
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

        if(scoreboard.getTeam("001Rot") == null) {
            scoreboard.registerNewTeam("001Rot");
        }
        scoreboard.getTeam("001Rot").setPrefix("§c⬛ §8× ");

        if(scoreboard.getTeam("002Blau") == null) {
            scoreboard.registerNewTeam("002Blau");
        }
        scoreboard.getTeam("002Blau").setPrefix("§9⬛ §8× ");
    }
}
