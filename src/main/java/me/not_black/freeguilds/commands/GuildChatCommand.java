package me.not_black.freeguilds.commands;

import me.not_black.freeguilds.FreeGuilds;
import me.not_black.freeguilds.managers.MessagesManager;
import me.not_black.freeguilds.objects.Guild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuildChatCommand implements TabExecutor {

    private final List<String> emptyList=new ArrayList<String>() {{
        add("");
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
        else {
            Guild guild=FreeGuilds.getInstance().getGuildsManager().getGuild(
                    FreeGuilds.getInstance().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId()));
            if(guild==null) sender.sendMessage(FreeGuilds.getInstance().getMessagesManager().getMsg("notInGuild"));
            else if(args.length==0) {
                MessagesManager.wrongUsage(sender);
            } else {
                StringBuilder msg = new StringBuilder();
                for (String i : args) {
                    msg.append(" ").append(i);
                }
                msg.deleteCharAt(0);
                guild.getGuildChat().sendMessage(msg.toString());
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return emptyList;
    }
}
