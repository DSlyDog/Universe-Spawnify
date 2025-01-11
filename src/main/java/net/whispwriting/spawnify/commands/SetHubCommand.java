package net.whispwriting.spawnify.commands;

import net.whispwriting.spawnify.Spawnify;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHubCommand implements CommandExecutor {
    private Spawnify plugin;

    public SetHubCommand(Spawnify plugin) {
        this.plugin = plugin;
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
            this.plugin.hubSpawnFile.get().set("hub.world", spawnpoint.getWorld().getName());
            this.plugin.hubSpawnFile.get().set("hub.x", spawnpoint.getX());
            this.plugin.hubSpawnFile.get().set("hub.y", spawnpoint.getY());
            this.plugin.hubSpawnFile.get().set("hub.z", spawnpoint.getZ());
            this.plugin.hubSpawnFile.get().set("hub.yaw", spawnpoint.getYaw());
            this.plugin.hubSpawnFile.get().set("hub.pitch", spawnpoint.getPitch());
            this.plugin.hubSpawnFile.save();
            if (!this.plugin.spawns.containsKey("hub")) {
                this.plugin.spawns.put("hub", spawnpoint);
            } else {
                this.plugin.spawns.replace("hub", spawnpoint);
            }

            player.sendMessage(ChatColor.GREEN + "Hub location set");
            return true;
        }
    }
}
