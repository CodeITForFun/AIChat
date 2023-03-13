package tech.codemein.aichat;

import org.bukkit.plugin.java.JavaPlugin;
import tech.codemein.aichat.managers.PluginManager;

public final class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        new PluginManager().start();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
