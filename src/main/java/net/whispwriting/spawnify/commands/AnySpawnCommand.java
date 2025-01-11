 package net.whispwriting.spawnify.commands;

import net.whispwriting.spawnify.Spawnify;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.utils.Universe;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AnySpawnCommand implements CommandExecutor {
    private Spawnify plugin;
    private Universes universes;

    public AnySpawnCommand(Spawnify plugin, Universes universes) {
        this.plugin = plugin;
        this.universes = universes;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("Universes.Spawnify.spawn")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command");
            return true;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may execute that command");
            return true;
        } else if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Please provide a group name");
            return true;
        } else {
            Player player = (Player)sender;
            if (Universes.plugin.universes.containsKey(args[0])) {
                Universe universe = (Universe)this.universes.universes.get(args[0]);
                if (this.plugin.spawns.containsKey(universe.name())) {
                    player.teleport((Location)this.plugin.spawns.get(universe.name()));
                    player.sendMessage(ChatColor.YELLOW + "You have been teleported to the group spawn");
                    return true;
                }
            }

            player.sendMessage(ChatColor.RED + "Could not find a spawn location for group " + ChatColor.DARK_RED + args[0]);
            return true;
        }
    }
}
