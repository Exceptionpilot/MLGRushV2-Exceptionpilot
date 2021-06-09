package net.exceptionpilot.mlgrush.location;

import lombok.SneakyThrows;
import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.types.Locations;
import net.exceptionpilot.mlgrush.utils.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:07
 * Class «» LocationHandler
 **/

public class LocationHandler {

    File ord = new File("plugins//MLGRush");
    File file = new File("plugins//MLGRush//locations.yml");
    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);

    @SneakyThrows
    public LocationHandler() {
        if(!ord.exists()) {
            ord.mkdir();
        }
        if(!file.exists()) {
            file.createNewFile();
        }
    }

    public void setLocation(Locations locs, Location location) {
        yamlConfiguration.set("location." + locs.toString().toLowerCase() + ".world", location.getWorld().getName());
        yamlConfiguration.set("location." + locs.toString().toLowerCase() + ".x", location.getX());
        yamlConfiguration.set("location." + locs.toString().toLowerCase() + ".y", location.getY());
        yamlConfiguration.set("location." + locs.toString().toLowerCase() + ".z", location.getZ());
        yamlConfiguration.set("location." + locs.toString().toLowerCase() + ".pitch", location.getPitch());
        yamlConfiguration.set("location." + locs.toString().toLowerCase() + ".yaw", location.getYaw());
        save();
    }

    public void teleport(Locations locations, Player player) {
        if(MLGRush.getInstance().getGameUtils().getLocationHashMap().containsKey(locations)) {
            player.teleport(MLGRush.getInstance().getLocationHandler().getLocation(locations));
        } else {
            MLGRush.getInstance().getGameUtils().getLocationHashMap().put(locations, getLocation(locations));
            player.teleport(MLGRush.getInstance().getGameUtils().getLocationHashMap().get(locations));
        }
    }

    public Location getLocation(Locations locations) {
        World world = Bukkit.getWorld(yamlConfiguration.getString("location." + locations.toString().toLowerCase() + ".world"));
        double x = yamlConfiguration.getDouble("location." + locations.toString().toLowerCase() + ".x");
        double y = yamlConfiguration.getDouble("location." + locations.toString().toLowerCase() + ".y");
        double z = yamlConfiguration.getDouble("location." + locations.toString().toLowerCase() + ".z");
        float yaw = (float) yamlConfiguration.getDouble("location." + locations.toString().toLowerCase() + ".yaw");
        float pitch = (float) yamlConfiguration.getDouble("location." + locations.toString().toLowerCase() + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }

    @SneakyThrows
    public void save() {
        yamlConfiguration.save(file);
    }
}
