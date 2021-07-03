package net.exceptionpilot.mlgrush.manager;

import lombok.Getter;
import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * @author Jonas D. Exceptionpilot
 * created on 03.07.2021
 * crated for MLGRushV2-Exceptionpilot
 */

@Getter
public class SpecManager {

    @Getter
    private static SpecManager instance;

    public SpecManager() {
        instance = this;
    }

    public List<String> getMatchList() {
        return MLGRush.getInstance().getMlgrushUtils().getMatches();
    }

    public void openSpecInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, MLGRush.getInstance().getStringUtils().getInventoryName().get("spec"));

        for (int i = 0; i < 10; i++) {
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 1)
                    .setDisplayName("§7")
                    .build());
        }

        int[] panels = new int[]{18, 27, 36, 45, 44, 35, 26, 17};

        for(int glass : panels) {
            inventory.setItem(glass, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 1)
                    .setDisplayName("§7")
                    .build());
        }

        inventory.setItem(45, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 1)
                .setDisplayName("§7")
                .build());

        for (int i =  9 * 6 - 10; i < 9 * 6; i++) {
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, (byte) 1)
                    .setDisplayName("§7")
                    .build());
        }

        if(getMatchList().isEmpty()) {
            inventory.setItem(9 * 5 / 2, new ItemBuilder(Material.BARRIER)
                    .setDisplayName("§cKeine Matches gefunden!")
            .build());

            player.openInventory(inventory);
            return;
        }


        getMatchList().forEach(matches -> {
            inventory.addItem(new ItemBuilder(Material.PAPER)
                    .setDisplayName("§8» §e" + matches)
            .build());
        });
        player.openInventory(inventory);
    }
}
