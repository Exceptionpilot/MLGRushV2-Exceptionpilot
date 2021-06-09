package net.exceptionpilot.mlgrush.location;

import lombok.Getter;
import lombok.SneakyThrows;
import net.exceptionpilot.mlgrush.MLGRush;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 19:36
 * Class «» MapLocations
 **/

@Getter
public class MapLocations {

    private File file = new File("plugins//MLGRush//config.yml");
    private YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    private List<String> mapList = new ArrayList<>();

    @SneakyThrows
    public void addMap(String map) {
        List<String> mList = yamlConfiguration.getStringList("maps");
        mList.add(map);
        yamlConfiguration.set("maps", mList);
        yamlConfiguration.save(file);
    }

    public void teleportToSpawn() {

    }

    @SneakyThrows
    public void setLocation(String locs, Location location) {
        yamlConfiguration.set("location." + locs + ".world", location.getWorld().getName());
        yamlConfiguration.set("location." + locs + ".x", location.getX());
        yamlConfiguration.set("location." + locs + ".y", location.getY());
        yamlConfiguration.set("location." + locs + ".z", location.getZ());
        yamlConfiguration.set("location." + locs + ".pitch", location.getPitch());
        yamlConfiguration.set("location." + locs + ".yaw", location.getYaw());
        yamlConfiguration.save(file);
    }

    public Location getLocation(String location) {
        World world = Bukkit.getWorld(yamlConfiguration.getString("location." + location + ".world"));
        double x = yamlConfiguration.getDouble("location." + location + ".x");
        double y = yamlConfiguration.getDouble("location." + location + ".y");
        double z = yamlConfiguration.getDouble("location." + location + ".z");
        float yaw = (float) yamlConfiguration.getDouble("location." + location + ".yaw");
        float pitch = (float) yamlConfiguration.getDouble("location." + location + ".pitch");
        return new Location(world, x, y, z, yaw, pitch);
    }
}
