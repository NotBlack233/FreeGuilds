package me.not_black.freeguilds.managers;

import me.not_black.freeguilds.FreeGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayersManager {

    private Map<UUID,UUID> playerGuildMap=new HashMap<>();
    private Map<UUID,Boolean> playerPendingStatus=new HashMap<>();
    private final File f=new File(FreeGuilds.Inst().getDataFolder(), "players.yml");
    private final FileConfiguration fc=YamlConfiguration.loadConfiguration(f);

    public PlayersManager() {}

    public void reload() {
        playerGuildMap.clear();
        playerPendingStatus.clear();
        for(String i:fc.getKeys(false)) {
            try {
                playerGuildMap.put(UUID.fromString(i),UUID.fromString(fc.getString(i+".guild")));
            } catch (Exception ignored) {}
            playerPendingStatus.put(UUID.fromString(i),fc.getBoolean(i+".pending"));
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

    @Nullable
    public UUID getPlayerGuild(@NotNull final UUID player) {
        return playerGuildMap.get(player);
    }

    public void setPlayerPendingStatus(UUID player, boolean status) {
        if(playerPendingStatus.containsKey(player)) playerPendingStatus.replace(player,status);
        else playerPendingStatus.put(player,status);
        fc.set(player.toString()+".pending",status);
        try {
            fc.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getPlayerPendingStatus(UUID player) {
        return playerPendingStatus.getOrDefault(player,false);
    }

    public File getFile() {
        return f;
    }

    public FileConfiguration getFileConfiguration() {
        return fc;
    }
}
