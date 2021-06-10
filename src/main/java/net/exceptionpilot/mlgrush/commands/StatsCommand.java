package net.exceptionpilot.mlgrush.commands;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.MapLocations;
import net.exceptionpilot.mlgrush.sql.user.SQLStats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 12:53
 * Class «» StatsCommand
 **/

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;

        if(MLGRush.getInstance().getStatsUtils().isInCooldown(player)) {
            return true;
        }

        MLGRush.getInstance().getStatsUtils().execute(player);

        if(args.length == 0) {
            SQLStats sqlStats = new SQLStats(player.getName());
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m------------------------------------");
            player.sendMessage(MessageFormat.format("{0}§eKills §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("KILLS")));
            player.sendMessage(MessageFormat.format("{0}§eBeds §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("BEDS")));
            player.sendMessage(MessageFormat.format("{0}§ePoints §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("POINTS")));
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§eKD §8»§a " + sqlStats.getKd());
            player.sendMessage(MessageFormat.format("{0}§eDeaths §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("DEATHS")));
            player.sendMessage(MessageFormat.format("{0}§eWins §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("WINS")));
            player.sendMessage(MessageFormat.format("{0}§eLoses §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("Loses")));
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m------------------------------------");
            return true;
        }

        if(args.length == 1) {
            SQLStats sqlStats = new SQLStats(args[0]);
            if(sqlStats.isPlayerInDB()) {
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m------------------------------------");
                player.sendMessage(MessageFormat.format("{0}§eKills §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("KILLS")));
                player.sendMessage(MessageFormat.format("{0}§eBeds §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("BEDS")));
                player.sendMessage(MessageFormat.format("{0}§ePoints §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("POINTS")));
                player.sendMessage(MessageFormat.format("{0}§eKD §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getKd()));
                player.sendMessage(MessageFormat.format("{0}§eDeaths §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("DEATHS")));
                player.sendMessage(MessageFormat.format("{0}§eWins §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("WINS")));
                player.sendMessage(MessageFormat.format("{0}§eLoses §8»§a {1}", MLGRush.getInstance().getPrefix(), sqlStats.getFormStats("Loses")));
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m------------------------------------");
            } else {
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDieser Spieler wurde nicht gefunden!");
            }
        }
        return true;
    }
}
