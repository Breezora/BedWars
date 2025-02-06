package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.alphalightning.bedwars.setup.visual.impl.BlockEdgeRenderer;
import net.alphalightning.bedwars.setup.visual.impl.BlockEdgeVisualization;
import net.alphalightning.bedwars.setup.visual.impl.FakeBlockRenderer;
import net.alphalightning.bedwars.setup.visual.impl.FakeBlockVisualization;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Collections;
import java.util.List;

public class TeamChestConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

    private final List<Team> teams;
    private final int count;
    private int phase;

    private TranslatableComponent teamName = null;
    private Team team = null;

    public TeamChestConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
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
        player.sendMessage(Component.translatable("mapsetup.stage.11"));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) {
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
        if (isNotStage(11)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        // Teamchest configuration is not completed

        final Location withOffset = location.add(OFFSET);

        team.chest(withOffset);
        new BlockEdgeRenderer(plugin, withOffset.getBlock()).render(new BlockEdgeVisualization(team.color()));
        new FakeBlockRenderer(plugin, withOffset).render(new FakeBlockVisualization(Material.CHEST));

        player.sendMessage(Component.translatable("mapsetup.stage.11.name.success", teamName));
        Feedback.success(player);

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        // All chests have been configured

        player.sendMessage(Component.translatable("mapsetup.stage.11.success"));
        gameMapSetup.startStage(12);
    }
}
