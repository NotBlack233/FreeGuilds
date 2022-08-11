package me.not_black.freeguilds;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.not_black.freeguilds.objects.Guild;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PapiSupport extends PlaceholderExpansion {

    public PapiSupport() {}

    private static double formatDouble(double d) {
        return (double) Math.round(d*100)/100;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "guild";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Not_Black";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(@NotNull OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("name")) {
            Guild guild=FreeGuilds.Inst().getGuildsManager().getGuild(FreeGuilds.Inst().getPlayersManager().getPlayerGuild(player.getUniqueId()));
            if(guild==null) return null;
            return guild.getName();
        } else if (params.equalsIgnoreCase("prefix")) {
            Guild guild=FreeGuilds.Inst().getGuildsManager().getGuild(FreeGuilds.Inst().getPlayersManager().getPlayerGuild(player.getUniqueId()));
            if(guild==null) return null;
            return guild.getGuildPrefix();
        }
        return null;
    }

}
