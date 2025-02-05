package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.setup.visual.VisualizationRenderer;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class PlayerRenderer implements VisualizationRenderer<PlayerVisualisation> {

    private final BedWarsPlugin plugin;
    private final NPC npc;

    public PlayerRenderer(BedWarsPlugin plugin, NPC npc) {
        this.plugin = plugin;
        this.npc = npc;
    }

    @Override
    public @NotNull BukkitTask render(@NotNull PlayerVisualisation visualisation) {
        return Bukkit.getScheduler().runTask(plugin, () -> visualisation.show(npc));
    }
}
