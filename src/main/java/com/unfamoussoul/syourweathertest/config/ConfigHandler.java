package com.unfamoussoul.syourweathertest.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConfigHandler {

    private final JavaPlugin plugin;
    private final Logger logger;
    private final String filename;

    private FileConfiguration config;
    private File configFile;

    public ConfigHandler(@NotNull JavaPlugin plugin, @NotNull String filename) {
        this.plugin = plugin;
        logger = plugin.getLogger();
        this.filename = filename;
    }

    private void logError(@NotNull Exception e) {
        String text = Arrays.stream(e.getStackTrace())
                .map(p -> p + "\n")
                .collect(Collectors.joining());
        logger.severe(text);
    }

    private void createFolder(@NotNull File file) {
        if (file.exists()) return;
        if (file.mkdir()) return;
        logger.warning("Can't create plugin's directory!");
    }

    private void createConfig() {
        if (configFile.exists()) return;

        try (InputStream in = plugin.getResource(filename)) {
            if (in == null) {
                if (configFile.createNewFile()) return;
                logger.severe("Can't create config file " + filename + "!");
                return;
            }
            Files.copy(in, configFile.toPath());
            logger.info("Config file " + filename + " created!");
        } catch (IOException e) {
            logger.severe("Can't create config file " + filename + "!");
            logError(e);
        }
    }

    public void setup() {
        File file = plugin.getDataFolder();
        createFolder(file);

        configFile = new File(file, filename);
        createConfig();
        reload();
    }

    public FileConfiguration get() {
        if (config == null) reload();
        return config;
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);

        InputStream defConfigStream = plugin.getResource(filename);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defConfigStream)
            );
            config.setDefaults(defConfig);
        }
    }
}