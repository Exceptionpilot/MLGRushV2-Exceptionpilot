package net.exceptionpilot.mlgrush.sql.user;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.SneakyThrows;
import net.exceptionpilot.mlgrush.MLGRush;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 22:49
 * Class «» SQLPlayer
 **/

public class SQLPlayer {

    Player player;

    public SQLPlayer(Player player) {
        this.player = player;
        if (!isExists()) {
            create();
        }
    }

    @SneakyThrows
    public void create() {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("INSERT INTO mlgrush_players (UUID, STICK, BLOCK, PICKAXE) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, player.getUniqueId().toString());
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 4);
        preparedStatement.setInt(4, 1);
        preparedStatement.executeUpdate();
    }


    @SneakyThrows
    public boolean isExists() {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players WHERE UUID=?");
        preparedStatement.setString(1, player.getUniqueId().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @SneakyThrows
    public int getSlot(String item) {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players WHERE UUID=?");
        preparedStatement.setString(1, player.getUniqueId().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int slot = resultSet.getInt(item);
        resultSet.close();
        preparedStatement.close();
        return slot;
    }

    @SneakyThrows
    public void set(String s, int i) {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("UPDATE mlgrush_players SET " + s + "=? WHERE UUID=?");
        preparedStatement.setInt(1, i);
        preparedStatement.setString(2, player.getUniqueId().toString());
        preparedStatement.executeUpdate();
    }
}
