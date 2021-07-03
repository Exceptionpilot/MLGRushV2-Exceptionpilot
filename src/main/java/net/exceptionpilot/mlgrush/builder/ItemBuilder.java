package net.exceptionpilot.mlgrush.builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 18:02
 * Class «» ItemBuilder
 **/

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, short subID) {
        this.itemStack = new ItemStack(material, 1, subID);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public void setUnbreakable() {
        itemMeta.spigot().setUnbreakable(true);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1, (short) 0);
        this.itemMeta = this.itemStack.getItemMeta();
        if(material != Material.STAINED_GLASS_PANE || material != Material.PAPER) {
            itemMeta.spigot().setUnbreakable(true);
        }
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setSubID(byte subID) {
        this.itemStack.getData().setData(subID);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        this.itemMeta.setLore(Collections.singletonList(lore));
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        if (this.itemMeta.getLore() != null) {
            this.itemMeta.getLore().add(line);
        } else {
            List<String> lore = new ArrayList();
            lore.add(line);
            this.itemMeta.setLore(lore);
        }

        return this;
    }


    public ItemBuilder addLoreArray(String[] array) {
        if (this.itemMeta.getLore() != null) {
            String[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String current = var2[var4];
                this.itemMeta.getLore().add(current);
            }
        } else {
            List<String> lore = new ArrayList(Arrays.asList(array));
            this.itemMeta.setLore(lore);
        }

        return this;
    }

    public ItemBuilder setType(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder setAmount(Integer amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment) {
        this.itemMeta.addEnchant(enchantment, 1, false);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, Integer power) {
        this.itemMeta.addEnchant(enchantment, power, false);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
