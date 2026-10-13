package com.github.GeorgieMeow.staffChat;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StaffChatToggleCommand implements CommandExecutor, Listener, TabCompleter {
    private static final Map<UUID, Boolean> staffChat = new HashMap<>();
    static StaffChat plugin = JavaPlugin.getPlugin(StaffChat.class);
    static String prefix = plugin.getConfig().getString("messages.prefix", "&c[SC] ");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player player) {
            boolean inStaffChat = staffChat.getOrDefault(player.getUniqueId(), false); //get player data
            if (sender.hasPermission("StaffChat.isStaff")) {

                if (!inStaffChat) {
                    staffChat.put(player.getUniqueId(), true);
                    sender.sendRichMessage(prefix + plugin.getConfig().getString("messages.toggle-on", "&aStaff chat enabled."));

                } else {
                    staffChat.put(player.getUniqueId(), false);
                    sender.sendRichMessage(prefix + plugin.getConfig().getString("messages.toggle-off", "&cStaff chat disabled."));
                }
                return true;

            } else {
                sender.sendMessage(plugin.getConfig().getString("messages.no-permission", "You don't have permission to run this command!"));
                return true;
            }
        } else {
            sender.sendMessage("You must run this command as a player!");
            return true;
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        boolean inStaffChannel = staffChat.getOrDefault(player.getUniqueId(), false);

        if (inStaffChannel) {
            event.setCancelled(true);
            sendStaffMessage(event.getPlayer(), net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText().serialize(event.message()));
        }
    }

    public static void sendStaffMessage(Player sender, String message) {

        String format = prefix + plugin.getConfig().getString("format", "&b%player%&7: &f%message%");
        // Replace placeholders with the actual sender name and message
        String formatted = format.replace("%player%", sender.getName()).replace("%message%", message);
        boolean inStaffChannel = staffChat.getOrDefault(sender.getUniqueId(), false);
        if(!inStaffChannel) {
            sender.sendRichMessage(formatted);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if(player.hasPermission("StaffChat.isStaff")) {
                inStaffChannel = staffChat.getOrDefault(player.getUniqueId(), false);
                if (inStaffChannel) {
                    player.sendRichMessage(formatted);
                }
            }
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
