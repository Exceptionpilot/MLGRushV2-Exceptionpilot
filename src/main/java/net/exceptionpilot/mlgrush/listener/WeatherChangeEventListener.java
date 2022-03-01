package net.exceptionpilot.mlgrush.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * @author Jonas | Exceptionpilot#5555
 * Created on 10.06.2021 «» 20:00
 * Class «» WeatherChangeEventListener
 **/

public class WeatherChangeEventListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
