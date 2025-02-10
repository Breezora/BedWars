package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.feedback.visual.renderer.BoundingBoxRenderer;
import net.alphalightning.bedwars.feedback.visual.renderer.LootspawnerRenderer;
import net.alphalightning.bedwars.feedback.visual.renderer.LootspawnerVisualization;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.alphalightning.bedwars.util.BlockUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Collections;
import java.util.List;


public class TeamLootspawnerConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();
    private final List<Team> teams;
    private final int size;
    private int phase;

    private TranslatableComponent teamName = null;
    private Team team = null;

    public TeamLootspawnerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.teams = Collections.emptyList();
            this.size = 0;
            return;
        }
        this.teams = gameMapSetup.teams();
        this.size = teams.size();
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.11"));
    }

    private void startPhase(int phase) {
        if (phase > size) {
            return;
        }
        this.phase = phase;
        this.team = teams.get(phase - 1);
        this.teamName = Component.translatable("team." + convertName(team.name()));

        player.sendMessage(Component.translatable("mapsetup.stage.11.name",
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
        if (isNotStage(GameMapSetup.TEAM_LOOTSPAWNER_CONFIGURATION_STAGE)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        // Lootspawner configuration is not completed

        final Location withOffset = location.add(OFFSET);
        team.lootspawner(withOffset);

        final int color = gameMapSetup.hasSlowIron() ? 0x00ff00 : 0xff0000;
        if (!BlockUtil.isHalfBlock(withOffset)) {
            this.visualizationManager.registerTask(gameMapSetup, new BoundingBoxRenderer<Block>(plugin, gameMapSetup)
                    .render(withOffset.getBlock(), color)
            );
        } else {
            this.visualizationManager.registerTask(gameMapSetup, new BoundingBoxRenderer<Location>(plugin, gameMapSetup)
                    .render(withOffset.toCenterLocation(), color)
            );
        }
        this.visualizationManager.registerTask(gameMapSetup, new LootspawnerRenderer(plugin, gameMapSetup, withOffset)
                .render(new LootspawnerVisualization(plugin, gameMapSetup, null, true))
        );

        player.sendMessage(Component.translatable("mapsetup.stage.11.name.success", teamName));
        Feedback.success(player);

        if (phase < size) {
            startPhase(++phase);
            return;
        }

        // Lootspawner are all configured

        player.sendMessage(Component.translatable("mapsetup.stage.11.success"));
        gameMapSetup.startStage(GameMapSetup.TEAM_CHEST_CONFIGURATION_STAGE);
    }
}
