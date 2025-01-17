package net.alphalightning.bedwars.setup.ui.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.alphalightning.bedwars.setup.ui.SelectTeamsGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractBoundItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

public class SaveConfigurationGuiItem extends AbstractBoundItem {

    private final NamespacedKey key = new NamespacedKey("bedwars", "stage");
    private final PersistentDataContainer container;

    public SaveConfigurationGuiItem(PersistentDataContainer container) {
        this.container = container;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("mapsetup.gui.save-configuration");

        return new ItemBuilder(Material.GREEN_BUNDLE)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .unset(DataComponentTypes.BUNDLE_CONTENTS); // Disable display of bundles' content
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        int stage = container.getOrDefault(key, PersistentDataType.INTEGER, 0);

        if (stage == 1) { // Team selection stage
            int size = SelectTeamsGui.selectedTeams().size();
            if (size < 2) {
                player.sendMessage(Component.translatable("mapsetup.error.invalid-team-configuration", Component.text(size)));
                Feedback.error(player);
                return;
            }
            startNextStage(player, 1);


        } else if (stage == 2) { // Item spawner configuration stage
            startNextStage(player, 2);
        }
        //TODO: Setup nehmen und neue stage starten
    }

    private void startNextStage(Player player, int current) {
        if (current == 1) {
            new GameMapConfigurationOverviewGui(player).showGui();

        } else if (current == 2) {
            player.closeInventory();
        }
        Feedback.success(player);
        container.set(key, PersistentDataType.INTEGER, current + 1);
    }

}
