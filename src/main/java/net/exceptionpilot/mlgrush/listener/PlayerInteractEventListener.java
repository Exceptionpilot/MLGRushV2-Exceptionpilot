package net.exceptionpilot.mlgrush.listener;

import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.builder.ItemBuilder;
import net.exceptionpilot.mlgrush.location.LocationHandler;
import net.exceptionpilot.mlgrush.location.types.Locations;
import net.exceptionpilot.mlgrush.manager.SpecManager;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.sql.user.SQLPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:51
 * Class «» PlayerInteractEventListener
 **/

public class PlayerInteractEventListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        RushPlayer rushPlayer = RushPlayer.getPlayer(event.getPlayer());
        if(event.getItem() == null) return;
        if(event.getItem().getItemMeta() == null) return;
        if(event.getItem().getItemMeta().getDisplayName() == null) return;
        if(rushPlayer.isLobby() && !rushPlayer.isBuildMode() && !rushPlayer.isPlayerSpec()) {
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().getItemNames().get("leave"))) {
                rushPlayer.getPlayer().kickPlayer(MLGRush.getInstance().getPrefix() + "§7Du hast MLGRush §cverlassen!");
            }
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().getItemNames().get("settings"))) {
                Inventory inventory = Bukkit.createInventory(null, 9 * 4, MLGRush.getInstance().getStringUtils().getInventoryName().get("sort"));

                for (int i = 0; i < 9; i++) {
                    inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 15).setDisplayName("§7").build());
                }

                for (int i = 18; i < 36; i++) {
                    inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 15).setDisplayName("§7").build());
                }

                inventory.setItem(21, new ItemBuilder(Material.INK_SACK, (short) 10).setDisplayName("§8» §aSpeichern").build());
                inventory.setItem(23, new ItemBuilder(Material.INK_SACK, (short) 1).setDisplayName("§8» §cAbbrechen").build());

                SQLPlayer sqlPlayer = new SQLPlayer(rushPlayer.getPlayer());

                inventory.setItem(sqlPlayer.getSlot("STICK") + 9, new ItemBuilder(Material.STICK)
                        .setDisplayName("§8» §eStick")
                        .build()
                );

                inventory.setItem(sqlPlayer.getSlot("BLOCK") + 9, new ItemBuilder(Material.SANDSTONE)
                        .setDisplayName("§8» §eBlöcke")
                        .build()
                );

                inventory.setItem(sqlPlayer.getSlot("PICKAXE") + 9, new ItemBuilder(Material.WOOD_PICKAXE)
                        .setDisplayName("§8» §ePickaxe")
                        .build()
                );

                rushPlayer.getPlayer().openInventory(inventory);
            }
            if((event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().getItemNames().get("spec")))) {
                SpecManager.getInstance().openSpecInventory(rushPlayer.getPlayer());
            }
        } else if(rushPlayer.isPlayerSpec()) {
            if((event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().getItemNames().get("leavespec")))) {
                MLGRush.getInstance().getLocationHandler().teleport(Locations.SPAWN, rushPlayer.getPlayer());
                rushPlayer.setPlayerSpec(false);
                rushPlayer.setScoreboard();
            }
        }
    }
}
