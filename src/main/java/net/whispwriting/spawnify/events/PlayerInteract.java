package net.whispwriting.spawnify.events;

import java.util.ArrayList;
import java.util.List;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.utils.Universe;
import net.whispwriting.universes.utils.UniversePlayer;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {
    private List<Material> respawnables = new ArrayList();

    public PlayerInteract() {
        this.respawnables.add(Material.BLACK_BED);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.RESPAWN_ANCHOR) {
                RespawnAnchor anchor = (RespawnAnchor)block.getBlockData();
                if (anchor.getCharges() > 0) {
                    this.saveBedRespawnLocation(event.getPlayer().getWorld(), event.getPlayer());
                }
            }
        }

    }

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event) {
        this.saveBedRespawnLocation(event.getBed().getWorld(), event.getPlayer());
    }

    private void saveBedRespawnLocation(World world, Player player) {
        UniversePlayer uPlayer = (UniversePlayer)Universes.plugin.onlinePlayers.get(player.getName());
        Universe universe = (Universe)Universes.plugin.universes.get(world.getName());
        uPlayer.saveBedLocation(universe, player.getLocation());
        uPlayer.storeBedLocations();
    }
}
