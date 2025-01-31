package net.alphalightning.bedwars.setup.map.stages.gamemap;

import io.papermc.paper.event.player.AsyncChatEvent;
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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class TeamLootspawnerConfigurationStage extends Stage implements TeamConfiguration, LocationConfiguration {

    private static final String YES = "yes";
    private static final String YES_ALIAS = "y";
    private static final String NO = "no";
    private static final String NO_ALIAS = "n";
    private static final Set<String> VALID_MESSAGES = Set.of(YES, YES_ALIAS, NO, NO_ALIAS);

    private final List<Team> tempTeams = new ArrayList<>();
    private final List<Team> teams;
    private final int count;
    private boolean isSlowConfigFinished = false;
    private int phase;

    private TranslatableComponent teamName = null;
    private Team team;

    public TeamLootspawnerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.teams = Collections.emptyList();
            this.count = 0;
            return;
        }
        this.teams = gameMapSetup.teams();
        this.count = teams.size();
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.10"));
        player.sendMessage(Component.translatable("mapsetup.stage.10.lootspawner"));
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;
        this.team = teams.get(phase - 1);
        this.teamName = Component.translatable("team." + convertName(team.name()));

        player.sendMessage(Component.translatable("mapsetup.stage.10.name", Component.text(phase), teamName));
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!isSlowConfigFinished) {
            if (isNotPlayerConfiguring(player)) {
                return;
            }
            if (isNotStage(10)) {
                return;
            }
            if (!(setup instanceof GameMapSetup gameMapSetup)) {
                return;
            }
            event.setCancelled(true); //Nachricht nicht in den Chat senden

            String message = event.signedMessage().message();

            if (VALID_MESSAGES.contains(message.toLowerCase())) {
                if (isSlowLootspawner(message)) {
                    gameMapSetup.configureSlowIron(true);
                    player.sendMessage(Component.translatable("mapsetup.stage.10.lootspawner.slow"));
                    Feedback.success(player);

                } else {
                    gameMapSetup.configureSlowIron(false);
                    Feedback.success(player);
                    player.sendMessage(Component.translatable("mapsetup.stage.10.lootspawner.fast"));
                }
            } else {
                player.sendMessage(Component.translatable("mapsetup.stage.10.lootspawner.error"));
                Feedback.error(player);
                return;
            }

            isSlowConfigFinished = true;
            player.sendMessage(Component.translatable("mapsetup.stage.10.lootspawner.success"));
            startPhase(1);
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Location location = player.getLocation();
        if (isSlowConfigFinished) {
            if (isNotPlayerConfiguring(event.getPlayer())) {
                return;
            }
            if (isNotOnGround(player, location)) {
                return;
            }
            if (isNotStage(10)) {
                return;
            }
            if (!(setup instanceof GameMapSetup gameMapSetup)) {
                return;
            }
            if (phase < count) {
                Team team = this.team.lootspawner(location);
                tempTeams.add(team);

                sendSuccessMessage();
                Feedback.success(player);

                startPhase(++phase);
                return;
            }

            sendSuccessMessage();
            player.sendMessage(Component.translatable("mapsetup.stage.10.success"));
            Feedback.success(player);

            gameMapSetup.configureTeams(tempTeams);
            gameMapSetup.startStage(11);
        }
    }

    private boolean isSlowLootspawner(@NotNull String message) {
        if (message.equalsIgnoreCase(YES) || message.equalsIgnoreCase(YES_ALIAS)) {
            return true;
        } else if (message.equalsIgnoreCase(NO) || message.equalsIgnoreCase(NO_ALIAS)) {
            return false;
        }
        return false;
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.10.name.success", teamName));
        Feedback.success(player);
    }
}
