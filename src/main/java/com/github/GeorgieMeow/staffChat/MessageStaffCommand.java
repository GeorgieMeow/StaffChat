package com.github.GeorgieMeow.staffChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MessageStaffCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(sender instanceof Player player) {
            if(player.hasPermission("StaffChat.isStaff")) {
                if(args.length == 1) {
                    ToggleStaffChatCommand.sendStaffMessage(player, args[0]);
                    return true;
                }else{
                    return false;
                }
            }else{
                sender.sendMessage(JavaPlugin.getPlugin(StaffChat.class).getConfig().getString("messages.no-permission", "You don't have permission to run this command!"));
                return true;
            }
        }else{
            sender.sendMessage("You must run this command as a player!");
            return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender.hasPermission("StaffChat.isStaff")) {
            List<String> suggestions = new ArrayList<>();
            if (args.length == 1) {
                suggestions.add("[Message]");
            }
            return suggestions;
        }else{

            return List.of();
        }
    }
}
