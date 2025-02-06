package net.alphalightning.bedwars.setup.map.stages.gamemap;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.map.MapSetup;
import net.alphalightning.bedwars.setup.map.jackson.Team;
import net.alphalightning.bedwars.setup.map.stages.LocationConfiguration;
import net.alphalightning.bedwars.setup.map.stages.Stage;
import net.alphalightning.bedwars.setup.map.stages.TeamConfiguration;
import net.alphalightning.bedwars.setup.visual.impl.FakeBlockRenderer;
import net.alphalightning.bedwars.setup.visual.impl.FakeBlockVisualisation;
import net.alphalightning.bedwars.setup.visual.impl.MultiBlockRenderer;
import net.alphalightning.bedwars.setup.visual.impl.MultiBlockVisualisation;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import org.bukkit.Location;
import org.bukkit.Material;
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
        if (!(setup instanceof GameMapSetup)) {
            return;
        }

        // Bed configuration is not completed

        updateBed(location.add(OFFSET));

        if (phase < count) {
            startPhase(++phase);
            return;
        }

        // Configuration is completed

        player.sendMessage(Component.translatable("mapsetup.stage.14.success"));
        setupManager.finishSetup(player, 15);
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

        new MultiBlockRenderer(plugin, List.of(topHalf.getBlock(), bottom.getBlock())).render(new MultiBlockVisualisation(team.color()));
        new FakeBlockRenderer(plugin, topHalf).render(new FakeBlockVisualisation(coloredBed())); // "BED" is top half

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

    private Material coloredBed() {
        return switch (team.color()) { // Colors are default hex encoded int values
            case 0xffffff -> Material.WHITE_BED;
            case 0xaaaaaa -> Material.LIGHT_GRAY_BED;
            case 0x555555 -> Material.GRAY_BED;
            case 0x000000 -> Material.BLACK_BED;
            case 0x783d0c -> Material.BROWN_BED;
            case 0xff5555 -> Material.RED_BED;
            case 0xff8800 -> Material.ORANGE_BED;
            case 0xffff55 -> Material.YELLOW_BED;
            case 0x55ff55 -> Material.LIME_BED;
            case 0x00aa00 -> Material.GREEN_BED;
            case 0x00aaaa -> Material.CYAN_BED;
            case 0xa3e5ff -> Material.LIGHT_BLUE_BED;
            case 0x5555ff -> Material.BLUE_BED;
            case 0xaa00aa -> Material.PURPLE_BED;
            case 0xff24fb -> Material.MAGENTA_BED;
            case 0xff6ef8 -> Material.PINK_BED;
            default -> Material.AIR;
        };
    }
}
