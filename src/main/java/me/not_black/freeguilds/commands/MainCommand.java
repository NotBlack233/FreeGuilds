package me.not_black.freeguilds.commands;

import me.not_black.freeguilds.FreeGuilds;
import me.not_black.freeguilds.managers.MessagesManager;
import me.not_black.freeguilds.objects.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MainCommand implements TabExecutor {

    private final List<String> mainTabList=new ArrayList<String>() {{
        add("chat");
        add("join");
        add("leave");
        add("create");
        add("disband");
        add("list");
        add("online");
        add("kick");
        add("transfer");
        add("accept");
        add("deny");
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length==0) FreeGuilds.Inst().getMessagesManager().sendHelp(sender);
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
                            if(FreeGuilds.Inst().getPlayersManager().getPlayerPendingStatus(((Player) sender).getUniqueId())) {
                                sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("addToPendingFailedInPending"));
                            }
                            else if(FreeGuilds.Inst().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId())!=null) {
                                sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("addToPendingFailedInGuild"));
                            }
                            else {
                                Guild guild = FreeGuilds.Inst().getGuildsManager().getGuild(args[1]);
                                if(guild==null) MessagesManager.wrongUsage(sender);
                                else {
                                    guild.addGuildPending(((Player) sender).getUniqueId());
                                    sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("addToPendingSuccess"));
                                }
                            }
                        } else MessagesManager.wrongUsage(sender);
                    } else MessagesManager.noPermission(sender);
                } else MessagesManager.noConsole(sender);
                break;
            }
            case "leave": {
                if(sender instanceof Player) {
                    if(sender.hasPermission("guild.leave")) {
                        Guild guild=FreeGuilds.Inst().getGuildsManager().getGuild(FreeGuilds.Inst().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId()));
                        if(guild!=null) {
                            guild.removeGuildMember(((Player) sender).getUniqueId());
                            sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("leaveSuccess"));
                        } else sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("leaveFailed"));
                    } else MessagesManager.noPermission(sender);
                } else MessagesManager.noConsole(sender);
                break;
            }
            case "create": {
                if(sender instanceof Player) {
                    if(sender.hasPermission("guild.create")) {
                        if(args.length==2) {
                            if(!FreeGuilds.Inst().getGuildsManager().hasGuild(args[1])) {
                                if(FreeGuilds.Inst().getEconomy().getBalance((Player) sender)>=FreeGuilds.Inst().getConfigsManager().getCreateGuildCost()){
                                    Guild newGuild=new Guild(args[1],((Player) sender).getUniqueId(),new ArrayList<UUID>(){{
                                        add(((Player)sender).getUniqueId());
                                    }}, new ArrayList<>(),System.currentTimeMillis(),args[1], new ArrayList<>());
                                    newGuild.register();
                                    FreeGuilds.Inst().getEconomy().withdrawPlayer((Player)sender,FreeGuilds.Inst().getConfigsManager().getCreateGuildCost());
                                    sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("createSuccess"));
                                } else FreeGuilds.Inst().getMessagesManager().getMsg("createFailedNoMoney");
                            } else FreeGuilds.Inst().getMessagesManager().getMsg("createFailedAlreadyExist");
                        } else MessagesManager.wrongUsage(sender);
                    } else MessagesManager.noPermission(sender);
                } else MessagesManager.noConsole(sender);
            }
            case "disband": {
                Guild guild;
                if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
                else if(args.length!=1) MessagesManager.wrongUsage(sender);
                else {
                    guild=FreeGuilds.Inst().getGuildsManager().getGuild(
                            FreeGuilds.Inst().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId()));
                    if(guild==null) sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("notInGuild"));
                    else if(!(sender.hasPermission("guild.disband"))) MessagesManager.noPermission(sender);
                    else if(!guild.getGuildMaster().equals(((Player) sender).getUniqueId())) MessagesManager.noPermission(sender);
                    else {
                        guild.remove();
                    }
                }
            }
            case "list": {
                if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
                else if(args.length!=1) MessagesManager.wrongUsage(sender);
                else if(!sender.hasPermission("guild.list")) MessagesManager.noPermission(sender);
                else {
                    Guild guild;
                    guild=FreeGuilds.Inst().getGuildsManager().getGuild(FreeGuilds.Inst().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId()));
                    if(guild==null) sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("notInGuild"));
                    else {
                        for(UUID i: guild.getGuildMembers()) {
                            OfflinePlayer p= Bukkit.getOfflinePlayer(i);
                            if(p.isOnline()) sender.sendMessage(ChatColor.GREEN+"※ "+ Objects.requireNonNull(p.getPlayer()).getDisplayName()+" ");
                            else sender.sendMessage(ChatColor.RED+"※ "+p.getName()+" ");
                        }
                    }
                }
            }
            case "online": {
                if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
                else if(args.length!=1) MessagesManager.wrongUsage(sender);
                else if(!sender.hasPermission("guild.list")) MessagesManager.noPermission(sender);
                else {
                    Guild guild;
                    guild=FreeGuilds.Inst().getGuildsManager().getGuild(FreeGuilds.Inst().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId()));
                    if(guild==null) sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("notInGuild"));
                    else {
                        for(UUID i: guild.getGuildMembers()) {
                            OfflinePlayer p= Bukkit.getOfflinePlayer(i);
                            if(p.isOnline()) sender.sendMessage(ChatColor.GREEN+"※ "+ Objects.requireNonNull(p.getPlayer()).getDisplayName()+" ");
                        }
                    }
                }
            }
            case "kick": {
                if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
                else if(args.length!=2) MessagesManager.wrongUsage(sender);
                else if(!sender.hasPermission("guild.kick")) MessagesManager.noPermission(sender);
            }
            case "transfer": {
                if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
                else if(args.length!=2) MessagesManager.wrongUsage(sender);
                else if(!sender.hasPermission("guild.transfer")) MessagesManager.noPermission(sender);
            }
            case "accept": {
                if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
                else if(args.length!=2) MessagesManager.wrongUsage(sender);
                else if(!sender.hasPermission("guild.accept")) MessagesManager.noPermission(sender);
            }
            case "deny": {
                if(!(sender instanceof Player)) MessagesManager.noConsole(sender);
                else if(args.length!=2) MessagesManager.wrongUsage(sender);
                else if(!sender.hasPermission("guild.deny")) MessagesManager.noPermission(sender);
            }
            default: FreeGuilds.Inst().getMessagesManager().sendHelp(sender);
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
            case "online":
            case "list":
            case "disband":
            case "create":
            case "c": {
                return GuildChatCommand.getEmptyList();
            }
            case "join": {
                return MessagesManager.listStartsWith(new ArrayList<>(FreeGuilds.Inst().getGuildsManager().getGuildsName()),args[1]);
            }
            case "accept":
            case "deny": {
                if(sender instanceof Player) {
                    Guild guild=FreeGuilds.Inst().getGuildsManager().getGuild(
                            FreeGuilds.Inst().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId())
                    );
                    if(guild==null) return null;
                    List<String> tmp=new ArrayList<>();
                    for(UUID i: guild.getGuildPending()) tmp.add(Bukkit.getOfflinePlayer(i).getName());
                    return MessagesManager.listStartsWith(tmp,args[1]);
                }
                else return null;
            }
            case "kick":
            case "transfer": {
                if(sender instanceof Player) {
                    Guild guild=FreeGuilds.Inst().getGuildsManager().getGuild(
                            FreeGuilds.Inst().getPlayersManager().getPlayerGuild(((Player) sender).getUniqueId())
                    );
                    if(guild==null) return null;
                    List<String> tmp=new ArrayList<>();
                    for(UUID i: guild.getGuildMembers()) tmp.add(Bukkit.getOfflinePlayer(i).getName());
                    return MessagesManager.listStartsWith(tmp,args[1]);
                }
                else return null;
            }
        }
        return null;
    }

}
