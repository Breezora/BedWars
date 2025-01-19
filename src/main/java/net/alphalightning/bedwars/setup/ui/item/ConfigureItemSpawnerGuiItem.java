package net.alphalightning.bedwars.setup.ui.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.ui.ConfigureItemSpawnerGui;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

import java.util.Arrays;

public class ConfigureItemSpawnerGuiItem extends AbstractItem {

    private final GameMapSetup setup;
    private final GameMapConfigurationOverviewGui gui;
    private final int stage;

    public ConfigureItemSpawnerGuiItem(GameMapSetup setup, GameMapConfigurationOverviewGui gui) {
        this.setup = setup;
        this.gui = gui;
        this.stage = setup.stage();
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable(stage == 2 ?  // Stage might be different
                "mapsetup.gui.overview.select-spawner.name" :
                "mapsetup.gui.overview.select-spawner.name.locked"
        );
        Component lore = Component.translatable("mapsetup.gui.overview.select-spawner.lore");

        return new ItemBuilder(Material.TRIAL_SPAWNER)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(Arrays.asList(
                        Component.empty(),
                        GlobalTranslator.render(lore, viewer.locale()),
                        Component.empty()
                ))
                .set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        if (stage != 2) {
            Feedback.error(player);
            return;
        }
        new ConfigureItemSpawnerGui(player, setup, gui).showGui();
    }
}
