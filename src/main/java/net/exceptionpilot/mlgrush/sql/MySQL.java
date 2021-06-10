package net.exceptionpilot.mlgrush.sql;

import lombok.SneakyThrows;
import net.exceptionpilot.mlgrush.MLGRush;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 22:48
 * Class «» MySQL
 **/

public class MySQL {


    public static Connection connection;

    public boolean isConnected() {
        return connection != null;
    }

    public void connect(String host, String password, String user, String database) {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + 3306 + "/" + database + "?autoReconnect=true", user, password);
                MLGRush.getInstance().getLogger().log(Level.INFO, "Die MySQL Verbindung wurde erfolgreich hergestellt!");
            } catch (SQLException e) {
                MLGRush.getInstance().getLogger().log(Level.INFO, "Die MySQL Verbindung konnte nicht hergestellt werden! " + e.getMessage());
            }
        }
    }

    @SneakyThrows
    public void createTable() {
        if(isConnected()) {
            PreparedStatement preparedStatement = getPreparedStatement("CREATE TABLE IF NOT EXISTS mlgrush_players (UUID VARCHAR(64), STICK INT(16), BLOCK INT(16), PICKAXE INT(16));");
            preparedStatement.execute();

            PreparedStatement preparedStatement1 = getPreparedStatement("CREATE TABLE IF NOT EXISTS mlgrush_players_stats (UUID VARCHAR(64), REALNAME VARCHAR(64), NAME VARCHAR(64), KILLS INT(16), DEATHS INT(16), WINS INT(16), LOSES INT(16), POINTS INT(16), BEDS INT(16));");
            preparedStatement1.execute();
        }
    }

    public PreparedStatement getPreparedStatement(String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
