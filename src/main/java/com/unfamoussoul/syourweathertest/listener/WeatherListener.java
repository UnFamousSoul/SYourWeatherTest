package com.unfamoussoul.syourweathertest.listener;


import com.unfamoussoul.syourweathertest.SYourWeatherTest;
import com.unfamoussoul.syourweathertest.data.DataSaver;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public record WeatherListener(SYourWeatherTest plugin) implements Listener {

    @EventHandler
    private void onJoin(@NotNull PlayerJoinEvent event) {
        DataSaver ds = plugin.getDataSaver();
        Player player = event.getPlayer();
        WeatherType type = ds.get(player);
        ds.apply(player, type);
    }
}