package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.util.MathUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class HologramVisualization implements Visualization<Location> {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();

    private final BedWarsPlugin plugin;
    private final MapSetup setup;
    private final int ranking;
    private final int kills;
    private final int deaths;
    private final int playedGames;
    private final int wonGames;
    private final int destroyedBeds;
    private final double kd;
    private final double winRate;

    public HologramVisualization(BedWarsPlugin plugin, MapSetup setup, int ranking, int kills, int deaths, int playedGames, int wonGames, int destroyedBeds) {
        this.plugin = plugin;
        this.setup = setup;
        this.ranking = ranking;
        this.kills = kills;
        this.deaths = deaths;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.destroyedBeds = destroyedBeds;
        this.kd = MathUtil.round((double) kills / deaths, 2);
        this.winRate = MathUtil.toPercentage((double) wonGames / playedGames, 2);
    }

    @Override
    public void show(@NotNull Location location) {
        final Location bottom = location.add(0, 0.5D, 0);

        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 2.25D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.name"))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 2.0D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.all-time"))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 1.75D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.ranking", Component.text(this.ranking)))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 1.5D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.kills", Component.text(this.kills)))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 1.25D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.deaths", Component.text(this.deaths)))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 1.0D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.kd", Component.text(this.kd)))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 0.75D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.played-games", Component.text(this.playedGames)))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 0.5D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.wins", Component.text(this.wonGames)))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom.clone().add(0, 0.25D, 0)).render(new TextVisualization(this.setup, Component.translatable("hologram.destroyed-beds", Component.text(this.destroyedBeds)))));
        this.visualizationManager.registerTask(this.setup, new TextRenderer(this.plugin, this.setup, bottom).render(new TextVisualization(this.setup, Component.translatable("hologram.win-rate", Component.text(this.winRate)))));
    }
}
