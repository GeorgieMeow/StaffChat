package com.github.GeorgieMeow.staffChat;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class StaffChat extends JavaPlugin {
boolean started = false;
    @Override
    public void onEnable() {
            registerCommands();
            getLogger().info("-------------------------------");
            getLogger().info("StaffChat started successfully!");
            getLogger().info("-------------------------------");
            started = true;
    }
    void registerCommands() {
        StaffChatCommand staffChatCommand = new StaffChatCommand();
        Objects.requireNonNull(getCommand("StaffChat")).setExecutor(staffChatCommand);
        Objects.requireNonNull(getCommand("StaffChat")).setTabCompleter(staffChatCommand);

        Objects.requireNonNull(getServer().getPluginManager()).registerEvents(staffChatCommand, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("-----------------------------------");
        getLogger().info("StaffChat has now stopped! Goodbye!");
        getLogger().info("-----------------------------------");
    }
}
