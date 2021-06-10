package net.exceptionpilot.mlgrush;

import lombok.Getter;
import net.exceptionpilot.mlgrush.commands.BuildCommand;
import net.exceptionpilot.mlgrush.commands.QuitCommand;
import net.exceptionpilot.mlgrush.commands.SetupCommand;
import net.exceptionpilot.mlgrush.commands.SpecCommand;
import net.exceptionpilot.mlgrush.listener.*;
import net.exceptionpilot.mlgrush.location.LocationHandler;
import net.exceptionpilot.mlgrush.location.MapLocations;
import net.exceptionpilot.mlgrush.manager.MapManager;
import net.exceptionpilot.mlgrush.manager.SetupManager;
import net.exceptionpilot.mlgrush.score.ScoreboardManager;
import net.exceptionpilot.mlgrush.sql.MySQL;
import net.exceptionpilot.mlgrush.sql.config.SQLConfig;
import net.exceptionpilot.mlgrush.tablist.TablistHandler;
import net.exceptionpilot.mlgrush.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 17:01
 * Class «» MLGRush
 **/

@Getter
public class MLGRush extends JavaPlugin {

    @Getter
    private static MLGRush instance;
    private GameUtils gameUtils;
    private LocationHandler locationHandler;
    private StringUtils stringUtils;
    private MLGRushUtils queueUtils;
    private ScoreboardManager scoreboardManager;
    private TablistHandler tablistHandler;
    private MapLocations mapLocations;
    private MapManager mapManager;
    private SetupManager setupManager;
    private TeamUtils teamUtils;
    private MapUtils mapUtils;
    private BlockUtils blockUtils;
    private MySQL mySQL;
    private SQLConfig sqlConfig;

    public String prefix = "§8»§7» §eMLGRush §8┃ ";
    public String noPerms = prefix + "§7Dazu hast du keine §cBerechtigung§7§l!";

    @Override
    public void onEnable() {
        instance = this;
        gameUtils = new GameUtils();
        stringUtils = new StringUtils();
        locationHandler = new LocationHandler();
        queueUtils = new MLGRushUtils();
        scoreboardManager = new ScoreboardManager();
        tablistHandler = new TablistHandler();
        mapLocations = new MapLocations();
        setupManager = new SetupManager();
        teamUtils = new TeamUtils();
        mapManager = new MapManager();
        mapUtils = new MapUtils();
        mySQL = new MySQL();
        blockUtils = new BlockUtils();
        sqlConfig = new SQLConfig();

        queueUtils.spawn();
        mySQL.connect(sqlConfig.getCfg().getString("host"), sqlConfig.getCfg().getString("password"), sqlConfig.getCfg().getString("user"), sqlConfig.getCfg().getString("database"));
        mySQL.createTable();

        this.kick();
        this.intListeners();
        this.intCommands();
        this.intWorlds();
        this.getLogger().log(Level.INFO, "Das Plugin wurde erfolgreich gestartet!");
    }

    @Override
    public void onDisable() {
        queueUtils.kill();
        blockUtils.debugClear();
        if(mySQL.isConnected()) {
            mySQL.close();
        }
    }

    private void intCommands() {
        this.getCommand("setup").setExecutor(new SetupCommand());
        this.getCommand("quit").setExecutor(new QuitCommand());
        this.getCommand("build").setExecutor(new BuildCommand());
        this.getCommand("spec").setExecutor(new SpecCommand());
    }

    private void intWorlds() {
        for(World world : Bukkit.getWorlds()) {
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setTime(6000);
            world.setThundering(false);
            world.setWeatherDuration(0);
        }
    }

    private void kick() {
        try {
            Bukkit.getOnlinePlayers().forEach(all -> {
                all.kickPlayer(prefix + "§cDer Server wurde reloadet!");
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void intListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinEventListener(), this);
        pluginManager.registerEvents(new PlayerQuitEventListener(), this);
        pluginManager.registerEvents(new PlayerInteractAtEntityEventListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeEventListener(), this);
        pluginManager.registerEvents(new EntityDamageByEntityEventListener(), this);
        pluginManager.registerEvents(new EntityDamageEventListener(), this);
        pluginManager.registerEvents(new AsyncPlayerChatEventListener(), this);
        pluginManager.registerEvents(new BlockBreakEventListener(), this);
        pluginManager.registerEvents(new PlayerInteractEventListener(), this);
        pluginManager.registerEvents(new InventoryClickEventListener(), this);
        pluginManager.registerEvents(new BlockPlaceEventListener(), this);
        pluginManager.registerEvents(new PlayerMoveEventListener(), this);
        pluginManager.registerEvents(new PlayerDropItemEventListener(), this);
        pluginManager.registerEvents(new PlayerPickupItemEventListener(), this);
        pluginManager.registerEvents(new InventoryCloseEventListener(), this);
        pluginManager.registerEvents(new PlayerBedEnterEventListener(), this);
        pluginManager.registerEvents(new PlayerAchievementAwardedEventListener(), this);
    }
}
