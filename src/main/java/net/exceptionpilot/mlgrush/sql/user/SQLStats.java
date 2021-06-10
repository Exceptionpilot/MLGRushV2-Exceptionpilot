package net.exceptionpilot.mlgrush.sql.user;

import lombok.SneakyThrows;
import net.exceptionpilot.mlgrush.MLGRush;
import org.bukkit.Material;

import javax.naming.ldap.PagedResultsControl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 12:51
 * Class «» SQLStats
 **/

public class SQLStats {

    String name;

    @Deprecated
    public SQLStats(String name) {
        this.name = name.toLowerCase();
    }

    @SneakyThrows
    public String getKd() {
        int kills = getFormStats("KILLS");
        int deaths = getFormStats("DEATHS");
        double kd = (double) kills / (double) deaths;
        return String.format("%.2f", kd).replace("NaN", "0.00");
    }

    @SneakyThrows
    public String getName(String type) {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players_stats WHERE NAME=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString(type);
    }
    @SneakyThrows
    public int getFormStats(String type) {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players_stats WHERE NAME=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(type);
    }

    @SneakyThrows
    public void add(String type) {
        int before = getFormStats(type);
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("UPDATE mlgrush_players_stats SET " + type + "=? WHERE NAME=?");
        preparedStatement.setInt(1, before + 1);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public void add(String type, boolean win) {
        int before = getFormStats(type);
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("UPDATE mlgrush_players_stats SET " + type + "=? WHERE NAME=?");
        preparedStatement.setInt(1, before + 10);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public List<String> getAllPlayers() {
        List<String> players = new ArrayList<>();
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players_stats ORDER BY POINTS DESC");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            players.add(resultSet.getString("REALNAME"));
        }
        return players;
    }

    @SneakyThrows
    public boolean isPlayerInDB() {
        PreparedStatement preparedStatement = MLGRush.getInstance().getMySQL().getPreparedStatement("SELECT * FROM mlgrush_players_stats WHERE NAME=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}
