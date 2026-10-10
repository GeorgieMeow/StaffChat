package com.github.GeorgieMeow.staffChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StaffCallCommand implements CommandExecutor, TabCompleter {
    String prefix = JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.prefix", "&c[SC] ");
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(sender instanceof Player ) {
            if (sender.hasPermission("StaffChat.isStaff")) {
                if (args.length == 1) {
                    String format = String.valueOf(LegacyComponentSerializer.legacy('&').deserialize(prefix + JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.call", "&6--- [%player%] wants you in staff chat! ---")));
                    // Replace placeholders with the actual sender name and message
                    String formatted = format.replace("%player%", sender.getName());
                    Objects.requireNonNull(Bukkit.getPlayer(args[0])).sendActionBar(Component.text(formatted));
                } else {
                    sender.sendMessage(prefix + JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.usage", "Usage: /staffCall [Player]"));
                }
                return true;

            } else {
                sender.sendMessage(JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.no-permission", "You don't have permission to run this command!"));
                return false;
            }
            }else{
            sender.sendMessage("You must run this command as a player!");
            return false;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1){

            for(Player player : Bukkit.getOnlinePlayers())
            {
                if(player.hasPermission("StaffChat.isStaff")) {
                    completions.add(player.getName());
                    return completions;
                }
            }
        }
        if(args.length !=1){
            return Collections.emptyList();
        }
    return null;
    }
}
