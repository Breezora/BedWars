package net.alphalightning.bedwars.setup.map.stages.lobby;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.feedback.visual.impl.HologramRenderer;
import net.alphalightning.bedwars.feedback.visual.impl.HologramVisualization;
import net.alphalightning.bedwars.feedback.visual.manager.VisualizationManager;
import net.alphalightning.bedwars.setup.map.LobbyMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class ConfigureHologramStage extends Stage implements LocationConfiguration {

    private final VisualizationManager visualizationManager = VisualizationManager.instance();

    public ConfigureHologramStage(BedWarsPlugin plugin, Player player, MapSetup setup) {
        super(plugin, player, setup);
    }

    @Override
    public void run() {
        Feedback.success(player);
        player.sendMessage(Component.translatable("lobbysetup.stage.2"));
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
        if (isNotStage(2)) {
            return;
        }
        if (!(setup instanceof LobbyMapSetup lobbyMapSetup)) {
            return;
        }

        final Location withOffset = location.add(OFFSET);
        lobbyMapSetup.hologram(withOffset);

        this.visualizationManager.registerTask(lobbyMapSetup, new HologramRenderer(plugin, lobbyMapSetup, withOffset).render(new HologramVisualization(plugin, lobbyMapSetup, 28263, 1065, 1534, 1834, 369, 660)));

        setupManager.finishSetup(player, 3);
    }
}
