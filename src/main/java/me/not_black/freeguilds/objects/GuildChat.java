package me.not_black.freeguilds.objects;

import me.not_black.freeguilds.FreeGuilds;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class GuildChat {

    private final Guild guild;

    public GuildChat(Guild guild) {
        this.guild=guild;
    }

    public void sendMessage(String msg) {
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        for(UUID member: guild.getGuildMembers()) {
            Player p=Bukkit.getPlayer(member);
            if(p!=null) p.sendMessage(Objects.requireNonNull(FreeGuilds.Inst().getMessagesManager().getMsg("guildChatFormat"))
                    .replace("{Player}",p.getDisplayName())
                    .replace("Message",msg));
        }
    }

}
