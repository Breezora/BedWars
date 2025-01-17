package net.alphalightning.bedwars.setup.ui.item;

import net.alphalightning.bedwars.setup.ui.Feedback;
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

public class ReturnToOverviewGuiItem extends AbstractItem {

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("mapsetup.gui.configure.back");

        return new ItemBuilder(Material.ARROW).setName(GlobalTranslator.render(display, viewer.locale()));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        Feedback.back(player);
        new GameMapConfigurationOverviewGui(player).showGui();
    }

}
