package com.github.GeorgieMeow.staffChat;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class StaffChat extends JavaPlugin {
boolean started = false;
    @Override
    public void onEnable() {
            StaffChatCommand staffChatCommand = new StaffChatCommand();
            Objects.requireNonNull(getCommand("StaffChat")).setExecutor(staffChatCommand);
            Objects.requireNonNull(getCommand("StaffCall")).setExecutor(new StaffCallCommand());
            getServer().getPluginManager().registerEvents(staffChatCommand, this);
            getLogger().info("-------------------------------");
            getLogger().info("StaffChat started successfully!");
            getLogger().info("-------------------------------");
            started = true;
    }

    @Override
    public void onDisable() {
        getLogger().info("-----------------------------------");
        getLogger().info("StaffChat has now stopped! Goodbye!");
        getLogger().info("-----------------------------------");
    }
}
