package com.unfamoussoul.syourweathertest.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public final class Config {

    private final MiniMessage mm = MiniMessage.miniMessage();

    public final Component usage;

    public final Component reloaded;
    public final Component noPermission;

    public final String weatherSet;
    public final String weatherReset;

    public final Component menuTitle;

    public final Component weatherClear;
    public final Component weatherRain;
    public final Component weatherStorm;

    public final Component menuClear;

    public Config(@NotNull ConfigHandler handler, String version) {
        FileConfiguration cfg = handler.get();

        usage = mm("""
                        SYourWeatherTest %s by UnFamousSoul:
                        
                        """.formatted(version));

        reloaded = mm(cfg.getString("reloaded", ""));
        noPermission = mm(cfg.getString("no_permission", ""));

        weatherSet = cfg.getString("weather_set", "");
        weatherReset = cfg.getString("weather_reset", "");

        menuTitle = mm(cfg.getString("menu.title", ""));
        menuClear = mm(cfg.getString("menu.clear", ""));

        weatherClear = mm(cfg.getString("weather.clear", ""));
        weatherRain = mm(cfg.getString("weather.rain", ""));
        weatherStorm = mm(cfg.getString("weather.storm", ""));
    }

    public @NotNull Component mm(String text, TagResolver... placeholders) {
        return mm.deserialize(text, placeholders);
    }
}
