package net.exceptionpilot.mlgrush.sql.user;

import lombok.SneakyThrows;
import net.exceptionpilot.mlgrush.MLGRush;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
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
        if(!isInStatDB()) {
            createStats();
        } else {
            updateName();
        }
    }

    @SneakyThrows
    public void updateName() {
        if(isInStatDB()) {
            if(!isNameExists()) {
                PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("UPDATE mlgrush_players_stats SET REALNAME=? WHERE UUID=?");
                preparedStatement.setString(1, player.getName());
                preparedStatement.setString(2, player.getUniqueId().toString());
                preparedStatement.executeUpdate();

                PreparedStatement preparedStatement1 = MLGRush.getInstance().getMySQL().getPreparedStatement("UPDATE mlgrush_players_stats SET NAME=? WHERE UUID=?");
                preparedStatement1.setString(1, player.getName().toLowerCase());
                preparedStatement1.setString(2, player.getUniqueId().toString());
                preparedStatement1.executeUpdate();
            }
        }
    }

    @SneakyThrows
    public boolean isInStatDB() {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players_stats WHERE UUID=?");
        preparedStatement.setString(1, player.getUniqueId().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @SneakyThrows
    public boolean isNameExists() {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players_stats WHERE REALNAME=?");
        preparedStatement.setString(1, player.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @SneakyThrows
    public void createStats() {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("INSERT INTO mlgrush_players_stats (UUID, REALNAME, NAME, KILLS, DEATHS, WINS, LOSES, POINTS, BEDS) VALUES (?, ?, ?, ?, ?, ? ,?, ?, ?)");
        preparedStatement.setString(1, player.getUniqueId().toString());
        preparedStatement.setString(2, player.getName());
        preparedStatement.setString(3, player.getName().toLowerCase());
        preparedStatement.setInt(4, 0);
        preparedStatement.setInt(5, 0);
        preparedStatement.setInt(6, 0);
        preparedStatement.setInt(7, 0);
        preparedStatement.setInt(8, 0);
        preparedStatement.setInt(9, 0);
        preparedStatement.executeUpdate();
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
