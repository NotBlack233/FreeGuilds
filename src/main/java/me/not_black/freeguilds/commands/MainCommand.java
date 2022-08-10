package me.not_black.freeguilds.commands;

import me.not_black.freeguilds.FreeGuilds;
import me.not_black.freeguilds.managers.MessagesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0) FreeGuilds.getInstance().getMessagesManager().sendHelp(sender);
        else if(!sender.hasPermission("guild.use")) {
            MessagesManager.noPermission(sender);
        } else switch (args[0]) {
                case "chat":
                case "c": {
                    args[0]="";
                    new GuildChatCommand().onCommand(sender,command,label,args);
                    break;
                }
                case "join": {
                    if(sender instanceof Player) {
                        if(sender.hasPermission("guild.join")) {

                        } else MessagesManager.noPermission(sender);
                    } else MessagesManager.noConsole(sender);
                    break;
                }
                case "leave": {
                    break;
                }
                default: FreeGuilds.getInstance().getMessagesManager().sendHelp(sender);
        }
        return true;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }

}
