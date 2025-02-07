package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.UnboundTeamVisuals;
import net.alphalightning.bedwars.feedback.visual.impl.EntityRenderer;
import net.alphalightning.bedwars.feedback.visual.impl.EntityVisualization;
import net.alphalightning.bedwars.feedback.visual.impl.TextRenderer;
import net.alphalightning.bedwars.feedback.visual.impl.TextVisualization;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.List;

public class ShopVillagerConfigurationStage extends Stage implements LocationConfiguration {

    private final List<Location> locations = new ArrayList<>();
    private final int count;
    private int phase;

    public ShopVillagerConfigurationStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            this.count = 0;
            return;
        }
        this.count = gameMapSetup.teams().size();
    }

    @Override
    public void run() {
        player.sendMessage(Component.translatable("mapsetup.stage.12"));
        startPhase(1);
    }

    private void startPhase(int phase) {
        if (phase > count) {
            return;
        }
        this.phase = phase;

        player.sendMessage(Component.translatable("mapsetup.stage.12.name", Component.text(phase)));
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
        if (isNotStage(12)) {
            return;
        }
        if (!(setup instanceof GameMapSetup gameMapSetup)) {
            return;
        }

        // Shop villager have not all been configured

        final Location withOffset = location.add(OFFSET);
        locations.add(withOffset);

        new EntityRenderer(plugin, location.toCenterLocation().subtract(0, 0.5D, 0)).render(new EntityVisualization(EntityType.VILLAGER, null));
        new TextRenderer(plugin, withOffset.toCenterLocation().add(0, 1.75D, 0)).render(new TextVisualization(Component.translatable("entity.interact")));
        new TextRenderer(plugin, withOffset.toCenterLocation().add(0, 1.95D, 0)).render(new TextVisualization(Component.translatable("entity.villager.shop.item")));
        Bukkit.getScheduler().runTaskLater(plugin, () -> UnboundTeamVisuals.renderSpawnpoint(plugin, player, withOffset), 1L); // Render this later to avoid some stupid collisions with the texts and villager

        player.sendMessage(Component.translatable("mapsetup.stage.12.name.success", Component.text(phase)));
        Feedback.success(player);

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        // Villager configuration is completed

        player.sendMessage(Component.translatable("mapsetup.stage.12.success"));
        gameMapSetup.configureShopVillager(locations);
        gameMapSetup.startStage(13);
    }
}
