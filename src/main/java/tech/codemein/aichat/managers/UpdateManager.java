package tech.codemein.aichat.managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.codemein.aichat.Main;
import tech.codemein.aichat.utils.ColorUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import static org.bukkit.Bukkit.getLogger;

public class UpdateManager implements Listener {
    private static String currentVersion = Main.getInstance().getDescription().getVersion();
    public static String latestVersion;
    private static String user = "CodeITForFun";
    private static String repo = "AIChat";
    public static void startUpdater() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                checkForUpdates();
            }
        }, 0L, 20 * 60 * 60 * 24); // Schedule the task to run every 24 hours
    }
    private static void checkForUpdates() {
        try {
            URL url = new URL("https://api.github.com/repos/"+ user + "/" + repo + "/releases"); // Replace with the URL to your GitHub releases
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JsonArray releases = new JsonParser().parse(result.toString()).getAsJsonArray();
            JsonObject latestRelease = releases.get(0).getAsJsonObject();
            latestVersion = latestRelease.get("tag_name").getAsString().replace("v", "");

            if(currentVersion == null) { currentVersion = new FileManager().getStringFromConfig("Version"); }

            if (!currentVersion.equals(latestVersion)) {
                Bukkit.getLogger().warning(ColorUtil.translate("[AIChat] A new update is available: " + latestVersion + " Your version is: " + currentVersion + ". Download it here: https://www.spigotmc.org/resources/web-server-for-minecraft.107949/"));
            } else {
                Bukkit.getLogger().warning(ColorUtil.translate("[AIChat] You are using latest version"));
            }
        } catch (Exception e) {
            getLogger().warning("[AIChat] ----------------------------------------------------------------");
            getLogger().warning("[AIChat] Failed to check for updates, please report bottom error in our discord.\n");
            e.printStackTrace();
            getLogger().warning("\n[AIChat] End of error.");
            getLogger().warning("[AIChat] ----------------------------------------------------------------");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!(player.hasPermission("cai.update") || player.hasPermission("cai.*"))) {
            return;
        }

        TextComponent message = new TextComponent(ColorUtil.translate(FileManager.config.getString("prefix") + " &bA new update of Web Server for minecraft is available: &c" +
                latestVersion + "&b. Your version is: &c" + currentVersion));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/web-server-for-minecraft.107949/"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to Download Manual Update from SpigotMC").create()));

            if (!(currentVersion.equals(latestVersion.toString()))) player.spigot().sendMessage(message);
    }
}
