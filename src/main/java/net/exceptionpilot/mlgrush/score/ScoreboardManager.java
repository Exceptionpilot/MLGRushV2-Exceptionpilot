package net.exceptionpilot.mlgrush.score;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.score.utils.BPlayerBoard;
import net.exceptionpilot.mlgrush.score.utils.Board;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 18:19
 * Class «» ScoreboardManager
 **/

public class ScoreboardManager {

    BPlayerBoard bPlayerBoard;

    public void set(RushPlayer rushPlayer) {
        bPlayerBoard = (Board.instance().getBoard(rushPlayer.getPlayer()) != null) ? Board.instance().getBoard(rushPlayer.getPlayer()) : Board.instance().createBoard(rushPlayer.getPlayer(), ChatColor.translateAlternateColorCodes('&', "§8» §eMLGRush §8«"));
        bPlayerBoard.clear();
        if(rushPlayer.isLobby()) {
            bPlayerBoard.set("§1", 9);
            bPlayerBoard.set("§7Ausgehend", 8);
            bPlayerBoard.set("§8● §1§e" + (MLGRush.getInstance().getQueueUtils().getMatching().get(rushPlayer.getPlayer()) != null ? MLGRush.getInstance().getQueueUtils().getMatching().get(rushPlayer.getPlayer()) : "[-/-]"), 7);
            bPlayerBoard.set("§2", 6);
            bPlayerBoard.set("§7Eingehend", 5);
            bPlayerBoard.set("§8● §2§e" + (MLGRush.getInstance().getQueueUtils().getInMatching().get(rushPlayer.getPlayer()) != null ? MLGRush.getInstance().getQueueUtils().getInMatching().get(rushPlayer.getPlayer()) : "[-/-]"), 4);
            bPlayerBoard.set("§9", 3);
            bPlayerBoard.set("§7Teamspeak", 2);
            bPlayerBoard.set("§8● §eSpigotFork.eu", 1);
            bPlayerBoard.set("§4", 0);
        }
        if(rushPlayer.isIngame()) {
            bPlayerBoard.set("§1", 9);
            bPlayerBoard.set("§7Map", 8);
            bPlayerBoard.set("§8● §1§b" + rushPlayer.getMap(), 7);
            bPlayerBoard.set("§2", 6);
            bPlayerBoard.set("§7Gegner", 5);
            bPlayerBoard.set("§8● §2§e" + MLGRush.getInstance().getQueueUtils().getMatch().get(rushPlayer.getPlayer()).getName(), 4);
            bPlayerBoard.set("§7", 3);
            bPlayerBoard.set("§7Punkte", 2);
            bPlayerBoard.set("§8● " + MLGRush.getInstance().getTeamUtils().getTeamColor(rushPlayer.getPlayer()) +
                    MLGRush.getInstance().getGameUtils().getPoints().get(rushPlayer.getPlayer()) + " §8| " +
                            MLGRush.getInstance().getTeamUtils().getTeamColor(MLGRush.getInstance().getQueueUtils().getMatch().get(rushPlayer.getPlayer()).getPlayer())
                    + MLGRush.getInstance().getGameUtils().getPoints().get(MLGRush.getInstance().getQueueUtils().getMatch().get(rushPlayer.getPlayer()))
                    , 1);
            bPlayerBoard.set("§9", 0);
        }
    }
}
