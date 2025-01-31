package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BedConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

    private final List<Team> tempTeams = new ArrayList<>();
    private final List<Team> teams;
    private final int count;
    private int phase;

    private TranslatableComponent teamName;
    private Team team;

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
        player.sendMessage(Component.translatable("mapsetup.stage.14"));
        player.sendMessage(Component.translatable("mapsetup.stage.14.tip"));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;
        this.team = teams.get(phase - 1);
        this.teamName = Component.translatable("team." + convertName(team.name()));

        player.sendMessage(Component.translatable("mapsetup.stage.14.name", Component.text(phase), teamName));
        Feedback.success(player);
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Location down = player.getLocation();

        if (isNotPlayerConfiguring(event.getPlayer())) {
            return;
        }
        if (isNotOnGround(player, down)) {
            return;
        }
        if (isNotStage(14)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }
        if (phase < count) {
            sendSuccessMessage();
            Feedback.success(player);

            player.getFacing().getDirection().normalize();

            Team team = this.team.bedDownside(down);
            tempTeams.add(team);

            if (getFacingLocation(player, down) != null) {
                team = this.team.bedUpside(getFacingLocation(player, down));
                tempTeams.add(team);
                startPhase(++phase);
                return;
            }

            player.sendMessage(Component.translatable("mapsetup.stage.14.error.facing"));
            Feedback.error(player);
            return;
        }

        sendSuccessMessage();
        player.sendMessage(Component.translatable("mapsetup.stage.14.success"));
        Feedback.success(player);

        gameMapSetup.configureTeams(tempTeams);
        gameMapSetup.startStage(15);
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.14.name.success", teamName));
    }

    private Location getFacingLocation(Player player, Location down) {
        BlockFace facing = player.getFacing(); // Blickrichtung des Spielers
        Location newLocation = down.clone(); // Kopie der Down-Location erstellen

        // Je nach Richtung den neuen Block berechnen
        switch (facing) {
            case NORTH -> newLocation.add(0, 0, -1);
            case SOUTH -> newLocation.add(0, 0, 1);
            case WEST -> newLocation.add(-1, 0, 0);
            case EAST -> newLocation.add(1, 0, 0);
            default -> {
                return null;
            }
        }
        return newLocation;
    }
}
