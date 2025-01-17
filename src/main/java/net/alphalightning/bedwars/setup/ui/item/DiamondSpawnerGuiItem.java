package net.alphalightning.bedwars.setup.ui.item;

import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.ui.ConfigureItemSpawnerGui;
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

public class DiamondSpawnerGuiItem extends AbstractItem {

    private static final int MAX_COUNT = 10;
    private int count;

    public DiamondSpawnerGuiItem() {
        this.count = ConfigureItemSpawnerGui.diamondSpawnerCount();
    }

    @Override
    public @NotNull ItemProvider getItemProvider(@NotNull Player viewer) {
        Component display = Component.translatable("mapsetup.gui.configure-spawner.diamond", Component.text(count));
        Component loreAdd = Component.translatable("mapsetup.gui.configure-spawner.add");
        Component loreLower = Component.translatable("mapsetup.gui.configure-spawner.lower");

        return new ItemBuilder(Material.DIAMOND_BLOCK)
                .setName(GlobalTranslator.render(display, viewer.locale()))
                .setLore(Arrays.asList(
                        Component.empty(),
                        GlobalTranslator.render(loreAdd, viewer.locale()),
                        GlobalTranslator.render(loreLower, viewer.locale()),
                        Component.empty()
                ));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull Click click) {
        if (clickType == ClickType.LEFT) {
            if(count + 1 > MAX_COUNT) {
                player.sendMessage(Component.translatable("mapsetup.error.max.diamond-spawner", Component.text(MAX_COUNT)));
                Feedback.error(player);
                return;
            }
            count++;
            notifyWindows(); // We call this here because we only want to trigger an update after an update
            Feedback.more(player);

        } else if (clickType == ClickType.RIGHT) {
            if(count - 1 < 0) {
                player.sendMessage(Component.translatable("mapsetup.error.min-diamond-spawner"));
                Feedback.error(player);
                return;
            }
            count--;
            notifyWindows(); // We call this here because we only want to trigger an update after an update
            Feedback.lower(player);
        }
    }

}
