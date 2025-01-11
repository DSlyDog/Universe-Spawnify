//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.whispwriting.spawnify.commands;

import net.whispwriting.spawnify.Spawnify;
import net.whispwriting.spawnify.guis.GroupSpawnPointItem;
import net.whispwriting.spawnify.guis.SpawnifyHubItem;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.commands.ModifyCommand;
import net.whispwriting.universes.events.ModifyInventoryClick;
import net.whispwriting.universes.gui.guis.WorldSettingsUI;
import net.whispwriting.universes.utils.Universe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SModifyCommand extends ModifyCommand {
    private Universes universes;
    private Spawnify plugin;

    public SModifyCommand(Spawnify plugin, Universes universes) {
        super(universes);
        this.universes = universes;
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("Universes.modify")) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                Universe universe = universes.universes.get(player.getWorld().getName());
                this.gui(player, universe);
            } else {
                sender.sendMessage(ChatColor.RED + "Only players may execute that command");
            }

            return true;
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to that command.");
            return true;
        }
    }

    public void gui(Player player, Universe universe) {
        WorldSettingsUI ui = WorldSettingsUI.getFor(universe);
        ui.build();
        ui.insertItem(new GroupSpawnPointItem(plugin));
        ui.insertItem(new SpawnifyHubItem(plugin));
        player.openInventory(ui.get());
        Bukkit.getPluginManager().registerEvents(new ModifyInventoryClick(universe, player.getUniqueId().toString(), this.universes), this.plugin);
    }
}
