package net.exceptionpilot.mlgrush.utils;

import lombok.Getter;
import net.exceptionpilot.mlgrush.location.types.Locations;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:04
 * Class «» GameUtils
 **/

@Getter
public class GameUtils {

    private ArrayList<Player> lobbyList = new ArrayList<>();
    private ArrayList<Player> ingameList = new ArrayList<>();
    private HashMap<Locations, Location> locationHashMap = new HashMap<>();
    private HashMap<Player, String> mapList = new HashMap<>();
    private HashMap<Player, String> teamList = new HashMap<>();
    private HashMap<Player, Integer> points = new HashMap<>();
    private ArrayList<Player> buildMode = new ArrayList<>();
    private ArrayList<Player> specList = new ArrayList<>();
    private HashMap<Player, Player> lastHitter = new HashMap<>();
    private HashMap<Player, String> playerSpecList = new HashMap<>();
}
