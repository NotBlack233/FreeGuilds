package me.not_black.freeguilds.managers;

import me.not_black.freeguilds.FreeGuilds;
import me.not_black.freeguilds.objects.Guild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class GuildsManager {

    public GuildsManager() {}

    private final File guildsFolder=new File(FreeGuilds.getInstance().getDataFolder(),"guilds");

    private Map<UUID, Guild> guilds=new HashMap<>();

    public void reload() {
        if(!guildsFolder.exists()) guildsFolder.mkdir();
        for(File file: Objects.requireNonNull(guildsFolder.listFiles())) {
            FileConfiguration fc= YamlConfiguration.loadConfiguration(file);
            String name=fc.getString("name");
            String guildPrefix=fc.getString("guildPrefix");
            UUID guildUUID=UUID.fromString(file.getName().replace(".yml",""));
            UUID guildMaster=UUID.fromString(fc.getString("guildMaster"));
            List<String> description=fc.getStringList("description");
            List<UUID> guildMembers=new ArrayList<>();
            List<String> guildMembersString=fc.getStringList("guildMembers");
            for(String stringUUID:guildMembersString) {
                guildMembers.add(UUID.fromString(stringUUID));
            }
            long creationTime=fc.getLong("creationTime");
            addGuild(new Guild(name,guildUUID,guildMaster,guildMembers,description,creationTime,guildPrefix));
        }
    }

    public void addGuild(Guild guild) {
        if(guilds.containsKey(guild.getGuildUUID())) replaceGuild(guild);
        else {
            guilds.put(guild.getGuildUUID(), guild);
            saveGuildFile(guild);
        }
    }

    public void removeGuild(Guild guild) {
        guilds.remove(guild.getGuildUUID());
        removeGuildFile(guild.getGuildUUID());
    }

    public void removeGuild(UUID guild) {
        guilds.remove(guild);
        removeGuildFile(guild);
    }

    @Nullable
    public Guild getGuild(UUID guild) {
        return guilds.get(guild);
    }

    public void replaceGuild(Guild guild) {
        guilds.replace(guild.getGuildUUID(),guild);
        saveGuildFile(guild);
    }

    public boolean hasGuild(UUID guild) {
        return guilds.containsKey(guild);
    }

    public boolean hasGuild(Guild guild) {
        return guilds.containsValue(guild);
    }

    private void saveGuildFile(Guild guild) {
        File guildFile=new File(FreeGuilds.getInstance().getDataFolder().getName()+"//guilds", guild.getGuildUUID().toString()+".yml");
        FileConfiguration fc=YamlConfiguration.loadConfiguration(guildFile);
        fc.set("name",guild.getName());
        fc.set("guildPrefix",guild.getGuildPrefix());
        fc.set("guildMaster",guild.getGuildMaster().toString());
        fc.set("description",guild.getDescription());
        List<String> guildMembersString=new ArrayList<>();
        for(UUID member:guild.getGuildMembers()) {
            guildMembersString.add(member.toString());
        }
        fc.set("guildMembers",guildMembersString);
        try {
            fc.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeGuildFile(@NotNull String uuid) {
        File guildFile=new File(FreeGuilds.getInstance().getDataFolder().getName()+"//guilds",uuid+".yml");
        if(guildFile.exists()) guildFile.delete();
        else return false;
        return true;
    }

    public boolean removeGuildFile(@NotNull UUID uuid) {
        return removeGuildFile(uuid.toString());
    }
}
