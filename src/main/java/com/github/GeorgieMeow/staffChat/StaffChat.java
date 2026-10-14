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
        ToggleStaffChatCommand toggleStaffChatCommand = new ToggleStaffChatCommand();
        MessageStaffCommand messageStaffCommand = new MessageStaffCommand();
        Objects.requireNonNull(getCommand("ToggleStaffChat")).setExecutor(toggleStaffChatCommand);
        Objects.requireNonNull(getCommand("ToggleStaffChat")).setTabCompleter(toggleStaffChatCommand);

        Objects.requireNonNull(getCommand("MessageStaff")).setExecutor(messageStaffCommand);
        Objects.requireNonNull(getCommand("MessageStaff")).setTabCompleter(messageStaffCommand);

        Objects.requireNonNull(getServer().getPluginManager()).registerEvents(toggleStaffChatCommand, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("-----------------------------------");
        getLogger().info("StaffChat has now stopped! Goodbye!");
        getLogger().info("-----------------------------------");
    }
}
