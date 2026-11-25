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
    public final String weatherSet;
    public final Component weatherReset;
    public final String errorInvalidWeather;

    public Config(@NotNull ConfigHandler handler, String version) {
        FileConfiguration cfg = handler.get();

        usage = mm("""
                        SYourWeatherTest %s - UnFamousSoul:
                        
                        /syourweathertest reload - перезагрузить плагин
                        /syourweathertest weather <погода> - поставить погоду
                        /syourweathertest clear - очистить индивидуальную погоду
                        """.formatted(version)
        );

        reloaded = mm(cfg.getString("reloaded", ""));
        weatherSet = cfg.getString("weather_set", "");
        weatherReset = mm(cfg.getString("weather_reset", ""));
        errorInvalidWeather = cfg.getString("error_invalid_weather", "");
    }

    public @NotNull Component mm(String text, TagResolver... placeholders) {
        return mm.deserialize(text, placeholders);
    }
}
