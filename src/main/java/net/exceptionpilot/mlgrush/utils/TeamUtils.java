package net.exceptionpilot.mlgrush.utils;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.entity.Player;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 20:59
 * Class «» TeamUtils
 **/

public class TeamUtils {

    public void setRandomTeam(Player player, Player matcher) {
        RushPlayer rushPlayer = RushPlayer.getPlayer(player);
        rushPlayer.setTeam(RushPlayer.Teams.ROT);
        RushPlayer player2 = RushPlayer.getPlayer(matcher);
        player2.setTeam(RushPlayer.Teams.BLAU);
    }

    public String getTeamColor(Player player) {
        if(MLGRush.getInstance().getGameUtils().getTeamList().get(player).equalsIgnoreCase("rot")) {
            return "§c";
        } else if(MLGRush.getInstance().getGameUtils().getTeamList().get(player).equalsIgnoreCase("blau")) {
            return "§9";
        } else {
            return "§7";
        }
    }
}
