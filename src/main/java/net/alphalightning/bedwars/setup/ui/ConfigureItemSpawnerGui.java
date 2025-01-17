package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.feedback.Feedback;
import net.alphalightning.bedwars.setup.ui.item.DiamondSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.EmeraldSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.ReturnToOverviewGuiItem;
import net.alphalightning.bedwars.setup.ui.item.SaveConfigurationGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

import java.util.Collections;

public class ConfigureItemSpawnerGui {

    private final Player owner;
    private final Single gui;

    private static int emeraldSpawnerCount;
    private static int diamondSpawnerCount;

    public ConfigureItemSpawnerGui(Player owner) {
        this.owner = owner;
        this.gui = createGui();

        emeraldSpawnerCount = 0;
        diamondSpawnerCount = 0;
    }

    private Single createGui() {
        return Window.single()
                .setGui(Gui.normal()
                        .setStructure(
                                ". . . . . . . . .",
                                ". . . a . b . . .",
                                "c . . . . . . . d"
                        )
                        .addIngredient('a', new EmeraldSpawnerGuiItem())
                        .addIngredient('b', new DiamondSpawnerGuiItem())
                        .addIngredient('c', new ReturnToOverviewGuiItem())
                        .addIngredient('d', new SaveConfigurationGuiItem(owner.getPersistentDataContainer()))
                        .build()
                )
                .setTitle(Component.translatable("mapsetup.gui.configure-spawner.title"))
                .setCloseHandlers(Collections.singletonList(this::handleClose));
    }

    public void showGui() {
        gui.open(owner);
    }

    public static int emeraldSpawnerCount() {
        return emeraldSpawnerCount;
    }

    public static int diamondSpawnerCount() {
        return diamondSpawnerCount;
    }

    private void handleClose() {
        PersistentDataContainer container = owner.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey("bedwars", "stage");

        int stage = container.getOrDefault(key, PersistentDataType.INTEGER, 0);
        container.set(key, PersistentDataType.INTEGER, stage + 1);
        Feedback.success(owner);
    }
}
