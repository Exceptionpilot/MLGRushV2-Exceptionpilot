package net.exceptionpilot.mlgrush.player;

import lombok.Getter;
import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.builder.ItemBuilder;
import net.exceptionpilot.mlgrush.builder.SkullBuilder;
import net.exceptionpilot.mlgrush.location.types.Locations;
import net.exceptionpilot.mlgrush.sql.user.SQLPlayer;
import net.exceptionpilot.mlgrush.sql.user.SQLStats;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:03
 * Class «» RushPlayer
 **/

@Getter
public class RushPlayer {

    Player player;

    public RushPlayer(Player player) {
        this.player = player;
    }

    public static RushPlayer getPlayer(Player player) {
        return new RushPlayer(player);
    }

    public void setIngame(boolean ingame) {
        if (ingame) {
            MLGRush.getInstance().getGameUtils().getIngameList().add(player);
            player.closeInventory();
            player.getInventory().clear();
            player.setHealthScale(20);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setAllowFlight(false);
            player.setExp(0);
            player.setGameMode(GameMode.SURVIVAL);
            MLGRush.getInstance().getGameUtils().getLobbyList().remove(player);
            MLGRush.getInstance().getGameUtils().getBuildMode().remove(player);
            return;
        }
        MLGRush.getInstance().getGameUtils().getIngameList().remove(player);
    }

    public void setIngameItems() {
        Inventory inventory = player.getInventory();
        player.getInventory().clear();
        SQLPlayer sqlPlayer = new SQLPlayer(player);
        inventory.setItem(sqlPlayer.getSlot("STICK"), new ItemBuilder(Material.STICK)
                .setDisplayName("§8» §eStick")
                .addEnchantment(Enchantment.KNOCKBACK)
                .build()
        );

        inventory.setItem(sqlPlayer.getSlot("BLOCK"), new ItemBuilder(Material.SANDSTONE)
                .setDisplayName("§8» §eBlöcke")
                .setAmount(64)
                .build()
        );

        inventory.setItem(sqlPlayer.getSlot("PICKAXE"), new ItemBuilder(Material.WOOD_PICKAXE)
                .setDisplayName("§8» §ePickaxe")
                .addEnchantment(Enchantment.DIG_SPEED)
                .build()
        );
    }

    public boolean isBuildMode() {
        return MLGRush.getInstance().getGameUtils().getBuildMode().contains(player);
    }

    public boolean isSpec() {
        return MLGRush.getInstance().getGameUtils().getSpecList().contains(player);
    }

    public void removeFormPlayersScoreboard() {

        MLGRush.getInstance().getMlgrushUtils().reset(player);

        MLGRush.getInstance().getMlgrushUtils().getRequests().remove(this.getPlayer());

        MLGRush.getInstance().getMlgrushUtils().getMatch().remove(this.getPlayer());

        MLGRush.getInstance().getMlgrushUtils().getMatching().remove(this.getPlayer());

        MLGRush.getInstance().getMlgrushUtils().getMapList().remove(this.getPlayer());

        MLGRush.getInstance().getMlgrushUtils().getInMatching().remove(this.getPlayer());

        MLGRush.getInstance().getMlgrushUtils().getQueueList().remove(this.getPlayer());
    }

