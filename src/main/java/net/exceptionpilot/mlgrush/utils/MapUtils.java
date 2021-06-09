package net.exceptionpilot.mlgrush.utils;

import net.exceptionpilot.mlgrush.MLGRush;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 21:00
 * Class «» MapUtils
 **/

public class MapUtils {

    public String getRandomMap() {
        String map = MLGRush.getInstance().getMapManager().availableMaps.get(0);
        MLGRush.getInstance().getMapManager().availableMaps.remove(map);
        return map;
    }
}
