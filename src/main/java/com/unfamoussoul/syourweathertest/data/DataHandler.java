package com.unfamoussoul.syourweathertest.data;

import org.bukkit.NamespacedKey;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DataHandler {

    private final NamespacedKey weatherKey;

    public DataHandler(@NotNull JavaPlugin plugin) {
        weatherKey = new NamespacedKey(plugin, "weather");
    }

    public @Nullable WeatherType get(@NotNull Player player) {
        Integer value = player.getPersistentDataContainer().get(weatherKey, PersistentDataType.INTEGER);
        if (value == null) return null;

        WeatherType[] all = WeatherType.values();
        if (value < 0 || value >= all.length) return null;
        return all[value];
    }

    public void save(@NotNull Player player, @Nullable WeatherType option) {
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (option == null) {
            pdc.remove(weatherKey);
            return;
        }
        pdc.set(weatherKey, PersistentDataType.INTEGER, option.ordinal());
    }

    public void apply(@NotNull Player player, @Nullable WeatherType type) {
        if (type == null) {
            player.resetPlayerWeather();
            return;
        }
        player.setPlayerWeather(type);
    }

}
