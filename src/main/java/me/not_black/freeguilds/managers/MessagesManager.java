package me.not_black.freeguilds.managers;

import me.not_black.freeguilds.FreeGuilds;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MessagesManager {

    private final Map<String,String> msg = new HashMap<>();

    public MessagesManager() {}
    private FileConfiguration getMsgConfig() {
        return YamlConfiguration.loadConfiguration(new File(FreeGuilds.getInstance().getDataFolder(),"messages.yml"));
    }
    public void reload() {
        msg.clear();
        FileConfiguration fc=getMsgConfig();
        for (String s : fc.getKeys(false)) {
            msg.put(s,ChatColor.translateAlternateColorCodes('&',fc.getString(s)));
        }
    }
    @Nullable
    public String getMsg(String name) {
        return msg.get(name);
    }
}
