package net.whispwriting.spawnify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.whispwriting.spawnify.commands.AnySpawnCommand;
import net.whispwriting.spawnify.commands.HubCommand;
import net.whispwriting.spawnify.commands.SModifyCommand;
import net.whispwriting.spawnify.commands.SetGroupSpawnCommand;
import net.whispwriting.spawnify.commands.SetHubCommand;
import net.whispwriting.spawnify.commands.SpawnCommand;
import net.whispwriting.spawnify.commands.tab_completers.AnySpawnTabCompleter;
import net.whispwriting.spawnify.events.PlayerChangeWorld;
import net.whispwriting.spawnify.events.PlayerInteract;
import net.whispwriting.spawnify.events.PlayerJoin;
import net.whispwriting.spawnify.events.RespawnEvent;
import net.whispwriting.spawnify.files.GroupSpawnsFile;
import net.whispwriting.spawnify.files.HubSpawnFile;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.files.AbstractFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Spawnify extends JavaPlugin {
    public Universes plugin = (Universes)Bukkit.getPluginManager().getPlugin("Universes");
    public Map<String, Location> spawns = new HashMap();
    public Map<UUID, Map<String, Location>> userSpawnPoints = new HashMap();
    public GroupSpawnsFile groupSpawnsFile;
    public HubSpawnFile hubSpawnFile;

    public Spawnify() {
        this.groupSpawnsFile = new GroupSpawnsFile(this.plugin);
        this.hubSpawnFile = new HubSpawnFile(this.plugin);
    }

    @Override
    public void onEnable() {
        this.loadSpawns();
        this.plugin.getCommand("universemodify").setExecutor(new SModifyCommand(this, this.plugin));
        this.getCommand("setgroupspawn").setExecutor(new SetGroupSpawnCommand(this, this.plugin));
        this.getCommand("anyspawn").setExecutor(new AnySpawnCommand(this, this.plugin));
        this.getCommand("anyspawn").setTabCompleter(new AnySpawnTabCompleter());
        this.getCommand("spawn").setExecutor(new SpawnCommand(this, this.plugin));
        this.getCommand("sethub").setExecutor(new SetHubCommand(this));
        this.getCommand("hub").setExecutor(new HubCommand(this));
        Bukkit.getPluginManager().registerEvents(new RespawnEvent(this, this.plugin), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this, this.plugin), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangeWorld(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
    }

    @Override
    public void onDisable() {
    }

    private void loadSpawns() {
        Iterator var1 = this.groupSpawnsFile.get().getConfigurationSection("").getKeys(false).iterator();

        while(var1.hasNext()) {
            String group = (String)var1.next();
            Location spawn = this.buildLocation(group, this.groupSpawnsFile);
            if (spawn != null) {
                this.spawns.put(group, spawn);
            }
        }

        Location hub = this.buildLocation("hub", this.hubSpawnFile);
        if (hub != null) {
            this.spawns.put("hub", hub);
        }

    }

    private Location buildLocation(String label, AbstractFile file) {
        String world = file.get().getString(label + ".world");
        double x = file.get().getDouble(label + ".x");
        double y = file.get().getDouble(label + ".y");
        double z = file.get().getDouble(label + ".z");
        float yaw = (float)file.get().getDouble(label + ".yaw");
        float pitch = (float)file.get().getDouble(label + ".pitch");

        try {
            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            return loc;
        } catch (IllegalArgumentException var13) {
            return null;
        }
    }
}
