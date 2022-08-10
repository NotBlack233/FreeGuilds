package me.not_black.freeguilds.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GuildChatCommand implements TabExecutor {

    private final List<String> emptyList=new ArrayList<String>() {{
        add("");
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        StringBuilder msg = new StringBuilder();
        for(String i:args) {
            msg.append(" ").append(i);
        }
        msg.deleteCharAt(0);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return emptyList;
    }
}
