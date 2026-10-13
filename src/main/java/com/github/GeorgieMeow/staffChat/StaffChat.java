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
        StaffChatToggleCommand staffChatToggleCommand = new StaffChatToggleCommand();
        StaffCallCommand staffCallCommand = new StaffCallCommand();
        StaffChatCommand staffChatCommand = new StaffChatCommand();
        Objects.requireNonNull(getCommand("StaffChatToggle")).setExecutor(staffChatToggleCommand);
        Objects.requireNonNull(getCommand("StaffChatToggle")).setTabCompleter(staffChatToggleCommand);

        Objects.requireNonNull(getCommand("StaffChat")).setExecutor(staffChatCommand);
        Objects.requireNonNull(getCommand("StaffChat")).setTabCompleter(staffChatCommand);

        Objects.requireNonNull(getCommand("StaffCall")).setExecutor(staffCallCommand);
        Objects.requireNonNull(getCommand("StaffCall")).setTabCompleter(staffCallCommand);

        getServer().getPluginManager().registerEvents(staffChatToggleCommand, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("-----------------------------------");
        getLogger().info("StaffChat has now stopped! Goodbye!");
        getLogger().info("-----------------------------------");
    }
}
