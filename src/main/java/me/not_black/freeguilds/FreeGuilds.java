package me.not_black.freeguilds;

import me.not_black.freeguilds.managers.ConfigsManager;
import me.not_black.freeguilds.managers.GuildsManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FreeGuilds extends JavaPlugin {

    private static FreeGuilds instance;

    private GuildsManager guildsManager;
    private ConfigsManager configsManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance=this;
        guildsManager=new GuildsManager();
        configsManager=new ConfigsManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FreeGuilds getInstance() {
        return instance;
    }


}
