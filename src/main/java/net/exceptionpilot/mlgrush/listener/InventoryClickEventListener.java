package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.location.MapLocations;
import net.exceptionpilot.mlgrush.manager.SpecManager;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.sql.user.SQLPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Random;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 21:20
 * Class «» InventoryClickEventListener
 **/

public class InventoryClickEventListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        RushPlayer rushPlayer = RushPlayer.getPlayer((Player) event.getWhoClicked());
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getName() == null) return;
        if(rushPlayer.isLobby()) {
            if(event.getClickedInventory().getName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().inventoryName.get("sort"))) {
                if (event.getCurrentItem() == null) return;
                if (event.getCurrentItem().getItemMeta() == null) return;
                if (event.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                    event.setCancelled(true);
                    return;
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §cAbbrechen")) {
                    rushPlayer.getPlayer().closeInventory();
                    rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast den Vorgang §cabgebrochen!");
                    rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.BURP, 20, 20);
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8» §aSpeichern")) {
                    Inventory inventory = event.getClickedInventory();
                    if (inventory.first(Material.WOOD_PICKAXE) != -1 && inventory.first(Material.STICK) != -1 && inventory.first(Material.SANDSTONE) != -1) {
                        int axe = inventory.first(Material.WOOD_PICKAXE) - 9;
                        int stick = inventory.first(Material.STICK) - 9;
                        int sandstone = inventory.first(Material.SANDSTONE) - 9;
                        SQLPlayer sqlPlayer = new SQLPlayer(rushPlayer.getPlayer());
                        sqlPlayer.set("STICK", stick);
                        sqlPlayer.set("PICKAXE", axe);
                        sqlPlayer.set("BLOCK", sandstone);
                        rushPlayer.getPlayer().closeInventory();
                        rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.LEVEL_UP, 20, 20);
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§7Dein Inventar wurde §aerfolgreich §7gespeichert!");
                    } else {
                        rushPlayer.getPlayer().closeInventory();
                        rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.NOTE_BASS, 20, 20);
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cEs ist ein Fehler aufgetreten!");
                    }
                }
            } else if(event.getClickedInventory().getName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().inventoryName.get("spec"))) {
                if(event.getCurrentItem().getType() == Material.PAPER) {
                    String map = event.getCurrentItem().getItemMeta().getDisplayName().substring(6);
                    if(SpecManager.getInstance().getMatchList().contains(map)) {
                        MLGRush.getInstance().getGameUtils().getPlayerSpecList().put(rushPlayer.getPlayer(), map);
                        rushPlayer.setPlayerSpec(true);
                        Bukkit.getOnlinePlayers().forEach(c -> rushPlayer.hidePlayer(c));
                        int i = new Random().nextInt(2);
                        if(i == 1) {
                            rushPlayer.getPlayer().teleport(MLGRush.getInstance().getMapLocations().getLocation("spawn." + "rot" + "." + map));
                        } else {
                            rushPlayer.getPlayer().teleport(MLGRush.getInstance().getMapLocations().getLocation("spawn." + "blau" + "." + map));
                        }
                        rushPlayer.setScoreboard();
                    } else {
                        rushPlayer.getPlayer().closeInventory();
                        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cDieses Match ist nicht am laufen!");
                    }
                }
                event.setCancelled(true);
            } else {
                if(!rushPlayer.isBuildMode()) {
                    event.setCancelled(true);
                } else {
                    event.setCancelled(false);
                }
            }
        }
    }
}
