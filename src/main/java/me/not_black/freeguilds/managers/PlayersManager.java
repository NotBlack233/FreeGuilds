package me.not_black.freeguilds.managers;

import me.not_black.freeguilds.FreeGuilds;
import me.not_black.freeguilds.objects.Guild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayersManager {

    private Map<UUID,UUID> playerGuildMap=new HashMap<>();
    private final File f=new File(FreeGuilds.getInstance().getDataFolder(), "players.yml");
    private final FileConfiguration fc=YamlConfiguration.loadConfiguration(f);

    public PlayersManager() {}

    public void reload() {
        playerGuildMap.clear();
        for(String i:fc.getKeys(false)) {
            playerGuildMap.put(UUID.fromString(i),UUID.fromString(fc.getString(i)));
        }
    }

    public void setPlayerGuild(@NotNull final UUID player, @Nullable final UUID guild) {
        if(guild==null) playerGuildMap.remove(player);
        else playerGuildMap.put(player,guild);
        fc.set(player.toString(),guild==null?null:guild.toString());
        try {
            fc.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
