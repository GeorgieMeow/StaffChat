package com.github.GeorgieMeow.staffChat;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;

public final class StaffChat extends JavaPlugin {
    private LuckPerms luckPerms;
    @Override
    public void onEnable() {
            registerCommands();
            getLogger().info("-------------------------------");
            getLogger().info("StaffChat started successfully!");
            getLogger().info("-------------------------------");
            saveDefaultConfig();
        luckPerms = getServer().getServicesManager()
                .load(LuckPerms.class);
            if (luckPerms != null) {

        } else {
            getLogger().warning(
                    "LuckPerms not found! Player prefixes will be empty!"
            );
        }
    }
    void registerCommands() {
        StaffChatCommand staffChatCommand = new StaffChatCommand();
        Objects.requireNonNull(getCommand("StaffChat")).setExecutor(staffChatCommand);
        Objects.requireNonNull(getCommand("StaffChat")).setTabCompleter(staffChatCommand);

        Objects.requireNonNull(getServer().getPluginManager()).registerEvents(staffChatCommand, this);
    }

    public String getPlayerPrefix(Player player) {
        if (luckPerms == null) {
            return "";
        }

        User user = luckPerms.getUserManager()
                .getUser(player.getUniqueId());

        if (user == null) {
            return "";
        }

        String prefix = user.getCachedData()
                .getMetaData(
                        luckPerms.getContextManager()
                                .getQueryOptions(player)
                )
                .getPrefix();

        return prefix != null ? prefix : "";
    }


    @Override
    public void onDisable() {
        getLogger().info("-----------------------------------");
        getLogger().info("StaffChat has now stopped! Goodbye!");
        getLogger().info("-----------------------------------");
    }
}
