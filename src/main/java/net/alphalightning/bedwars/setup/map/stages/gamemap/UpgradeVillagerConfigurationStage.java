package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.UnboundTeamVisuals;
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

    private final List<Location> locations = new ArrayList<>();
    private final int count;
    private int phase;

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
        player.sendMessage(Component.translatable("mapsetup.stage.14"));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;

        player.sendMessage(Component.translatable("mapsetup.stage.14.name", Component.text(phase)));
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
        if (isNotStage(13)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        // Upgrade villagers have not all been set

        final Location withOffset = location.add(OFFSET);
        locations.add(withOffset);

        UnboundTeamVisuals.renderShop(plugin, gameMapSetup, player, location, withOffset, Component.translatable("entity.villager.shop.upgrade"));

        player.sendMessage(Component.translatable("mapsetup.stage.14.name.success", Component.text(phase)));
        Feedback.success(player);

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        // All villagers are configured

        player.sendMessage(Component.translatable("mapsetup.stage.14.success"));
        gameMapSetup.configureUpgradeVillager(locations);
        gameMapSetup.startStage(GameMapSetup.BED_CONFIGURATION_STAGE);
    }
}
