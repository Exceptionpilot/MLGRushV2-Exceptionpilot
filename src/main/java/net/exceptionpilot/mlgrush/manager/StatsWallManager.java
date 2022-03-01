package net.exceptionpilot.mlgrush.manager;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.MapLocations;
import net.exceptionpilot.mlgrush.sql.user.SQLStats;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 13:42
 * Class «» StatsWall
 **/

public class StatsWallManager {

    int size;
    private YamlConfiguration cfg;
    private File file;
    boolean active;

    public StatsWallManager() {
        size = MLGRush.getInstance().getMapLocations().getYamlConfiguration().getStringList("skulls").size();
        MLGRush.getInstance().getLogger().log(Level.INFO, "Es wurde(n) " + size + " Statswall location(s) gefunden!");

        if(size != 0) {
            active = true;
        }

        cfg = MLGRush.getInstance().getMapLocations().getYamlConfiguration();
        file = MLGRush.getInstance().getMapLocations().getFile();

        if(active) {
            setup();
            reload();
        }
    }

    public void setup() {
        List<String> players = new SQLStats("null").getAllPlayers();

        for (int i = 1; i <= size; i++) {
            Location skullLocation = getLocation("location.statswall.skull." + i);
            Location signLocation = getLocation("location.statswall.sign." + i);
            Skull skull = (Skull) skullLocation.getBlock().getState();
            Sign sign = (Sign) signLocation.getBlock().getState();
            if (i > players.size()) {
                skull.setOwner("MHF_Question");
                skull.update();
                sign.setLine(0, "» Platz #" + i + " «");
                sign.setLine(1, "???");
                sign.setLine(2, "??? Wins");
                sign.setLine(3, "??? Punkte");
                sign.update();
                continue;
            }
            SQLStats sqlStats = new SQLStats(players.get(i - 1));
            String skullOwner = sqlStats.getName("REALNAME");
            skull.setOwner(skullOwner);
            skull.update();
            sign.setLine(0, "» Platz #" + i + " «");
            sign.setLine(1, skullOwner);
            sign.setLine(2, sqlStats.getFormStats("WINS") + " Wins");
            sign.setLine(3, sqlStats.getFormStats("POINTS") + " Punkte");
            sign.update();

        }
    }

    public void reload() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MLGRush.getInstance(), this::setup,  5 * 60 * 20, 5 * 60 * 20);
    }

    public Location getLocation(String path) {
        Double x = cfg.getDouble(path + ".x");
        Double y = cfg.getDouble(path + ".y");
        Double z = cfg.getDouble(path + ".z");
        Float yaw = (float) cfg.getInt(path + ".yaw");
        Float pitch = (float) cfg.getInt(path + ".pitch");
        String world = cfg.getString(path + ".world");
        if (world == null || x == null || y == null)
            throw new IllegalArgumentException("Die Location '" + path + "' existiert nicht");
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}
