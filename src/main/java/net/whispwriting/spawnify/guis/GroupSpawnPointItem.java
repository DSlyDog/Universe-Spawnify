package net.whispwriting.spawnify.guis;

import net.whispwriting.spawnify.Spawnify;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.gui.GUIItem;
import net.whispwriting.universes.gui.Utils;
import net.whispwriting.universes.utils.Universe;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GroupSpawnPointItem extends GUIItem {

    private Spawnify plugin;

    public GroupSpawnPointItem(Spawnify plugin){
        this.plugin = plugin;
        Material[] materials = {Material.RESPAWN_ANCHOR};
        List<String> lore1 = new ArrayList<>();
        lore1.add(Utils.chat("&dClick to set the group's spawn point."));

        super.create(Utils.chat("&bSpawnify Group Spawn Point"), 1, false, 0, materials, lore1);
    }

    @Override
    public void onClick(Player player, Universe universe){
        Location spawnpoint = player.getLocation();
        plugin.hubSpawnFile.get().set(universe.name() + ".world", spawnpoint.getWorld().getName());
        plugin.hubSpawnFile.get().set(universe.name() + ".x", spawnpoint.getX());
        plugin.hubSpawnFile.get().set(universe.name() + ".y", spawnpoint.getY());
        plugin.hubSpawnFile.get().set(universe.name() + ".z", spawnpoint.getZ());
        plugin.hubSpawnFile.get().set(universe.name() + ".yaw", spawnpoint.getYaw());
        plugin.hubSpawnFile.get().set(universe.name() + ".pitch", spawnpoint.getPitch());
        plugin.hubSpawnFile.save();
        if (!plugin.spawns.containsKey(universe.name())) {
            plugin.spawns.put(universe.name(), spawnpoint);
        } else {
            plugin.spawns.replace(universe.name(), spawnpoint);
        }

        player.sendMessage(ChatColor.GREEN + "Group spawnpoint set");
    }
}
