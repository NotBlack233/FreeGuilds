package me.not_black.freeguilds.objects;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class Guild {

    private String name;
    private String guildPrefix;
    private final UUID guildUUID;
    private UUID guildMaster;
    private List<UUID> guildMembers;
    private List<String> description;
    private final long creationTime;

    public Guild(@NotNull String name,@NotNull UUID guildMaster,@NotNull List<UUID> guildMembers,@NotNull List<String> description, long creationTime,@NotNull String guildPrefix) {
        this.name=name;
        this.guildUUID=UUID.randomUUID();
        this.guildMaster=guildMaster;
        this.guildMembers=guildMembers;
        this.description=description;
        this.creationTime=creationTime;
        this.guildPrefix=guildPrefix;
    }

    public Guild(@NotNull String name,@NotNull UUID guildUUID,@NotNull UUID guildMaster,@NotNull List<UUID> guildMembers,@NotNull List<String> description,long creationTime,@NotNull String guildPrefix) {
        this.name=name;
        this.guildUUID=guildUUID;
        this.guildMaster=guildMaster;
        this.guildMembers=guildMembers;
        this.description=description;
        this.creationTime=creationTime;
        this.guildPrefix=guildPrefix;
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
    }

    public void setGuildMaster(@NotNull UUID guildMaster) {
        this.guildMaster = guildMaster;
    }

    public void setGuildMembers(@NotNull List<UUID> guildMembers) {
        this.guildMembers = guildMembers;
    }

    public void setGuildPrefix(@NotNull String guildPrefix) {
        this.guildPrefix = guildPrefix;
    }

    public void setDescription(@NotNull List<String> description) {
        this.description = description;
    }

    public void addGuildMember(@NotNull UUID member) {
        guildMembers.add(member);
    }

    public void removeGuildMember(@NotNull UUID member) {
        guildMembers.remove(member);
    }


}