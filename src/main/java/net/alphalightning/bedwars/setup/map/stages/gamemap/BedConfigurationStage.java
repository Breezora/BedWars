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
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class BedConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

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

        player.sendMessage(Component.translatable("mapsetup.stage.14.name",
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
        if (isNotStage(14)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        if (phase < count) { // Teams are configured
            updateBed(location);
            startPhase(++phase);
            return;
        }

        updateBed(location); // Last team is configured

        player.sendMessage(Component.translatable("mapsetup.stage.14.success"));
        Feedback.success(player);

        gameMapSetup.startStage(15);
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.14.name.success", teamName));
    }

    private void updateBed(Location bottom) {
        team.bedBottomHalf(bottom);

        Location topHalf = calculateTopHalf(bottom);
        if (topHalf == null) {
            player.sendMessage(Component.translatable("mapsetup.stage.14.error.facing"));
            Feedback.error(player);
            return;
        }

        team.bedTopHalf(topHalf);

        player.sendMessage(Component.translatable("mapsetup.stage.14.name.success", teamName));
        Feedback.success(player);
    }

    private @Nullable Location calculateTopHalf(@NotNull Location bottom) {
        Location top = bottom.clone();
        switch (player.getFacing()) {
            case NORTH -> top.add(0, 0, -1);
            case SOUTH -> top.add(0, 0, 1);
            case WEST -> top.add(-1, 0, 0);
            case EAST -> top.add(1, 0, 0);
            default -> {
                return null;
            }
        }
        return top;
    }
}
