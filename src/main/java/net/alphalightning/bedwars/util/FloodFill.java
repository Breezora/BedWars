package net.alphalightning.bedwars.util;

import com.destroystokyo.paper.ParticleBuilder;
import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class FloodFill {

    private static final int MAX_BLOCKS = 5_000;

    private final Set<Location> visited = new HashSet<>();
    private final BedWarsPlugin plugin;
    private final Location start;
    private final World world;


    public FloodFill(BedWarsPlugin plugin, @NotNull Location start) {
        this.plugin = plugin;
        this.start = start;
        this.world = start.getWorld();
    }

    public CompletableFuture<Set<Location>> fill() {
        CompletableFuture<Set<Location>> future = new CompletableFuture<>();

        if (world == null) {
            future.complete(Collections.emptySet());
            return future;
        }

        future.completeAsync(() -> {
            Queue<Location> queue = new LinkedList<>();
            queue.add(start);

            int filled = 0;

            while (!queue.isEmpty() && filled < MAX_BLOCKS) {
                Location current = queue.poll();
                if (visited.contains(current)) continue;

                Block block = world.getBlockAt(current);
                if (block.getType() != Material.AIR) continue;

                spawnParticle(current);

                visited.add(current);
                filled++;

                for (Location neighbor : neighbors(current)) {
                    if (!visited.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
            return visited; // Die Menge aller Luft-Positionen, die erfolgreich innerhalb der geschlossenen Region gefunden und markiert wurden.
        });

        return future;
    }

    private void spawnParticle(Location current) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> createParticle(current), 0L, 10L);
    }

    private void createParticle(Location current) {
        new ParticleBuilder(Particle.DUST)
                .location(current.toCenterLocation())
                .color(Color.fromRGB(0xE3197C), 0.75F)
                .spawn();
    }

    private List<Location> neighbors(Location loc) {
        List<Location> neighbors = new ArrayList<>();
        neighbors.add(loc.clone().add(1, 0, 0));
        neighbors.add(loc.clone().add(-1, 0, 0));
        neighbors.add(loc.clone().add(0, 1, 0));
        neighbors.add(loc.clone().add(0, -1, 0));
        neighbors.add(loc.clone().add(0, 0, 1));
        neighbors.add(loc.clone().add(0, 0, -1));
        return neighbors;
    }
}
