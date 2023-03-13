package tech.codemein.aichat.commands;

import io.github.jetkai.openai.api.CreateChatCompletion;
import io.github.jetkai.openai.api.data.completion.chat.ChatCompletionData;
import io.github.jetkai.openai.api.data.completion.chat.message.ChatCompletionMessageData;
import io.github.jetkai.openai.openai.OpenAI;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.codemein.aichat.Main;
import tech.codemein.aichat.handers.AIHandler;
import tech.codemein.aichat.managers.FileManager;
import tech.codemein.aichat.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

public class ASKCommand implements CommandExecutor, TabCompleter {
    private final List<ChatCompletionMessageData> messageHistory = new ArrayList<>();
    private static String response;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Usage: /ask Hello, how are you today?");
            return true;
        }

        player.sendMessage(ColorUtil.translate(FileManager.config.getString("prefix") + "&7 Question: " + String.join(" ", args)));

        AIHandler gpt = new AIHandler();

        //The first message that we want to send
        String message = String.join(" ", args);

        //Response from ChatGPT
        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 2);
        try {
            response = gpt.exampleBuilders(message);
        } catch (Exception e) {
            sender.sendMessage(ColorUtil.translate(FileManager.config.getString("prefix") + "&c Error: Failed, please look at console and check your API Key if is correct. If you do not know what to do, ask on out discord."));
            Main.getInstance().getLogger().warning(e.getMessage());
            return true;
        }
        sender.sendMessage(ColorUtil.translate(FileManager.config.getString("prefix") + "&7" +  response));
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1, 2);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return null;
    }
}
