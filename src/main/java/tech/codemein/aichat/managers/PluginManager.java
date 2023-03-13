package tech.codemein.aichat.managers;

import org.bstats.bukkit.Metrics;
import tech.codemein.aichat.Main;
import tech.codemein.aichat.commands.ASKCommand;
import tech.codemein.aichat.commands.MainCommand;

public class PluginManager {
    public void start() {
        Main.getInstance().getLogger().info("Starting ChatGPT AI for Minecraft by https://codemein.tech using OpenAI API.");

        Main.getInstance().getLogger().info("Loading FileManager.");
            new FileManager().onStartup();
            new FileManager().setStringInConfig("Version", Main.getInstance().getDescription().getVersion());

        Main.getInstance().getLogger().info("Loading Commands.");
        Main.getInstance().getCommand("ask").setExecutor(new ASKCommand());
        Main.getInstance().getCommand("aichat").setExecutor(new MainCommand());

        Metrics metrics = new Metrics(Main.getInstance(), 17944);

        UpdateManager.startUpdater();
    }

}