    public void setSpec(boolean spec) {
        if(spec) {
            MLGRush.getInstance().getGameUtils().getBuildMode().remove(player);
            MLGRush.getInstance().getGameUtils().getSpecList().add(player);
            player.setGameMode(GameMode.CREATIVE);
            player.getInventory().clear();
            forEachReloadSpec();
            removeFormPlayersScoreboard();
            setScoreboard();
            return;
        }
        MLGRush.getInstance().getGameUtils().getSpecList().remove(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        setLobbyItems();
        forEachReloadSpec();
        teleport(Locations.SPAWN);
        setScoreboard();
    }

    public void forEachReloadSpec() {
        Bukkit.getOnlinePlayers().forEach(all -> {
            setSpecReload(all);
        });
    }

    public void setSpecReload(Player player) {
        Bukkit.getOnlinePlayers().forEach(all -> {
            if(MLGRush.getInstance().getGameUtils().getSpecList().contains(all)) {
                player.hidePlayer(all);
            } else {
                player.showPlayer(all);
            }
        });
    }

    public void setBuildMode(boolean build) {
        if(build) {
            player.setGameMode(GameMode.CREATIVE);
            player.getInventory().clear();
            player.setLevel(0);
            player.setExp(0);
            MLGRush.getInstance().getGameUtils().getSpecList().remove(player);
            MLGRush.getInstance().getGameUtils().getBuildMode().add(player);
            removeFormPlayersScoreboard();
            setScoreboard();
            forEachReloadSpec();
            return;
        }
        player.getInventory().clear();
        setLobbyItems();
        player.setGameMode(GameMode.SURVIVAL);
        teleport(Locations.SPAWN);
        MLGRush.getInstance().getGameUtils().getBuildMode().remove(player);
        setLobbyItems();
        setScoreboard();
    }

    public void add(String type) {
        SQLStats sqlStats = new SQLStats(player.getName());
        sqlStats.add(type);
    }

    public void reloadVisibility(Player visibility) {
        RushPlayer v  = new RushPlayer(visibility);
        if(v.isLobby()) {
            Bukkit.getOnlinePlayers().forEach(all -> {
                RushPlayer current = RushPlayer.getPlayer(all);
                if(current.isSpec()) {
                    visibility.hidePlayer(all);
                }
                if(current.isLobby()) {
                    visibility.showPlayer(all);
                } else {
                    visibility.hidePlayer(all);
                }
            });
        }
        if(v.isIngame()) {
            Bukkit.getOnlinePlayers().forEach(all -> {
                if(all == MLGRush.getInstance().getMlgrushUtils().getMatch().get(visibility)) {
                    visibility.showPlayer(all);
                } else {
                    visibility.hidePlayer(all);
                }
            });
        }
    }

    public void setMap(String map) {
        MLGRush.getInstance().getGameUtils().getMapList().put(player, map);
    }

    public String getMap() {
        return MLGRush.getInstance().getGameUtils().getMapList().get(player);
    }

    public void teleportToIngameSpawn() {
        player.teleport(MLGRush.getInstance().getMapLocations().getLocation("spawn." + getTeam() + "." + getMap()));
        setIngameItems();
    }

    public void setTeam(Teams teams) {
        MLGRush.getInstance().getGameUtils().getTeamList().put(player, teams.toString().toLowerCase());
    }

    public String getTeam() {
        return MLGRush.getInstance().getGameUtils().getTeamList().get(player).toLowerCase();
    }

    public enum Teams {
        ROT, BLAU
    }

    boolean ic;

    public void sendActionbar() {
        ic = true;
        if (ic)
            Bukkit.getScheduler().scheduleSyncRepeatingTask(MLGRush.getInstance(), () -> {
                if (isIngame()) {
                    sendAc("§8» §eGegner §8➟§c " + MLGRush.getInstance().getGameUtils().getPoints().get(MLGRush.getInstance().getMlgrushUtils().getMatch().get(player))
                            + " §8×§e Du §8➟§9 " + MLGRush.getInstance().getGameUtils().getPoints().get(player) + " §8«");
                } else {
                    ic = false;
                }
            }, 20, 20L);
    }

    public void sendAc(String message) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer
                .a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public boolean isIngame() {
        return MLGRush.getInstance().getGameUtils().getIngameList().contains(player);
    }

    public boolean isLobby() {
        return MLGRush.getInstance().getGameUtils().getLobbyList().contains(player);
    }

    public void setLobby(boolean bool) {
        if (bool) {
            player.getInventory().clear();
            player.setLevel(0);
            player.setHealthScale(20);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.ADVENTURE);
            player.setExp(0);
            player.setAllowFlight(false);
            MLGRush.getInstance().getGameUtils().getLobbyList().add(player);
            MLGRush.getInstance().getGameUtils().getIngameList().remove(player);
            return;
        }
        MLGRush.getInstance().getGameUtils().getLobbyList().remove(player);
    }

    public void setScoreboard() {
        MLGRush.getInstance().getScoreboardManager().set(this);
    }

    public void teleport(Locations locations) {
        MLGRush.getInstance().getLocationHandler().teleport(locations, player);
    }

    public boolean isInQueue() {
        return MLGRush.getInstance().getMlgrushUtils().isInQueue(player);
    }

    public boolean isInColdown() {
        return MLGRush.getInstance().getMlgrushUtils().isInCooldown(player);
    }

    BukkitTask bukkitTask;

    public void setCooldown(boolean cooldown) {
        if (cooldown) {
            MLGRush.getInstance().getMlgrushUtils().addCooldown(player);
            bukkitTask = Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
                MLGRush.getInstance().getMlgrushUtils().removeCooldown(player);
            }, 50L);
            return;
        }
        MLGRush.getInstance().getMlgrushUtils().removeCooldown(player);
    }

    public void setLobbyItems() {
        Inventory inventory = player.getInventory();
        inventory.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD)
                .setDisplayName(MLGRush.getInstance().getStringUtils().getItemNames().get("sword"))
                .build());
        inventory.setItem(4, new ItemBuilder(Material.CHEST)
                .setDisplayName(MLGRush.getInstance().getStringUtils().getItemNames().get("settings"))
                .build());
        inventory.setItem(8, SkullBuilder.getUrlSkull("8652e2b936ca8026bd28651d7c9f2819d2e923697734d18dfdb13550f8fdad5f")
                .setDisplayName(MLGRush.getInstance().getStringUtils().getItemNames().get("leave"))
                .build());
    }

    public void sendTitle(String title, String subTitle, int fadeIn, int show, int fadeOut) {
        PacketPlayOutTitle timesPacket = new PacketPlayOutTitle(fadeIn, show, fadeOut);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
        PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}"));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(timesPacket);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitlePacket);
    }

    public void setQueue(boolean queue) {
        if (queue) {
            MLGRush.getInstance().getMlgrushUtils().addQueue(player);
            return;
        }
        MLGRush.getInstance().getMlgrushUtils().removeQueue(player);
    }
}
