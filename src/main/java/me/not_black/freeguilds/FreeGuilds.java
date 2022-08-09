package me.not_black.freeguilds;

import me.not_black.freeguilds.commands.AdminCommand;
import me.not_black.freeguilds.commands.MainCommand;
import me.not_black.freeguilds.managers.ConfigsManager;
import me.not_black.freeguilds.managers.GuildsManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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
        Objects.requireNonNull(getCommand("guild")).setExecutor(new MainCommand());
        Objects.requireNonNull(getCommand("guild")).setTabCompleter(new MainCommand());
        Objects.requireNonNull(getCommand("guildadmin")).setExecutor(new AdminCommand());
        Objects.requireNonNull(getCommand("guildadmin")).setTabCompleter(new AdminCommand());

        reload();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FreeGuilds getInstance() {
        return instance;
    }

    public GuildsManager getGuildsManager() {
        return guildsManager;
    }

    public ConfigsManager getConfigsManager() {
        return configsManager;
    }

    public void reload() {
        guildsManager.reload();
        configsManager.reload();
    }
}
