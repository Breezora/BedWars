package net.alphalightning.bedwars.setup.map.stages.gamemap;

import com.destroystokyo.paper.ParticleBuilder;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.JacksonTeam;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.alphalightning.bedwars.util.CuboidSelection;
import net.alphalightning.bedwars.util.FloodFill;
import net.alphalightning.bedwars.util.RegionInformation;
import net.alphalightning.bedwars.util.RegionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FloodFillConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

    private final List<CuboidSelection> selections;
    private final List<JacksonTeam> teams;

    private final int count;
    private int phase;

    private TranslatableComponent teamName = null;

    public FloodFillConfigurationStage(@NotNull BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.selections = Collections.emptyList();
            this.teams = Collections.emptyList();
            this.count = 0;
            return;
        }

        this.selections = gameMapSetup.selections();
        this.teams = gameMapSetup.teams();
        this.count = gameMapSetup.teams().size();
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.17"));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) return;

        this.phase = phase;

        JacksonTeam team = teams.get(phase - 1);
        this.teamName = Component.translatable("team." + convertName(team.name()));

        player.sendMessage(Component.translatable("mapsetup.stage.17.name",
                NamedTranslationArgument.numeric("phase", phase),
                NamedTranslationArgument.component("team", teamName)
        ));
        Feedback.success(player);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Location location = player.getLocation();

        if (isNotPlayerConfiguring(event.getPlayer())) return;
        if (isNotOnGround(player, location)) return;
        if (isNotStage(GameMapSetup.FLOOD_FILL_CONFIGURATION_STAGE)) return;
        if (!isPlayerInSelection()) return;

        if (!(setup instanceof GameMapSetup gameMapSetup)) return;

        FloodFill floodFill = new FloodFill(location.add(OFFSET));

        floodFill.fill().whenComplete((locations, throwable) -> {
            if (throwable != null) {
                plugin.getLogger().severe("There was an error while flood filling the space: " + throwable.getMessage());
                return;
            }

            if (!isSelectionValid(locations)) {
                player.sendMessage(Component.translatable("mapsetup.stage.17.name.error"));
                Feedback.error(player);
                return;
            }

            for (Location current : locations) {
                Bukkit.getScheduler().runTaskTimer(plugin, () -> createParticle(current.toCenterLocation()), 0L, 10L);
            }

            if (phase < count) {
                startPhase(++phase);
                return;
            }

            player.sendMessage(Component.translatable("mapsetup.stage.17.name.success",
                    NamedTranslationArgument.numeric("phase", phase),
                    NamedTranslationArgument.component("team", teamName)
            ));
            Feedback.success(player);

            RegionInformation information = RegionUtil.createRegionInformation(locations, selections.get(phase - 1));
            gameMapSetup.configureRegionInformation(List.of(information));

            gameMapSetup.startStage(GameMapSetup.SPAWNER_PROTECTION_CONFIGURATION_STAGE);
        });
    }

    private boolean isPlayerInSelection() {
        CuboidSelection selection = selections.get(phase - 1);
        return selection.allBetween().contains(player.getLocation().getBlock().getLocation());
    }

    private boolean isSelectionValid(Set<Location> floodFillLocations) {
        CuboidSelection selection = selections.get(phase - 1);

        int minX = Math.min(selection.first().getBlockX(), selection.second().getBlockX());
        int maxX = Math.max(selection.first().getBlockX(), selection.second().getBlockX());
        int minY = Math.min(selection.first().getBlockY(), selection.second().getBlockY());
        int maxY = Math.max(selection.first().getBlockY(), selection.second().getBlockY());
        int minZ = Math.min(selection.first().getBlockZ(), selection.second().getBlockZ());
        int maxZ = Math.max(selection.first().getBlockZ(), selection.second().getBlockZ());

        for (Location location : floodFillLocations) {
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();

            if (x < minX || x > maxX || y < minY || y > maxY || z < minZ || z > maxZ) return false;
        }

        return true;
    }

    private void createParticle(Location location) {
        new ParticleBuilder(Particle.DUST)
                .location(location)
                .color(Color.fromRGB(0xE3197C), 0.75F)
                .spawn();
    }
}