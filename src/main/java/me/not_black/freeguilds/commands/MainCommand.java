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

public class MainCommand implements TabExecutor {

    private final List<String> mainTabList=new ArrayList<String>() {{
        add("chat");
        add("join");
        add("leave");
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0) FreeGuilds.getInstance().getMessagesManager().sendHelp(sender);
        else if(!sender.hasPermission("guild.use")) {
            MessagesManager.noPermission(sender);
        } else switch (args[0]) {
                case "chat":
                case "c": {
                    if(args.length==1) MessagesManager.wrongUsage(sender);
                    else {
                        args[0] = "";
                        new GuildChatCommand().onCommand(sender, command, label, args);
                    }
                    break;
                }
                case "join": {
                    if(sender instanceof Player) {
                        if(sender.hasPermission("guild.join")) {
                            if(args.length==2) {
                                if(FreeGuilds.getInstance().getPlayersManager().getPlayerPendingStatus(((Player) sender).getUniqueId())) {
                                    sender.sendMessage(FreeGuilds.getInstance().getMessagesManager().getMsg("addToPendingFailedInPending"));
                                }
                                else if(FreeGuilds.getInstance().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId())!=null) {
                                    sender.sendMessage(FreeGuilds.getInstance().getMessagesManager().getMsg("addToPendingFailedInGuild"));
                                }
                                else {
                                    Guild guild = FreeGuilds.getInstance().getGuildsManager().getGuild(args[1]);
                                    if(guild==null) MessagesManager.wrongUsage(sender);
                                    else {
                                        guild.addGuildMember(((Player) sender).getUniqueId());
                                    }
                                }
                            } else MessagesManager.wrongUsage(sender);
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
        if(args.length==1) return MessagesManager.listStartsWith(mainTabList,args[0]);
        else switch (args[0]) {
            case "chat":
            case "leave":
            case "cancel":
            case "c": {
                return GuildChatCommand.getEmptyList();
            }
            case "join": {
                return MessagesManager.listStartsWith(new ArrayList<>(FreeGuilds.getInstance().getGuildsManager().getGuildsName()),args[1]);
            }
        }
        return null;
    }

}
