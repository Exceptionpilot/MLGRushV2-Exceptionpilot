package net.exceptionpilot.mlgrush.commands;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 10:23
 * Class «» SpecCommand
 **/

public class SpecCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("mlgrush.spec")) {
            player.sendMessage(MLGRush.getInstance().getNoPerms());
            return true;
        }

        RushPlayer rushPlayer = RushPlayer.getPlayer(player);
        if(rushPlayer.isIngame()) {
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu kannst dies nicht Ingame tun§7§l!");
            return true;
        }

        if(rushPlayer.isSpec()) {
            rushPlayer.setSpec(false);
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du bist nun nicht mehr im §cSpec§7§l!");
            return true;
        }

        rushPlayer.setSpec(true);
        player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du bist nun im §aSpec§7§l!");
        return true;
    }
}
