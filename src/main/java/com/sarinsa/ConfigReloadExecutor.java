package com.sarinsa;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigReloadExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission(command.getPermission())) {
            if (args.length > 0) {
                return false;
            }
            try {
                DataFetcher.instance.reloadConfig();
                commandSender.sendMessage(ChatColor.GREEN + "" + ChatColor.ITALIC + "Reloaded DataFetcher config!");
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        //commandSender.sendMessage(ChatColor.RED + "You are not permitted to perform this command.");
        return false;
    }
}
