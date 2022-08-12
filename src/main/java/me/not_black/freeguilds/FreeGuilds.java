package me.not_black.freeguilds;

import me.not_black.freeguilds.commands.AdminCommand;
import me.not_black.freeguilds.commands.MainCommand;
import me.not_black.freeguilds.managers.ConfigsManager;
import me.not_black.freeguilds.managers.GuildsManager;
import me.not_black.freeguilds.managers.MessagesManager;
import me.not_black.freeguilds.managers.PlayersManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class FreeGuilds extends JavaPlugin {

    private static FreeGuilds instance;

    private GuildsManager guildsManager;
    private ConfigsManager configsManager;
    private MessagesManager messagesManager;
    private PlayersManager playersManager;
    private Economy economy;
//    private Metrics metrics;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance=this;
        guildsManager=new GuildsManager();
        configsManager=new ConfigsManager();
        messagesManager=new MessagesManager();
        playersManager=new PlayersManager();
        Objects.requireNonNull(getCommand("guild")).setExecutor(new MainCommand());
        Objects.requireNonNull(getCommand("guild")).setTabCompleter(new MainCommand());
        Objects.requireNonNull(getCommand("guildadmin")).setExecutor(new AdminCommand());
        Objects.requireNonNull(getCommand("guildadmin")).setTabCompleter(new AdminCommand());
        new Metrics(this,16094);
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) new PapiSupport().register();
        saveDefaultConfig();
        saveResource("messages.yml",false);
        saveResource("help.yml",false);
        reload();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @NotNull
    public static FreeGuilds Inst() {
        return instance;
    }
    @NotNull
    public GuildsManager getGuildsManager() {
        return guildsManager;
    }
    @NotNull
    public ConfigsManager getConfigsManager() {
        return configsManager;
    }
    @NotNull
    public PlayersManager getPlayersManager() {
        return playersManager;
    }
    @NotNull
    public MessagesManager getMessagesManager() {
        return messagesManager;
    }
    @NotNull
    public Economy getEconomy() {
        return economy;
    }

    public void reload() {
        reloadConfig();
        guildsManager.reload();
        configsManager.reload();
        messagesManager.reload();
        playersManager.reload();
        setupEconomy();
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
}
