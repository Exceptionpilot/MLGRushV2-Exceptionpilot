package net.exceptionpilot.mlgrush.utils;

import lombok.Getter;
import net.exceptionpilot.mlgrush.MLGRush;
import net.exceptionpilot.mlgrush.builder.ItemBuilder;
import net.exceptionpilot.mlgrush.location.types.Locations;
import net.exceptionpilot.mlgrush.player.RushPlayer;
import net.exceptionpilot.mlgrush.sql.user.SQLPlayer;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:38
 * Class «» QueueUtils
 **/

@Getter
public class MLGRushUtils {

    Golem golem = null;
    private ArrayList<Player> queueList = new ArrayList<>();
    private ArrayList<Player> cooldown = new ArrayList<>();
    private HashMap<Player, String> matching = new HashMap<>();
    private HashMap<Player, String> inMatching = new HashMap<>();
    private HashMap<Player, Player> match = new HashMap<>();
    private HashMap<Player, String> mapList = new HashMap<>();
    private HashMap<Player, Player> requests = new HashMap<>();

    public void handleSword(Player player, Player matcher) {
        if(player.getItemInHand() == null) return;
        if(player.getItemInHand().getItemMeta() == null) return;
        if(player.getItemInHand().getItemMeta().getDisplayName() == null) return;
        if(!player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().itemNames.get("sword"))) {
            return;
        }
        RushPlayer rushPlayer = RushPlayer.getPlayer(player);
        RushPlayer matchPlayer = RushPlayer.getPlayer(matcher);
        if(matchPlayer.isLobby() && rushPlayer.isLobby()) {
            rushPlayer.setQueue(false);
            if(rushPlayer.isInColdown()) {
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§cBitte warte bevor du die Aktion erneut ausführen kannst!");
                return;
            }
            if (MLGRush.getInstance().getQueueUtils().getRequests().get(matcher) != player) {
                if(getRequests().get(player) == matcher) {
                    player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast die Anfrage §czurückgezogen!");
                    getMatching().remove(player);
                    reset(player);
                    rushPlayer.setScoreboard();
                    rushPlayer.setCooldown(true);
                    return;
                }
                reset(rushPlayer.getPlayer());
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast eine herausforderung §agesendet!");
                matcher.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast eine Herausforderung von §e" + player.getName() + " §aerhalten!");
                MLGRush.getInstance().getQueueUtils().getMatching().put(player, matcher.getName());
                MLGRush.getInstance().getQueueUtils().getInMatching().put(matcher, player.getName());
                rushPlayer.setScoreboard();
                matchPlayer.setScoreboard();
                getRequests().put(player, matcher);
            } else {
                startMatch(player, matcher);
            }
        }
        rushPlayer.forEachReloadSpec();
    }

    public void addPoint(Player player) {
        int a = MLGRush.getInstance().getGameUtils().getPoints().get(player);
        int i = a + 1;
        MLGRush.getInstance().getGameUtils().getPoints().remove(player);
        MLGRush.getInstance().getGameUtils().getPoints().put(player, i);
        RushPlayer rushPlayer = RushPlayer.getPlayer(player);

        RushPlayer rushPlayer1 = RushPlayer.getPlayer(match.get(rushPlayer.getPlayer()));

        rushPlayer.teleportToIngameSpawn();
        rushPlayer1.teleportToIngameSpawn();

        addPotions(player);
        addPotions(rushPlayer1.getPlayer());

        playLoseSound(rushPlayer1.getPlayer());
        playWinSound(rushPlayer.getPlayer());

        rushPlayer.setScoreboard();
        rushPlayer1.setScoreboard();

        rushPlayer.forEachReloadSpec();
        MLGRush.getInstance().getTablistHandler().intIngameTablist(rushPlayer.getPlayer());
        MLGRush.getInstance().getTablistHandler().intIngameTablist(rushPlayer1.getPlayer());
    }

    public void addPotions(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 255));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10, 250));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 1, 250));
    }

    public void playWinSound(Player player) {
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 20, 20);
    }

    public void playLoseSound(Player player) {
        player.playSound(player.getLocation(), Sound.NOTE_BASS, 20, 20);
    }

    public void startMatch(Player player, Player matcher) {

        if(!MLGRush.getInstance().getMapManager().hasMap()) {
            player.sendMessage(MLGRush.getInstance().getPrefix() + "§cMomentan ist keine Map verfügbar!");
            return;
        }

        match.put(player, matcher);
        match.put(matcher, player);
        RushPlayer player2 = RushPlayer.getPlayer(player);
        RushPlayer rushPlayer = RushPlayer.getPlayer(matcher);


        player2.setLobby(false);
        player2.setIngame(true);
        rushPlayer.setQueue(false);
        rushPlayer.setLobby(false);
        rushPlayer.setIngame(true);

        player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Du spielst gegen §e" + rushPlayer.getPlayer().getName() + "§7§l!");
        rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§7Du spielst gegen §e" + player.getName() + "§7§l!");

        player2.setQueue(false);
        rushPlayer.setQueue(false);

        MLGRush.getInstance().getTeamUtils().setRandomTeam(player, matcher);
        String map = MLGRush.getInstance().getMapUtils().getRandomMap();

        rushPlayer.setMap(map);
        player2.setMap(map);

        MLGRush.getInstance().getGameUtils().getPoints().put(player, 0);
        MLGRush.getInstance().getGameUtils().getPoints().put(player2.getPlayer(), 0);
        MLGRush.getInstance().getGameUtils().getPoints().put(rushPlayer.getPlayer(), 0);

        rushPlayer.getPlayer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 255));
        rushPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 1, 250));
        player2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 255));
        player2.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 1, 250));

        MLGRush.getInstance().getTablistHandler().intIngameTablist(player);

        rushPlayer.setScoreboard();
        player2.setScoreboard();

        rushPlayer.forEachReloadSpec();
        MLGRush.getInstance().getTablistHandler().intIngameTablist(rushPlayer.getPlayer());
        MLGRush.getInstance().getTablistHandler().intIngameTablist(player2.getPlayer());

        Bukkit.getScheduler().runTaskLater(MLGRush.getInstance(), () -> {
            rushPlayer.teleportToIngameSpawn();
            player2.teleportToIngameSpawn();
        }, 2L);
    }

    public void leaveMatch(RushPlayer rushPlayer) {
        RushPlayer rushPlayer1 = RushPlayer.getPlayer(match.get(rushPlayer.getPlayer()));
        MLGRush.getInstance().getMapManager().availableMaps.add(rushPlayer.getMap());

        MLGRush.getInstance().getBlockUtils().clearBlocks(rushPlayer.getMap());
        MLGRush.getInstance().getBlockUtils().clearBlocks(rushPlayer1.getMap());

        getRequests().remove(rushPlayer.getPlayer());
        getRequests().remove(rushPlayer1.getPlayer());

        getMatch().remove(rushPlayer.getPlayer());
        getMatch().remove(rushPlayer1.getPlayer());

        getMatching().remove(rushPlayer.getPlayer());
        getMatching().remove(rushPlayer1.getPlayer());

        getMapList().remove(rushPlayer.getPlayer());
        getMapList().remove(rushPlayer1.getPlayer());

        getInMatching().remove(rushPlayer.getPlayer());
        getInMatching().remove(rushPlayer1.getPlayer());

        getQueueList().remove(rushPlayer.getPlayer());
        getQueueList().remove(rushPlayer1.getPlayer());

        MLGRush.getInstance().getGameUtils().getPoints().remove(rushPlayer.getPlayer());
        MLGRush.getInstance().getGameUtils().getPoints().remove(rushPlayer1.getPlayer());

        rushPlayer1.sendTitle("§8» §aGewonnen §8«", "§7Du hast das Spiel §agewonnen!", 20, 20, 20);
        rushPlayer.sendTitle("§8» §cVerloren §8«", "§7Du hast das Spiel §cverloren!", 20, 20, 20);

        rushPlayer1.getPlayer().playSound(rushPlayer1.getPlayer().getLocation(), Sound.LEVEL_UP, 20, 20);
        rushPlayer.getPlayer().playSound(rushPlayer.getPlayer().getLocation(), Sound.NOTE_BASS, 20, 20);

        rushPlayer1.setLobby(true);
        rushPlayer1.teleport(Locations.SPAWN);
        rushPlayer1.setLobbyItems();

        rushPlayer.setLobby(true);
        rushPlayer.teleport(Locations.SPAWN);
        rushPlayer.setLobbyItems();

        rushPlayer1.setScoreboard();
        rushPlayer.setScoreboard();

        rushPlayer.forEachReloadSpec();
        MLGRush.getInstance().getTablistHandler().intIngameTablist(rushPlayer.getPlayer());
        MLGRush.getInstance().getTablistHandler().intIngameTablist(rushPlayer1.getPlayer());
    }

    public void handleQueue(RushPlayer rushPlayer) {
        if(rushPlayer.isSpec() || rushPlayer.isBuildMode()) {
            return;
        }
        if(rushPlayer.getPlayer().getItemInHand() == null) return;
        if(rushPlayer.getPlayer().getItemInHand().getItemMeta() == null) return;
        if(rushPlayer.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null) return;
        if(!rushPlayer.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(MLGRush.getInstance().getStringUtils().itemNames.get("sword"))) {
            return;
        }
        if (rushPlayer.isInColdown()) {
            rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§cBitte warte bevor du diese Aktion erneut ausführst");
            return;
        }
        if (!rushPlayer.isInQueue()) {
            reset(rushPlayer.getPlayer());
            matching.put(rushPlayer.getPlayer(), "§aWarteschlange");
            rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast erfolgreich die Warteschlange §abetreten!");
            rushPlayer.setQueue(true);
            if (queueList.size() >= 2) {
                startMatch(queueList.get(0), rushPlayer.getPlayer());
                return;
            }
            rushPlayer.setScoreboard();
        } else {
            matching.remove(rushPlayer.getPlayer());
            rushPlayer.getPlayer().sendMessage(MLGRush.getInstance().getPrefix() + "§7Du hast erfolgreich die Warteschlange §cverlassen!");
            rushPlayer.setQueue(false);
            rushPlayer.setCooldown(true);
            rushPlayer.setScoreboard();
        }

        rushPlayer.forEachReloadSpec();
    }

    public void reset(Player player) {
        if(getRequests().containsKey(player.getPlayer())) {
            Player old = getRequests().get(player.getPlayer());
            if(old != null) {
                RushPlayer oldPlayer = RushPlayer.getPlayer(old);
                getInMatching().remove(old);
                oldPlayer.setScoreboard();
            }
            getRequests().remove(player.getPlayer());
        }
    }

    public boolean isInCooldown(Player player) {
        return cooldown.contains(player);
    }

    public void addCooldown(Player player) {
        cooldown.add(player);
    }

    public void removeCooldown(Player player) {
        cooldown.remove(player);
    }

    public void addQueue(Player player) {
        queueList.add(player);
    }

    public void removeQueue(Player player) {
        queueList.remove(player);
    }

    public boolean isInQueue(Player player) {
        return queueList.contains(player);
    }

    public void spawn() {
        try {
            Location queueLocation = MLGRush.getInstance().getLocationHandler().getLocation(Locations.QUEUE);
            golem = (Golem) queueLocation.getWorld().spawnEntity(queueLocation, EntityType.IRON_GOLEM);
            golem.setCustomName(MLGRush.getInstance().getStringUtils().queueTitle);
            golem.setCustomNameVisible(true);
            golem.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 365000, 365000));
            Entity entity = ((CraftEntity) golem).getHandle();
            NBTTagCompound nbtTagCompound = entity.getNBTTag();
            if (nbtTagCompound == null) {
                nbtTagCompound = new NBTTagCompound();
            }
            entity.c(nbtTagCompound);
            nbtTagCompound.setInt("NoAI", 1);
            nbtTagCompound.setInt("Silent", 1);
            entity.f(nbtTagCompound);
        } catch (Exception exception) {
            MLGRush.getInstance().getLogger().log(Level.WARNING, "Die Location für die Queue wurde noch nicht gsetzt!");
        }
    }

    public void kill() {
        if (golem != null) {
            golem.setHealth(0);
        }
    }
}
