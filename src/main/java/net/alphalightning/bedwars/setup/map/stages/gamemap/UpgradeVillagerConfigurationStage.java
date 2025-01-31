package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.List;

public class UpgradeVillagerConfigurationStage extends Stage implements LocationConfiguration {

    private final int count;
    private int phase;
    private final List<Location> locations = new ArrayList<>();

    public UpgradeVillagerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.count = 0;
            return;
        }
        this.count = gameMapSetup.teams().size();
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.13"));
        startPhase(1);
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
        if (isNotStage(13)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }
        if (phase < count) {
            sendSuccessMessage();
            Feedback.success(player);

            locations.add(location);
            startPhase(++phase);
            return;
        }

        sendSuccessMessage();
        player.sendMessage(Component.translatable("mapsetup.stage.13.success"));
        Feedback.success(player);

        gameMapSetup.configureUpgradeVillager(locations);
        gameMapSetup.startStage(14);
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;

        player.sendMessage(Component.translatable("mapsetup.stage.13.name", Component.text(phase)));
    }

    private void sendSuccessMessage() {
        player.sendMessage(Component.translatable("mapsetup.stage.13.name.success", Component.text(phase)));
        Feedback.success(player);
    }
}
