package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Collections;
import java.util.List;

public class TeamSpawnpointConfigurationStage extends Stage implements LocationConfiguration {

    private final List<Team> teams;
    private final int size;
    private int phase;

    private TranslatableComponent teamName = null;

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
        this.teamName = Component.translatable("team.red");

        player.sendMessage(Component.translatable("mapsetup.stage.9.name",
                Component.text(phase),
                teamName
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
            sendSuccessMessage();
            Feedback.success(player);

            Team team = teams.get(phase - 1).spawnpoint(location);
            teams.set(phase - 1, team);

            startPhase(++phase);
            return;
        }

        sendSuccessMessage();
        player.sendMessage(Component.translatable("mapsetup.stage.9.success"));
        Feedback.success(player);

        gameMapSetup.startStage(10);
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.9.name.success", NamedTranslationArgument.component("name", teamName)));
    }

}
