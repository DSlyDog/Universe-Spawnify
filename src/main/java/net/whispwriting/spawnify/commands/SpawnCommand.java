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

public class SpawnCommand implements CommandExecutor {
    private Spawnify plugin;
    private Universes universes;

    public SpawnCommand(Spawnify plugin, Universes universes) {
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
        } else {
            Player player = (Player)sender;
            Location currentLocation = player.getLocation();
            if (Universes.plugin.universes.containsKey(currentLocation.getWorld().getName())) {
                Universe universe = (Universe)Universes.plugin.universes.get(currentLocation.getWorld().getName());
                if (this.plugin.spawns.containsKey(universe.name())) {
                    player.teleport((Location)this.plugin.spawns.get(universe.name()));
                    player.sendMessage(ChatColor.YELLOW + "You have been teleported to the group spawn");
                    return true;
                }
            }

            player.sendMessage(ChatColor.RED + "Could not find a spawn location for the world you are in");
            return true;
        }
    }
}
