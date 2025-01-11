package net.whispwriting.spawnify.commands.tab_completers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.whispwriting.universes.Universes;
import net.whispwriting.universes.utils.Universe;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class AnySpawnTabCompleter implements TabCompleter {
    public AnySpawnTabCompleter() {
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> worlds = new ArrayList();
        Iterator var6;
        World world;
        if (!Universes.plugin.worldEntryPermissions) {
            if (!sender.hasPermission("Universes.teleport")) {
                return worlds;
            } else {
                var6 = Bukkit.getWorlds().iterator();

                while(var6.hasNext()) {
                    world = (World)var6.next();
                    Universe universe = (Universe)Universes.plugin.universes.get(world.getName());
                    if (!worlds.contains(universe.name())) {
                        worlds.add(universe.name());
                    }
                }

                return worlds;
            }
        } else {
            var6 = Bukkit.getWorlds().iterator();

            while(true) {
                do {
                    if (!var6.hasNext()) {
                        return worlds;
                    }

                    world = (World)var6.next();
                } while(!sender.hasPermission("Universes.teleport." + world.getName()) && !sender.hasPermission("Universes.teleport.*"));

                worlds.add(world.getName());
            }
        }
    }
}
