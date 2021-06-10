package net.exceptionpilot.mlgrush.utils;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.MapLocations;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 12:56
 * Class «» StatsUtils
 **/

public class StatsUtils {

    public ArrayList<Player> cooldown = new ArrayList<>();

    public void execute(Player player) {
        cooldown.add(player);
        Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
            cooldown.remove(player);
        }, 40L);
    }

    public boolean isInCooldown(Player player) {
        if(cooldown.contains(player)) {
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§cBitte warte bevor du diese Aktion erneut ausführst§7§l!");
            return true;
        }
        return false;
    }
}
