package com.github.GeorgieMeow.staffChat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.valueOf;

public class StaffChatCommand implements CommandExecutor, Listener {
    private final Map<UUID, Boolean> staffChat = new HashMap<>();

    String prefix = JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.prefix", "&c[SC] ");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player player) {
            boolean inStaffChat = staffChat.getOrDefault(player.getUniqueId(), false); //get player data
            if (sender.hasPermission("StaffChat.isStaff")) {

                if (!inStaffChat) {
                    staffChat.put(player.getUniqueId(), true);
                    sender.sendMessage(valueOf(LegacyComponentSerializer.legacy('&').deserialize(prefix + JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.toggle-on", "&aStaff chat enabled."))));

                } else {
                    staffChat.put(player.getUniqueId(), false);
                    sender.sendMessage(valueOf(LegacyComponentSerializer.legacy('&').deserialize(prefix + JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.toggle-off", "&cStaff chat disabled."))));
                }
                return true;

            } else {
                sender.sendMessage(JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.no-permission", "You don't have permission to run this command!"));
                return false;
            }
        } else {
            sender.sendMessage("You must run this command as a player!");
            return false;
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

    public void sendStaffMessage(Player sender, String message) {

        String format = valueOf(LegacyComponentSerializer.legacy('&').deserialize(prefix + JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("format", "&b%player%&7: &f%message%")));
        // Replace placeholders with the actual sender name and message
        String formatted = format.replace("%player%", sender.getName()).replace("%message%", message);

        for (Player player : Bukkit.getOnlinePlayers()) {
            boolean inStaffChannel = staffChat.getOrDefault(player.getUniqueId(), false);
            if (inStaffChannel) {
                player.sendMessage(formatted);
            }
        }
    }


}
