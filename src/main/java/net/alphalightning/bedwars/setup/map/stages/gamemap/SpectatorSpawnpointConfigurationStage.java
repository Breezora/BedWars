package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.visual.impl.MultiVerticalBlockRenderer;
import net.alphalightning.bedwars.setup.visual.impl.MultiVerticalBlockVisualisation;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

public class SpectatorSpawnpointConfigurationStage extends Stage implements LocationConfiguration {

    public SpectatorSpawnpointConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.6"));
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
        if (isNotStage(6)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        final Location withOffset = location.add(OFFSET);

        new MultiVerticalBlockRenderer(plugin, List.of(withOffset.getBlock(), withOffset.add(0, 1, 0).getBlock()))
                .render(new MultiVerticalBlockVisualisation(0xbfbfbf));

        player.sendMessage(Component.translatable("mapsetup.stage.6.success"));
        Feedback.success(player);

        gameMapSetup.configureSpectatorSpawn(withOffset);
        gameMapSetup.startStage(7);
    }
}
