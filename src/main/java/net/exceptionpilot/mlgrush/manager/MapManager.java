package net.exceptionpilot.mlgrush.manager;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.MapLocations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 20:50
 * Class «» MapManage
 **/

public class MapManager {

    public ArrayList<String> availableMaps = new ArrayList<>();

    public MapManager() {
        List<String> list = MLGRush.getInstance().getMapLocations().getYamlConfiguration().getStringList("maps");
        for(String s : list) {
            availableMaps.add(s);
        }
        MLGRush.getInstance().getLogger().log(Level.INFO, "Es wuden " + list.size() + " Maps gefunden!");
    }

    public boolean hasMap() {
        return !availableMaps.isEmpty();
    }

    public void removeMap(String map) {
        availableMaps.remove(map);
    }

    public void addMap(String map) {
        availableMaps.add(map);
    }
}
