package com.unfamoussoul.syourweathertest.data;

import org.bukkit.NamespacedKey;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class DataSaver {

    private final NamespacedKey weatherKey;

    public DataSaver(JavaPlugin plugin) {
        weatherKey = new NamespacedKey(plugin, "weather");
    }

    public WeatherType get(@NotNull Player player) {
        Integer value = player.getPersistentDataContainer().get(weatherKey, PersistentDataType.INTEGER);
        if (value == null) {
            return WeatherType.CLEAR;
        }
        return WeatherType.values()[value];
    }

    public void save(@NotNull Player player, WeatherType option) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (option == WeatherType.CLEAR) {
            pdc.remove(weatherKey);
        } else {
            pdc.set(weatherKey, PersistentDataType.INTEGER, option.ordinal());
        }
    }

}
