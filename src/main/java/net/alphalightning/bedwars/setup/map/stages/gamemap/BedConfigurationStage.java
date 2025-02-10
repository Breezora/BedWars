package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.feedback.visual.renderer.BoundingBoxRenderer;
import net.alphalightning.bedwars.feedback.visual.renderer.EntityRenderer;
import net.alphalightning.bedwars.feedback.visual.renderer.EntityVisualization;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.alphalightning.bedwars.util.BedUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Collections;
import java.util.List;

public class BedConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final List<Team> teams;
    private final int count;
    private int phase;

    private TranslatableComponent teamName = null;
    private Team team = null;

    public BedConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.teams = Collections.emptyList();
            this.count = 0;
            return;
        }
        this.teams = gameMapSetup.teams();
        this.count = gameMapSetup.teams().size();
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.15"));
        player.sendMessage(Component.translatable("mapsetup.stage.15.tip"));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;
        this.team = teams.get(phase - 1);
        this.teamName = Component.translatable("team." + convertName(team.name()));

        player.sendMessage(Component.translatable("mapsetup.stage.15.name",
                NamedTranslationArgument.numeric("phase", phase),
                NamedTranslationArgument.component("name", teamName)
        ));
        Feedback.success(player);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Location location = player.getLocation();

        if (isNotPlayerConfiguring(event.getPlayer())) {
            return;
        }
        if (isNotOnGround(player, location)) {
            return;
        }
        if (isNotStage(GameMapSetup.BED_CONFIGURATION_STAGE)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        // Bed configuration is not completed

        updateBed(gameMapSetup, location.add(OFFSET));

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        // Configuration is completed

        player.sendMessage(Component.translatable("mapsetup.stage.15.success"));
        setupManager.finishSetup(player, GameMapSetup.COMPLETION_STAGE);
    }

    private void updateBed(MapSetup setup, Location bottom) {
        team.bedBottomHalf(bottom);

        Location topHalf = BedUtil.calculateHeadLocation(bottom, player.getFacing());
        if (topHalf == null) {
            player.sendMessage(Component.translatable("mapsetup.stage.15.error.facing"));
            Feedback.error(player);
            return;
        }

        team.bedTopHalf(topHalf);

        final List<Block> blocks = List.of(topHalf.getBlock(), bottom.getBlock());
        this.visualizationManager.registerTask(setup, new BoundingBoxRenderer<List<Block>>(plugin, setup).render(blocks, team.color()));
        this.visualizationManager.registerTask(setup, new EntityRenderer(plugin, setup, bottom)
                .render(new EntityVisualization(setup, player, EntityType.BLOCK_DISPLAY, BedUtil.fromColor(team.color()), null))
        );

        player.sendMessage(Component.translatable("mapsetup.stage.15.name.success", teamName));
        Feedback.success(player);
    }
}
