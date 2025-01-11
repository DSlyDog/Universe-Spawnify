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

public class SetGroupSpawnCommand implements CommandExecutor {
    private Spawnify plugin;
    private Universes universes;

    public SetGroupSpawnCommand(Spawnify plugin, Universes universes) {
        this.plugin = plugin;
        this.universes = universes;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("Universes.Spawnify.setspawn")) {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command");
            return true;
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may execute that command");
            return true;
        } else {
            Player player = (Player)sender;
            Location spawnpoint = player.getLocation();
            Universe universe = (Universe)this.universes.universes.get(spawnpoint.getWorld().getName());
            this.plugin.groupSpawnsFile.get().set(universe.name() + ".world", spawnpoint.getWorld().getName());
            this.plugin.groupSpawnsFile.get().set(universe.name() + ".x", spawnpoint.getX());
            this.plugin.groupSpawnsFile.get().set(universe.name() + ".y", spawnpoint.getY());
            this.plugin.groupSpawnsFile.get().set(universe.name() + ".z", spawnpoint.getZ());
            this.plugin.groupSpawnsFile.get().set(universe.name() + ".yaw", spawnpoint.getYaw());
            this.plugin.groupSpawnsFile.get().set(universe.name() + ".pitch", spawnpoint.getPitch());
            this.plugin.groupSpawnsFile.save();
            if (!this.plugin.spawns.containsKey(universe.name())) {
                this.plugin.spawns.put(universe.name(), spawnpoint);
            } else {
                this.plugin.spawns.replace(universe.name(), spawnpoint);
            }

            player.sendMessage(ChatColor.GREEN + "Group spawnpoint set");
            return true;
        }
    }
}
