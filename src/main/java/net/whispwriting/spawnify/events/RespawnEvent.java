package net.whispwriting.spawnify.events;

import net.whispwriting.spawnify.Spawnify;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.utils.Universe;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {
    private Universes universes;
    private Spawnify plugin;

    public RespawnEvent(Spawnify plugin, Universes universes) {
        this.universes = universes;
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (Universes.plugin.useBedRespawn && event.getPlayer().getBedSpawnLocation() != null) {
            if (event.getPlayer().getBedSpawnLocation().getWorld().getEnvironment() != Environment.NETHER) {
                event.setRespawnLocation(event.getPlayer().getBedSpawnLocation());
                return;
            }

            Block block = this.searchForRespawnAnchor(event.getPlayer().getBedSpawnLocation());
            if (block != null) {
                RespawnAnchor anchor = (RespawnAnchor)block.getBlockData();
                if (anchor.getCharges() > 0) {
                    anchor.setCharges(anchor.getCharges() - 1);
                    event.setRespawnLocation(event.getPlayer().getBedSpawnLocation());
                    block.setBlockData(anchor);
                    return;
                }
            }
        }

        if (Universes.plugin.toGroupOnRespawn && Universes.plugin.universes.containsKey(event.getPlayer().getLocation().getWorld().getName())) {
            Universe universe = (Universe)Universes.plugin.universes.get(event.getPlayer().getLocation().getWorld().getName());
            if (this.plugin.spawns.containsKey(this.universes.groups.get(universe.serverWorld().getName()))) {
                event.setRespawnLocation((Location)this.plugin.spawns.get(this.universes.groups.get(universe.serverWorld().getName())));
                return;
            }
        }

        if (Universes.plugin.toHubOnRespawn && this.plugin.spawns.containsKey("hub")) {
            event.setRespawnLocation((Location)this.plugin.spawns.get("hub"));
        }

    }

    public Block searchForRespawnAnchor(Location respawnLocation) {
        System.out.println(respawnLocation);
        Location xPos = respawnLocation.clone();
        Location xNeg = respawnLocation.clone();
        Location zPos = respawnLocation.clone();
        Location zNeg = respawnLocation.clone();
        xPos.setX(respawnLocation.getX() + 1.0);
        xNeg.setX(respawnLocation.getX() - 1.0);
        zPos.setZ(respawnLocation.getZ() + 1.0);
        zNeg.setZ(respawnLocation.getZ() - 1.0);
        if (xPos.getBlock().getType() == Material.RESPAWN_ANCHOR) {
            return xPos.getBlock();
        } else if (xNeg.getBlock().getType() == Material.RESPAWN_ANCHOR) {
            return xNeg.getBlock();
        } else if (zPos.getBlock().getType() == Material.RESPAWN_ANCHOR) {
            return zPos.getBlock();
        } else {
            return zNeg.getBlock().getType() == Material.RESPAWN_ANCHOR ? zNeg.getBlock() : null;
        }
    }
}
