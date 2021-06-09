package net.exceptionpilot.mlgrush.manager;

import lombok.Getter;
import lombok.Setter;
import net.exceptionpilot.mlgrush.MLGRush;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 19:30
 * Class «» SetupManager
 **/

@Setter
@Getter
public class SetupManager {

    public HashMap<Player, Integer> setupList = new HashMap<>();
    public ArrayList<Player> setup = new ArrayList<>();
    String map = null;

    public void start(Player player) {
        setupList.put(player, 0);
        setup.add(player);
        player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schreibe nun den Namen der Map in den Chat!");
    }

    public void push(Player player) {
        int i = setupList.get(player);
        int a = setupList.get(player) + 1;
        switch (i + 1) {
            case 1:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Gehe zum Spawn von Team §cRot §7und schreibe §7'fertig'");
                break;
            case 2:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Gehe zum Spawn von Team §9Blau §7und schreibe §7'fertig'");
                break;
            case 3:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schlage nun das obere Stück des Bettes von Team §cRot");
                break;
            case 4:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schlage nun das untere Stück des Bettes von Team §cRot");
                break;
            case 5:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schlage nun das obere Stück des Bettes von Team §9Blau");
                break;
            case 6:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schlage nun das untere Stück des Bettes von Team §9Blau");
                break;
            case 7:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Gehe nun an den tiefsten Punkt der Map! Und schreibe 'fertig'");
                break;
            case 8:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Gehe nun an den höhsten Punkt der Map! Und schreibe 'fertig'");
                break;
            case 9:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§7Schreibe nun 'fertig' um das Setup zu beenden!");
                break;
            case 10:
                player.sendMessage(MLGRush.getInstance().getPrefix() + "§aDu hast erfolgreich das Setup beendet!");
                Bukkit.reload();
                setup.remove(player);
                setupList.remove(player);
                break;
        }
        if(a != 10) {
            setupList.remove(player);
            setupList.put(player, a);
        }
    }
}
