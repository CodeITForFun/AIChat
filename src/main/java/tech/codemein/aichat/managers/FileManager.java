package tech.codemein.aichat.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import tech.codemein.aichat.Main;
import tech.codemein.aichat.utils.ColorUtil;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public static File configFile = new File(Main.getInstance().getDataFolder(), "config.yml");
    public static YamlConfiguration config;
    public void onStartup() {
        if(!configFile.exists()) Main.getInstance().saveResource("config.yml", true);

        config = new YamlConfiguration().loadConfiguration(configFile);
    }

    public void setStringInConfig(String key, String value) {
        if (config == null) { config = new YamlConfiguration().loadConfiguration(configFile); }
        config.set(key, value);
        try {
            config.save(configFile);
        } catch (IOException e) {
            Main.getInstance().getLogger().warning("Failed to set string. Executed with this error: \n" + e);
        }
    }

    public void reloadConfig() {
        File cfgFile = new File(Main.getInstance().getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(cfgFile);
    }

    public boolean getBooleanFromConfig(String b) {
        if (config == null) {
            config = new YamlConfiguration().loadConfiguration(configFile);
        } return config.getBoolean(b);
    }

    public String getStringFromConfig(String string) {
        if (config == null) {
            config = new YamlConfiguration().loadConfiguration(configFile);
        } return ColorUtil.translate(config.getString(string));
    }
}
