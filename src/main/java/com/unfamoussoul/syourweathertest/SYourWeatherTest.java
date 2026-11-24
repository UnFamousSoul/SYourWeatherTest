package com.unfamoussoul.syourweathertest;

import com.unfamoussoul.syourweathertest.config.Config;
import com.unfamoussoul.syourweathertest.config.ConfigHandler;
import com.unfamoussoul.syourweathertest.data.DataSaver;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class SYourWeatherTest extends JavaPlugin {

    private final ConfigHandler cfgH = new ConfigHandler(this, "config.yml");
    private final DataSaver ds = new DataSaver(this);

    private Config cfg;

    @Override
    public void onEnable() {
        loadConfig();
    }

    private void loadConfig() {
        String ver = getPluginMeta().getVersion();
        cfgH.setup();
        cfg = new Config(cfgH, ver);
    }

    public Config getCfg() {
        return cfg;
    }
}
