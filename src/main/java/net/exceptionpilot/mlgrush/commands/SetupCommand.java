package net.exceptionpilot.mlgrush.commands;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.types.Locations;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:08
 * Class «» SetupCommand
 **/

public class SetupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("mlgrush.admin")) {
            player.sendMessage(MLGRush.getInstance().getNoPerms());
            return true;
        }

        if (!new RushPlayer(player).isLobby()) {
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDu kannst dies nur in der Lobby!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7/setup setLocation <LocationType>");
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7/setup locationList");
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7/setup setQueue");
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§7/setup startMap");
        }
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("startMap")) {
                MLGRush.getInstance().getSetupManager().start(player);
                player.setGameMode(GameMode.CREATIVE);
                return true;
            }
            if (args[0].equalsIgnoreCase("locationlist")) {
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m-------------------");
                Arrays.stream(Locations.values()).forEach(all -> {
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§7LocationName §8» §e" + all.name());
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Beschreibung §8» §a" + all.getDescription());
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§8§m-------------------");
                });
                return true;
            }
            if (args[0].equalsIgnoreCase("setQueue")) {
                MLGRush.getInstance().getLocationHandler().setLocation(Locations.QUEUE, player.getLocation());
                MLGRush.getInstance().getQueueUtils().spawn();
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§aDu hast erfolgreich die Location gesetzt!");
                return true;
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setLocation")) {
                List<String> locationList = new ArrayList<>();
                Arrays.stream(Locations.values()).forEach(locations -> {
                    locationList.add(locations.toString().toUpperCase());
                });
                String loc = args[1];
                if (locationList.contains(loc.toUpperCase())) {
                    MLGRush.getInstance().getLocationHandler().setLocation(Locations.valueOf(loc.toUpperCase()), player.getLocation());
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§aDu hast erfolgreich die Location gesetzt!");
                } else {
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§cDiese Location wurde nicht gefunden!");
                }
                return true;
            }
        }

        return true;
    }
}
