package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Collections;
import java.util.List;

public class TeamSpawnpointConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

    private final List<Team> teams;
    private final int size;
    private int phase;

    private TranslatableComponent teamName = null;
    private Team team;

    public TeamSpawnpointConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
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
        player.sendMessage(Component.translatable("mapsetup.stage.9", Component.text(size)));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > size) {
            return;
        }
        this.phase = phase;
        this.team = teams.get(phase - 1);

        Bukkit.getLogger().info("Current team: " + team.name());

        this.teamName = Component.translatable("team." + convertName(team.name()));

        player.sendMessage(Component.translatable("mapsetup.stage.9.name",
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
        if (isNotStage(9)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        if (phase < size) {
            team.spawnpoint(location);

            Bukkit.getLogger().info("Update team " + team.name() + ": " + team);

            sendSuccessMessage();
            Feedback.success(player);

            startPhase(++phase);
            return;
        }

        Bukkit.getLogger().info("Teams: " + teams);

        sendSuccessMessage();
        player.sendMessage(Component.translatable("mapsetup.stage.9.success"));
        Feedback.success(player);

        gameMapSetup.startStage(10);
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.9.name.success", NamedTranslationArgument.component("name", teamName)));
    }

}
