package net.exceptionpilot.mlgrush.score;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.score.utils.BPlayerBoard;
import net.exceptionpilot.mlgrush.score.utils.Board;
import net.md_5.bungee.api.ChatColor;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 18:19
 * Class «» ScoreboardManager
 **/

public class ScoreboardManager {

    BPlayerBoard bPlayerBoard;

    public void set(RushPlayer rushPlayer) {
        bPlayerBoard = (Board.instance().getBoard(rushPlayer.getPlayer()) != null) ? Board.instance().getBoard(rushPlayer.getPlayer()) : Board.instance().createBoard(rushPlayer.getPlayer(), ChatColor.translateAlternateColorCodes('&', "§8» §eMLGRush §8«"));
        if(rushPlayer.isBuildMode()) {
            bPlayerBoard.getLines().clear();
            bPlayerBoard.set("§1", 9);
            bPlayerBoard.set("§7Buildmodus", 8);
            bPlayerBoard.set("§8● §1§aAktiviert!", 7);
            bPlayerBoard.set("§2", 6);
            bPlayerBoard.set("§7Shop", 5);
            bPlayerBoard.set("§8● §1§2§eSpigotFork.eu", 4);
            bPlayerBoard.set("§9", 3);
            bPlayerBoard.set("§7Teamspeak", 2);
            bPlayerBoard.set("§8● §eSpigotFork.eu", 1);
            bPlayerBoard.set("§4", 0);
            return;
        }
        if(rushPlayer.isSpec()) {
            bPlayerBoard.getLines().clear();
            bPlayerBoard.set("§1", 9);
            bPlayerBoard.set("§7Spec", 8);
            bPlayerBoard.set("§8● §1§aAktiviert!", 7);
            bPlayerBoard.set("§2", 6);
            bPlayerBoard.set("§7Shop", 5);
            bPlayerBoard.set("§8● §1§2§eSpigotFork.eu", 4);
            bPlayerBoard.set("§9", 3);
            bPlayerBoard.set("§7Teamspeak", 2);
            bPlayerBoard.set("§8● §eSpigotFork.eu", 1);
            bPlayerBoard.set("§4", 0);
            return;
        }
        if(rushPlayer.isLobby()) {
            bPlayerBoard.getLines().clear();
            bPlayerBoard.set("§1", 9);
            bPlayerBoard.set("§7Ausgehend", 8);
            bPlayerBoard.set("§8● §1§e" + (MLGRush.getInstance().getMlgrushUtils().getMatching().get(rushPlayer.getPlayer()) != null ? MLGRush.getInstance().getMlgrushUtils().getMatching().get(rushPlayer.getPlayer()) : "[-/-]"), 7);
            bPlayerBoard.set("§2", 6);
            bPlayerBoard.set("§7Eingehend", 5);
            bPlayerBoard.set("§8● §2§e" + (MLGRush.getInstance().getMlgrushUtils().getInMatching().get(rushPlayer.getPlayer()) != null ? MLGRush.getInstance().getMlgrushUtils().getInMatching().get(rushPlayer.getPlayer()) : "[-/-]"), 4);
            bPlayerBoard.set("§9", 3);
            bPlayerBoard.set("§7Teamspeak", 2);
            bPlayerBoard.set("§8● §eSpigotFork.eu", 1);
            bPlayerBoard.set("§4", 0);
            return;
        }
        if(rushPlayer.isIngame()) {
            bPlayerBoard.getLines().clear();
            bPlayerBoard.set("§1", 9);
            bPlayerBoard.set("§7Map", 8);
            bPlayerBoard.set("§8● §1§b" + rushPlayer.getMap(), 7);
            bPlayerBoard.set("§2", 6);
            bPlayerBoard.set("§7Gegner", 5);
            bPlayerBoard.set("§8● §2§e" + MLGRush.getInstance().getMlgrushUtils().getMatch().get(rushPlayer.getPlayer()).getName(), 4);
            bPlayerBoard.set("§7", 3);
            bPlayerBoard.set("§7Punkte", 2);
            bPlayerBoard.set("§8● " + MLGRush.getInstance().getTeamUtils().getTeamColor(rushPlayer.getPlayer()) +
                    MLGRush.getInstance().getGameUtils().getPoints().get(rushPlayer.getPlayer()) + " §8| " +
                            MLGRush.getInstance().getTeamUtils().getTeamColor(MLGRush.getInstance().getMlgrushUtils().getMatch().get(rushPlayer.getPlayer()).getPlayer())
                    + MLGRush.getInstance().getGameUtils().getPoints().get(MLGRush.getInstance().getMlgrushUtils().getMatch().get(rushPlayer.getPlayer()))
                    , 1);
            bPlayerBoard.set("§9", 0);
            return;
        }
    }
}
