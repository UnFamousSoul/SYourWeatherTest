package com.unfamoussoul.syourweathertest;

import com.unfamoussoul.syourweathertest.command.CommandHandler;
import com.unfamoussoul.syourweathertest.config.Config;
import com.unfamoussoul.syourweathertest.config.ConfigHandler;
import com.unfamoussoul.syourweathertest.data.DataHandler;
import com.unfamoussoul.syourweathertest.listener.TridentListener;
import com.unfamoussoul.syourweathertest.listener.WeatherListener;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class SYourWeatherTest extends JavaPlugin {

    private final ConfigHandler cfgH = new ConfigHandler(this, "config.yml");
    private final DataHandler ds = new DataHandler(this);

    private Config cfg;

    @Override
    public void onEnable() {
        loadConfig();
        registerCommands();
        registerListeners();
    }

    public void loadConfig() {
        String ver = getPluginMeta().getVersion();
        cfgH.setup();
        cfg = new Config(cfgH, ver);
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new WeatherListener(this), this);
        pm.registerEvents(new TridentListener(this), this);
    }

    private void registerCommands() {
        LifecycleEventManager<@NotNull Plugin> manager = getLifecycleManager();
        CommandHandler commandHandler = new CommandHandler(this);
        manager.registerEventHandler(LifecycleEvents.COMMANDS, commandHandler::run);
    }

    public @NotNull Config getCfg() {
        return cfg;
    }

    public @NotNull DataHandler getDataSaver() {
        return ds;
    }
}
