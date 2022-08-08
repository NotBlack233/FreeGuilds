package me.not_black.freeguilds;

import org.bukkit.plugin.java.JavaPlugin;

public final class FreeGuilds extends JavaPlugin {

    private static FreeGuilds instance;



    @Override
    public void onEnable() {
        // Plugin startup logic
        instance=this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FreeGuilds getInstance() {
        return instance;
    }


}
