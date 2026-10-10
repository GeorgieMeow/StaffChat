package com.github.GeorgieMeow.staffChat;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class StaffChat extends JavaPlugin {
boolean started = false;
    @Override
    public void onEnable() {
        if(!started){
            StaffChatCommand staffChatCommand = new StaffChatCommand();
    Objects.requireNonNull(getCommand("StaffChat")).setExecutor(staffChatCommand);
    Objects.requireNonNull(getCommand("StaffCall")).setExecutor(new StaffCallCommand());
            getServer().getPluginManager().registerEvents(staffChatCommand, this);
        getLogger().info("StaffChat plugin started successfully!");
            started = true;
        }
        else{
            getLogger().info("StaffChat plugin already started silly!");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("StaffChat plugin has now stopped! Goodbye!");
    }
}
