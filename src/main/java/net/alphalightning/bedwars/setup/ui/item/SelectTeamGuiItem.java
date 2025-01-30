package net.alphalightning.bedwars.setup.ui.item;

import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.alphalightning.bedwars.setup.ui.SelectTeamsGui;
import net.alphalightning.bedwars.translation.CustomGlobalTranslator;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.Arrays;

public class SelectTeamGuiItem extends AbstractItem {

    private final GameMapSetup setup;
    private final GameMapConfigurationOverviewGui overviewGui;
    private final int stage;

    public SelectTeamGuiItem(GameMapSetup setup, GameMapConfigurationOverviewGui overviewGui) {
        this.setup = setup;
        this.overviewGui = overviewGui;
        this.stage = setup.stage();
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable(stage == 1 ?
                "mapsetup.gui.overview.select-teams.name" :
                "mapsetup.gui.overview.select-teams.name.locked"
        );
        Component lore = Component.translatable("mapsetup.gui.overview.select-teams.lore");

        return new ItemBuilder(Material.RED_BED)
                .setName(CustomGlobalTranslator.render(display, viewer.locale()))
                .setLore(Arrays.asList(
                        Component.empty(),
                        CustomGlobalTranslator.render(lore, viewer.locale()),
                        Component.empty()
                ));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        if (stage != 1) {
            Feedback.error(player);
            return;
        }
        new SelectTeamsGui(player, setup, overviewGui).showGui();
    }
}
