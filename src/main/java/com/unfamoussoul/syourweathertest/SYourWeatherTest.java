package com.unfamoussoul.syourweathertest;

import com.unfamoussoul.syourweathertest.command.CommandHandler;
import com.unfamoussoul.syourweathertest.config.Config;
import com.unfamoussoul.syourweathertest.config.ConfigHandler;
import com.unfamoussoul.syourweathertest.data.DataSaver;
import com.unfamoussoul.syourweathertest.listener.WeatherListener;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class SYourWeatherTest extends JavaPlugin {

    private final ConfigHandler cfgH = new ConfigHandler(this, "config.yml");
    private final DataSaver ds = new DataSaver(this);

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

    public void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new WeatherListener(this), this);
    }

    private void registerCommands() {
        var manager = getLifecycleManager();
        CommandHandler commandHandler = new CommandHandler(this);
        manager.registerEventHandler(LifecycleEvents.COMMANDS, commandHandler::run);
    }

    public Config getCfg() {
        return cfg;
    }

    public DataSaver getDataSaver() {
        return ds;
    }
}
