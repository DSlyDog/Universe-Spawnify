package net.whispwriting.spawnify.events;

import net.whispwriting.spawnify.Spawnify;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.utils.UniversePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private Spawnify plugin;
    private Universes universes;

    public PlayerJoin(Spawnify plugin, Universes universes) {
        this.plugin = plugin;
        this.universes = universes;
    }

    @EventHandler
    public void playerJoin(final PlayerJoinEvent event) {
        if (Universes.plugin.hubOnJoin && !this.isSkippingHubTele(event) && this.plugin.spawns.containsKey("hub")) {
            Runnable runnable = new Runnable() {
                public void run() {
                    event.getPlayer().teleport((Location)PlayerJoin.this.plugin.spawns.get("hub"));
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, runnable);
        }

        UniversePlayer player = (UniversePlayer)Universes.plugin.onlinePlayers.get(event.getPlayer().getName());
        player.buildBedLocations();
    }

    private boolean isSkippingHubTele(PlayerJoinEvent event) {
        return !event.getPlayer().hasPlayedBefore() && this.universes.useFirstJoinSpawn;
    }
}
