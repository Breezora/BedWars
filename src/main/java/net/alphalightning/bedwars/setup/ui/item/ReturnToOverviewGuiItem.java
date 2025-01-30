package net.alphalightning.bedwars.setup.ui.item;

import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.ui.GameMapConfigurationOverviewGui;
import net.alphalightning.bedwars.translation.CustomGlobalTranslator;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.AbstractBoundItem;
import xyz.xenondevs.invui.item.Click;
import xyz.xenondevs.invui.item.ItemBuilder;
import xyz.xenondevs.invui.item.ItemProvider;

public class ReturnToOverviewGuiItem extends AbstractBoundItem {

    private final GameMapConfigurationOverviewGui gui;

    public ReturnToOverviewGuiItem(GameMapConfigurationOverviewGui gui) {
        this.gui = gui;
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("mapsetup.gui.configure.back");

        return new ItemBuilder(Material.ARROW).setName(CustomGlobalTranslator.render(display, viewer.locale()));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        super.getGui().closeForAllViewers();
        gui.showGui();

        Feedback.back(player);
    }

}
