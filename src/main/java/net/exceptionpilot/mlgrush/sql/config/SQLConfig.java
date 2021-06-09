package net.exceptionpilot.mlgrush.sql.config;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 09.06.2021 «» 22:50
 * Class «» SQLConfig
 **/

@Getter
public class SQLConfig {

    File file = new File("plugins//MLGRush//mysql.yml");
    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    @SneakyThrows
    public SQLConfig() {
        if(!file.exists()) {
            file.createNewFile();
            cfg.set("host", "host");
            cfg.set("database", "database");
            cfg.set("password", "password");
            cfg.set("user", "user");
            cfg.save(file);
        }
    }
}
