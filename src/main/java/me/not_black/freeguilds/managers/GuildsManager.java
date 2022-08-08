package me.not_black.freeguilds.managers;

import me.not_black.freeguilds.FreeGuilds;
import me.not_black.freeguilds.objects.Guild;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class GuildsManager {

    public GuildsManager() {}

    private final File guildsFolder=new File(FreeGuilds.getInstance().getDataFolder(),"guilds");

    private Map<UUID, Guild> Guilds=new HashMap<>();

    public void reload() {
        if(!guildsFolder.exists()) guildsFolder.mkdir();
        for(File file: Objects.requireNonNull(guildsFolder.listFiles())) {

        }
    }

}
