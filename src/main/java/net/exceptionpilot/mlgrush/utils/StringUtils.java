package net.exceptionpilot.mlgrush.utils;

import lombok.Getter;

import java.util.HashMap;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:35
 * Class «» StringUtils
 **/

@Getter
public class StringUtils {

    public String queueTitle = "§8» §6Warteschlange §8«";

    public HashMap<String, String> itemNames = new HashMap<>();

    public HashMap<String, String> inventoryName = new HashMap<>();

    public StringUtils() {
        itemNames.put("sword", "§8» §eSpieler herausfordern");
        itemNames.put("settings", "§8» §cEinstellungen");
        itemNames.put("leave", "§8» §9Verlassen");
        itemNames.put("spec", "§8» §aSpectate");
        itemNames.put("leavespec", "§a§8» §cVerlassen");

        inventoryName.put("sort", "§8» §6Inventarsortierung");
        inventoryName.put("spec", "§8» §aSpectate");
    }
}
