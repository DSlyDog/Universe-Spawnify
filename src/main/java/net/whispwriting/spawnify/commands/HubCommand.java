package net.whispwriting.spawnify.commands;

import net.whispwriting.spawnify.Spawnify;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {
    private Spawnify plugin;

    public HubCommand(Spawnify plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("Universes.Spawnify.spawn")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command");
            return true;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may execute that command");
            return true;
        } else {
            Player player = (Player)sender;
            if (this.plugin.spawns.containsKey("hub")) {
                player.teleport((Location)this.plugin.spawns.get("hub"));
                player.sendMessage(ChatColor.YELLOW + "You have been teleported to the hub");
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "The hub has not been set");
                return true;
            }
        }
    }
}
