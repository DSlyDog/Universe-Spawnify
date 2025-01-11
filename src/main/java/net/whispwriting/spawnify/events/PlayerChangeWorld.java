package net.whispwriting.spawnify.events;

import net.whispwriting.universes.Universes;
import net.whispwriting.universes.utils.Universe;
import net.whispwriting.universes.utils.UniversePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangeWorld implements Listener {
    public PlayerChangeWorld() {
    }

    @EventHandler
    public void onChangedWorld(PlayerChangedWorldEvent event) {
        if (Universes.plugin.perWorldBedRespawn) {
            Universe from = (Universe)Universes.plugin.universes.get(event.getFrom().getName());
            Universe to = (Universe)Universes.plugin.universes.get(event.getPlayer().getWorld().getName());
            if (Universes.plugin.inventoryGrouping) {
                if (!from.name().equals(to.name())) {
                    this.updateBedSpawn(event, from, to);
                }

            } else {
                this.updateBedSpawn(event, from, to);
            }
        }
    }

    private void updateBedSpawn(PlayerChangedWorldEvent event, Universe from, Universe to) {
        UniversePlayer player = (UniversePlayer)Universes.plugin.onlinePlayers.get(event.getPlayer().getName());
        if (event.getPlayer().getBedSpawnLocation() != null) {
            player.saveBedLocation(from, event.getPlayer().getBedSpawnLocation());
            player.storeBedLocations();
        }

        event.getPlayer().setBedSpawnLocation(player.loadBedLocation(to), true);
    }
}
