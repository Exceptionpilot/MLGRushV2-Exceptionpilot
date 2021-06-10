package net.exceptionpilot.mlgrush.commands;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.MapLocations;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 10:06
 * Class «» BuildCommand
 **/

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        RushPlayer rushPlayer = RushPlayer.getPlayer(player);
        if(!player.hasPermission("mlgrush.admin")) {
            player.sendMessage(MLGRush.getInstance().getNoPerms());
            return true;
        }

        if(rushPlayer.isIngame()) {
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu kannst dies nicht Ingame tun§7§l!");
            return true;
        }

        if(rushPlayer.isBuildMode()) {
            rushPlayer.setBuildMode(false);
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du bist nun nicht mehr im §cBuildmodus§7§l!");
            return true;
        }

        rushPlayer.setBuildMode(true);
        player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du bist nun im §aBuildmodus§7§l!");

        return true;
    }
}
