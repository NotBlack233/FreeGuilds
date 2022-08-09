package me.not_black.freeguilds.objects;

import me.not_black.freeguilds.FreeGuilds;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guild {

    private final File guildFile;
    private final FileConfiguration guildFC;
    private String name;
    private String guildPrefix;
    private final UUID guildUUID;
    private UUID guildMaster;
    private List<UUID> guildMembers;
    private List<String> description;
    private final long creationTime;

    public Guild(@NotNull String name,@NotNull UUID guildMaster,@NotNull List<UUID> guildMembers,@NotNull List<String> description, long creationTime,@NotNull String guildPrefix) {
        this.name = name;
        this.guildUUID = UUID.randomUUID();
        this.guildMaster = guildMaster;
        this.guildMembers = guildMembers;
        this.description = description;
        this.creationTime = creationTime;
        this.guildPrefix = guildPrefix;
        this.guildFile = new File(FreeGuilds.getInstance().getDataFolder() + "//guilds", this.guildUUID + ".yml");
        this.guildFC = YamlConfiguration.loadConfiguration(this.guildFile);
    }

    public Guild(@NotNull String name,@NotNull UUID guildUUID,@NotNull UUID guildMaster,@NotNull List<UUID> guildMembers,@NotNull List<String> description,long creationTime,@NotNull String guildPrefix) {
        this.name = name;
        this.guildUUID = guildUUID;
        this.guildMaster = guildMaster;
        this.guildMembers = guildMembers;
        this.description = description;
        this.creationTime = creationTime;
        this.guildPrefix = guildPrefix;
        this.guildFile = new File(FreeGuilds.getInstance().getDataFolder() + "//guilds", this.guildUUID + ".yml");
        this.guildFC = YamlConfiguration.loadConfiguration(this.guildFile);
    }

    public void register() {
        FreeGuilds.getInstance().getGuildsManager().addGuild(this);
    }

    public void remove() {
        FreeGuilds.getInstance().getGuildsManager().removeGuild(this);
    }

    @NotNull
    public String getName() {
        return name;
    }
    @NotNull
    public List<UUID> getGuildMembers() {
        return guildMembers;
    }
    @NotNull
    public UUID getGuildMaster() {
        return guildMaster;
    }
    @NotNull
    public UUID getGuildUUID() {
        return guildUUID;
    }
    @NotNull
    public String getGuildPrefix() {
        return guildPrefix;
    }
    @NotNull
    public List<String> getDescription() {
        return description;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setName(@NotNull String name) {
        this.name = name;
        guildFC.set("name",name);
        try {
            guildFC.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGuildMaster(@NotNull UUID guildMaster) {
        this.guildMaster = guildMaster;
        guildFC.set("guildMaster",this.guildMaster.toString());
        try {
            guildFC.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGuildMembers(@NotNull List<UUID> guildMembers) {
        this.guildMembers = guildMembers;
        List<String> guildMembersString=new ArrayList<>();
        for(UUID uuid:this.guildMembers) {
            guildMembersString.add(uuid.toString());
        }
        guildFC.set("guildMembers",guildMembersString);
        try {
            guildFC.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGuildPrefix(@NotNull String guildPrefix) {
        this.guildPrefix = guildPrefix;
        guildFC.set("guildPrefix",this.guildPrefix);
        try {
            guildFC.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDescription(@NotNull List<String> description) {
        this.description = description;
        guildFC.set("description",this.description);
        try {
            guildFC.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGuildMember(@NotNull UUID member) {
        guildMembers.add(member);
        List<String> guildMembersString=new ArrayList<>();
        for(UUID uuid:this.guildMembers) {
            guildMembersString.add(uuid.toString());
        }
        guildFC.set("guildMembers",guildMembersString);
        try {
            guildFC.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeGuildMember(@NotNull UUID member) {
        guildMembers.remove(member);
        List<String> guildMembersString=new ArrayList<>();
        for(UUID uuid:this.guildMembers) {
            guildMembersString.add(uuid.toString());
        }
        guildFC.set("guildMembers",guildMembersString);
        try {
            guildFC.save(guildFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}