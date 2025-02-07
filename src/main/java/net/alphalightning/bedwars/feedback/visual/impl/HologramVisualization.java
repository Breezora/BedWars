package net.alphalightning.bedwars.feedback.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.visual.Visualization;
import net.alphalightning.bedwars.utils.MathUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class HologramVisualization implements Visualization<Location> {

    private static final Vector OFFSET = new Vector(0, 0.25D, 0);

    private final BedWarsPlugin plugin;
    private final int ranking;
    private final int kills;
    private final int deaths;
    private final int playedGames;
    private final int wonGames;
    private final int destroyedBeds;
    private final double kd;
    private final double winRate;

    public HologramVisualization(BedWarsPlugin plugin, int ranking, int kills, int deaths, int playedGames, int wonGames, int destroyedBeds) {
        this.plugin = plugin;
        this.ranking = ranking;
        this.kills = kills;
        this.deaths = deaths;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.destroyedBeds = destroyedBeds;
        this.kd = MathUtil.round((double) kills / deaths, 2);
        this.winRate = MathUtil.round((double) wonGames / playedGames, 2);
    }

    @Override
    public void show(@NotNull Location location) {
        final Location bottom = location.add(0, 0.5D, 0);

        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(9))).render(new TextVisualization(Component.translatable("hologram.name")));
        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(8))).render(new TextVisualization(Component.translatable("hologram.all-time")));
        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(7))).render(new TextVisualization(Component.translatable("hologram.ranking", Component.text(this.ranking))));
        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(6))).render(new TextVisualization(Component.translatable("hologram.kills", Component.text(this.kills))));
        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(5))).render(new TextVisualization(Component.translatable("hologram.deaths", Component.text(this.deaths))));
        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(4))).render(new TextVisualization(Component.translatable("hologram.kd", Component.text(this.kd))));
        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(3))).render(new TextVisualization(Component.translatable("hologram.played-games", Component.text(this.playedGames))));
        new TextRenderer(this.plugin, bottom.add(OFFSET.multiply(2))).render(new TextVisualization(Component.translatable("hologram.wins", Component.text(this.wonGames))));
        new TextRenderer(this.plugin, bottom.add(OFFSET)).render(new TextVisualization(Component.translatable("hologram.destroyed-beds", Component.text(this.destroyedBeds))));
        new TextRenderer(this.plugin, bottom).render(new TextVisualization(Component.translatable("hologram.win-rate", Component.text(this.winRate))));

    }
}
