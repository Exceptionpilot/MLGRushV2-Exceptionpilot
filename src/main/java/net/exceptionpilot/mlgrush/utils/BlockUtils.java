package net.exceptionpilot.mlgrush.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 21:35
 * Class «» BlockUtils
 **/

@Getter
public class BlockUtils {

    public HashMap<String, ArrayList<Block>> blockHashMap = new HashMap<>();

    public void clearBlocks(String map) {
        try {
            ArrayList<Block> blockArrayList = new ArrayList<>();
            for(int i = 0; i < blockHashMap.get(map).size(); i++) {
                blockArrayList.add(blockHashMap.get(map).get(i));
            }
            for(int i = 0; i < blockArrayList.size(); i++ ){
                blockArrayList.get(i).getWorld().getBlockAt(blockArrayList.get(i).getLocation()).setType(Material.AIR);
            }
            blockHashMap.remove(map);
        } catch (Exception exception) { }
    }

    public void add(String map, Block block) {
        if(blockHashMap.get(map) == null) {
            blockHashMap.put(map, new ArrayList<>());
            blockHashMap.get(map).add(block);
        }
        blockHashMap.get(map).add(block);
    }

    public void debugClear() {
        for(String s : blockHashMap.keySet()) {
            clearBlocks(s);
        }
    }
}
