package me.not_black.freeguilds.objects;

import java.util.List;
import java.util.UUID;

public class Guild {

    private String name;
    private UUID guildUUID;
    private UUID guildMaster;
    private List<UUID> guildMembers;

    public Guild(String name,UUID guildMaster,List<UUID> guildMembers) {
        this.name=name;
        this.guildMaster=guildMaster;
        this.guildMembers=guildMembers;
    }

    public Guild(String name,UUID guildUUID,UUID guildMaster,List<UUID> guildMembers) {
        this.name=name;
        this.guildUUID=guildUUID;
        this.guildMaster=guildMaster;
        this.guildMembers=guildMembers;
    }

    public String getName() {
        return name;
    }

    public List<UUID> getGuildMembers() {
        return guildMembers;
    }

    public UUID getGuildMaster() {
        return guildMaster;
    }

    public UUID getGuildUUID() {
        return guildUUID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGuildMaster(UUID guildMaster) {
        this.guildMaster = guildMaster;
    }

    public void setGuildMembers(List<UUID> guildMembers) {
        this.guildMembers = guildMembers;
    }

    public void addGuildMember(UUID member) {
        guildMembers.add(member);
    }

    public void removeGuildMember(UUID member) {
        guildMembers.remove(member);
    }


}