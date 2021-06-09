package net.exceptionpilot.mlgrush.commands;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 21:11
 * Class «» QuitCommand
 **/

public class QuitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        RushPlayer rushPlayer = RushPlayer.getPlayer(player);
        if(rushPlayer.isIngame()) {
            MLGRush.getInstance().getQueueUtils().leaveMatch(rushPlayer);
        }
        return true;
    }
}
