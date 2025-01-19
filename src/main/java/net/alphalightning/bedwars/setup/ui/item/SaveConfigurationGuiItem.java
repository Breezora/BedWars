package net.alphalightning.bedwars.setup.ui.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.ui.SelectTeamsGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractBoundItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

public class SaveConfigurationGuiItem extends AbstractBoundItem {

    private final GameMapSetup setup;

    public SaveConfigurationGuiItem(GameMapSetup setup) {
        this.setup = setup;
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
        int stage = setup.stage();

        if (stage == 1) { // Team selection stage
            int size = SelectTeamsGui.selectedTeams().size();
            if (size < 2) {
                player.sendMessage(Component.translatable("mapsetup.error.invalid-team-configuration", Component.text(size)));
                Feedback.error(player);
                return;
            }
            startNextStage(player, 1);

        } else if (stage == 2) { // Item spawner configuration stage
            super.getGui().findAllWindows().forEach(window -> window.setCloseable(true)); // Make gui closeable to be able to close it
            startNextStage(player, 2);
        }
    }

    private void startNextStage(Player player, int current) {
        if (current == 1) {
            setup.startStage(2);

        } else if (current == 2) {
            player.closeInventory();
        }
        Feedback.success(player);
    }

}
