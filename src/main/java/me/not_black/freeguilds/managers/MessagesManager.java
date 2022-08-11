package me.not_black.freeguilds.managers;

import me.not_black.freeguilds.FreeGuilds;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public final class MessagesManager {

    private final Map<String,String> msg = new HashMap<>();
    private List<String> helpList = new ArrayList<>();

    public MessagesManager() {}
    private FileConfiguration getMsgConfig() {
        return YamlConfiguration.loadConfiguration(new File(FreeGuilds.Inst().getDataFolder(),"messages.yml"));
    }
    public void reload() {
        msg.clear();
        FileConfiguration fc=getMsgConfig();
        for (String s : fc.getKeys(false)) {
            msg.put(s,ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(fc.getString(s))));
        }
        helpList=YamlConfiguration.loadConfiguration(new File(FreeGuilds.Inst().getDataFolder(), "help.yml")).getStringList("help");
    }
    @NotNull
    public String getMsg(String name) {
        return msg.getOrDefault(name, "null");
    }

    public void sendHelp(CommandSender sender) {
        for (String i:helpList) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',i));
        }
    }

    public static void noConsole(@NotNull CommandSender sender) {
        sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("noConsole"));
    }

    public static void noPermission(@NotNull CommandSender sender) {
        sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("noPermission"));
    }

    public static void wrongUsage(@NotNull CommandSender sender) {
        sender.sendMessage(FreeGuilds.Inst().getMessagesManager().getMsg("wrongUsage"));
    }

    public static List<String> listStartsWith(@NotNull List<String> list, @NotNull String prefix) {
        List<String> tmp=new ArrayList<>();
        for(String i:list) if(i.startsWith(prefix)) tmp.add(i);
        return tmp;
    }
}
