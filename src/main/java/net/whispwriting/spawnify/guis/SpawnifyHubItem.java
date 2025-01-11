package net.whispwriting.spawnify.guis;

import net.whispwriting.spawnify.Spawnify;
import net.whispwriting.universes.gui.GUIItem;
import net.whispwriting.universes.gui.Utils;
import net.whispwriting.universes.utils.Universe;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnifyHubItem extends GUIItem {

    private Spawnify plugin;

    public SpawnifyHubItem(Spawnify plugin){
        this.plugin = plugin;
        Material[] materials = {Material.CLOCK};
        List<String> lore1 = new ArrayList<>();
        lore1.add(Utils.chat("&dClick to set the hub location."));

        super.create(Utils.chat("&bSpawnify Hub"), 1, false, 0, materials, lore1);
    }

    @Override
    public void onClick(Player player, Universe universe){
        Location spawnpoint = player.getLocation();
        plugin.hubSpawnFile.get().set("hub.world", spawnpoint.getWorld().getName());
        plugin.hubSpawnFile.get().set("hub.x", spawnpoint.getX());
        plugin.hubSpawnFile.get().set("hub.y", spawnpoint.getY());
        plugin.hubSpawnFile.get().set("hub.z", spawnpoint.getZ());
        plugin.hubSpawnFile.get().set("hub.yaw", spawnpoint.getYaw());
        plugin.hubSpawnFile.get().set("hub.pitch", spawnpoint.getPitch());
        plugin.hubSpawnFile.save();
        if (!plugin.spawns.containsKey("hub")) {
            plugin.spawns.put("hub", spawnpoint);
        } else {
            plugin.spawns.replace("hub", spawnpoint);
        }

        player.sendMessage(ChatColor.GREEN + "Hub location set");
    }
}
