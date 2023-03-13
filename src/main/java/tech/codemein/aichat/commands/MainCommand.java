package tech.codemein.aichat.commands;

import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.codemein.aichat.Main;
import tech.codemein.aichat.managers.FileManager;
import tech.codemein.aichat.utils.ColorUtil;

public class MainCommand implements CommandExecutor {
    private String SpigotMCUrlForPlugin = null;

    /**
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return Command
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Failed to execute command, are connected to server?");
            return true;
        }

        Player player = (Player) sender;
        if (args.length >= 0) {
            switch (args[0]) {
                case "help":
                    TextComponent discord = new TextComponent(ColorUtil.translate("&9&lDISCORD SUPPORT&7"));
                    TextComponent spigotmc = new TextComponent(ColorUtil.translate("&e&lSpigotMC"));
                    discord.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/Yxtc7e4naJ"));
                    spigotmc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/" + SpigotMCUrlForPlugin));
                    discord.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to Redirect").create()));
                    spigotmc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to Redirect").create()));
                    player.sendMessage(ColorUtil.translate("&8⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛\n\n"));
                    player.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ &r"));
                    sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ &aSurvival Manager For Minecraft Server\n&8[&cChatGPT&8] &b→ &r" +
                            "\n   &a&lChatGPT: \n" +
                            "   &r&2/caichat help    &7Shows this message\n" +
                            "   &b&lOthers: \n" +
                            "   &r&9/caichat reload [config] / [plugin]    &7Reloads config/plugin\n" +
                            "   &r&9/caichat version    &7Displays the current and latest version\n" +
                            "   &r&9/caichat [text]    &7Ask ChatGPT for message\n"
                    ));

                    player.sendMessage("\n    ");
                    BaseComponent[] dDsc15 = new BaseComponent[]{new TextComponent(ColorUtil.translate("&8[&cChatGPT&8] &b→ &aHelpful: ")), discord, new TextComponent(ColorUtil.translate("  &7x  ")), spigotmc, new TextComponent(ColorUtil.translate("\n&8[&cChatGPT&8] &b→ &7"))};
                    player.spigot().sendMessage(dDsc15);
                    player.sendMessage(ColorUtil.translate("&8⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛"));
                case "ver":
                case "version":
                    if (!(sender.hasPermission("cai.ver") || sender.hasPermission("cai.*"))) {
                        sender.sendMessage(ColorUtil.translate(FileManager.config.getString("No-Permission")));
                        return true;
                    }
                    sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ Your version is: &c" + Main.getInstance().getDescription().getVersion()));
                    return true;
                /*case "update":
                    if (!(sender.hasPermission("cai.set") || sender.hasPermission("cai.*"))) {
                        sender.sendMessage(ColorUtil.translate(FileManager.config.getString("No-Permission")));
                        return true;
                    }
                    if (args.length > 1) {
                        switch (args[1]) {

                            case "broadcast":
                                if (args.length <= 2) {
                                    sender.sendMessage(ColorUtil.translate("&8[&aSurvival&8] &con/off."));
                                    return true;
                                }

                                if (!(args[2].equals("on") || args[2].equals("off"))) {
                                    sender.sendMessage(ColorUtil.translate("&8[&aSurvival&8] &con/off."));
                                    return true;
                                }

                                sender.sendMessage(ColorUtil.translate("&8[&aSurvival&8] &7Setting..."));
                                boolean broadcastenable;
                                if (args[2] == "off") {
                                    broadcastenable = true;
                                } else {
                                    broadcastenable = false;
                                }

                                new FileManager().reloadConfig();

                                String broadcasting;
                                if (args[2] == "off") {
                                    broadcasting = "&cDisabled";
                                } else {
                                    broadcasting = "&aEnabled";
                                }
                                sender.sendMessage(ColorUtil.translate("&8[&aSurvival&8] &aBroadcast is now " + broadcasting));
                                return true;
                        }
                    } else {
                        sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ Please choose between key"));
                    }
                    return true;*/
                case "reload":
                    if (!(sender.hasPermission("cai.reload") || sender.hasPermission("cai.*"))) {
                        sender.sendMessage(ColorUtil.translate(new FileManager().getStringFromConfig("No-Permission")));
                        return true;
                    }
                    if(args.length > 1) {
                        switch (args[1]) {
                            case "config":
                                sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ &7Reloading config..."));
                                new FileManager().reloadConfig();
                                sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ &aReloaded"));
                                return true;
                            case "plugin":
                                sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ &7Reloading plugin... (This don't have 100% effectivity)"));
                                Bukkit.getPluginManager().disablePlugin(Main.getInstance());
                                Bukkit.getPluginManager().enablePlugin(Main.getInstance());
                                sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ &aReloaded"));
                                return true;
                        }
                    } else {
                        sender.sendMessage(ColorUtil.translate("&8[&cChatGPT&8] &b→ &bPlease choose config/plugin"));
                    }
                    return true;
            }
        }
        return true;
    }
}
