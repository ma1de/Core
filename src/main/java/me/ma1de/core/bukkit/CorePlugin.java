package me.ma1de.core.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin {
    private static CorePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static CorePlugin getInstance() {
        return instance;
    }
}
