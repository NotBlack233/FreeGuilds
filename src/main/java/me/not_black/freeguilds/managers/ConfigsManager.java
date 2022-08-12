package me.not_black.freeguilds.managers;

import me.not_black.freeguilds.FreeGuilds;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public final class ConfigsManager {

    public ConfigsManager() {}

    private String configVersion;
    private double createGuildCost;

    public void reload() {
        FileConfiguration config= FreeGuilds.Inst().getConfig();
        configVersion=config.getString("configVersion");
        if(configVersion==null) throw new NullPointerException("Null configVersion");
        createGuildCost=config.getDouble("createGuildCost");
    }

    public double getCreateGuildCost() {
        return createGuildCost;
    }
}
