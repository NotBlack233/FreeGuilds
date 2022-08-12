package me.not_black.freeguilds.events;

import me.not_black.freeguilds.FreeGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        final File f=new File(FreeGuilds.Inst().getDataFolder(), "players.yml");
        final FileConfiguration fc= YamlConfiguration.loadConfiguration(f);
        if(!fc.isConfigurationSection(event.getPlayer().getUniqueId().toString())) {
            fc.set(event.getPlayer().getUniqueId() + ".guild", "no");
            fc.set(event.getPlayer().getUniqueId() + ".pending", false);
        }
        try {
            fc.save(FreeGuilds.Inst().getPlayersManager().getFile());
        } catch (IOException ignored) {}
    }
}
