package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.visual.impl.PlayerRenderer;
import net.alphalightning.bedwars.setup.visual.impl.PlayerVisualisation;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;

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

        final NPC npc = CitizensAPI.createInMemoryNPCRegistry("spectator").createNPC(EntityType.PLAYER, "Team");
        npc.setName(LegacyComponentSerializer.legacySection().serialize(Component.translatable("team.spectator")));

        new PlayerRenderer(plugin, npc).render(new PlayerVisualisation(npc, withOffset));

        player.sendMessage(Component.translatable("mapsetup.stage.6.success"));
        Feedback.success(player);

        gameMapSetup.configureSpectatorSpawn(withOffset);
        gameMapSetup.startStage(7);
    }
}
