package net.alphalightning.bedwars.setup.ui;

import net.alphalightning.bedwars.setup.map.GameMapSetup;
import net.alphalightning.bedwars.setup.ui.item.DiamondSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.EmeraldSpawnerGuiItem;
import net.alphalightning.bedwars.setup.ui.item.ReturnToOverviewGuiItem;
import net.alphalightning.bedwars.setup.ui.item.SaveConfigurationGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;
import xyz.xenondevs.invui.window.Window.Builder.Normal.Single;

public class ConfigureItemSpawnerGui {

    private final Player owner;
    private final Single gui;

    private final GameMapSetup setup;
    private final GameMapConfigurationOverviewGui overviewGui;

    private static int emeraldSpawnerCount;
    private static int diamondSpawnerCount;

    public ConfigureItemSpawnerGui(Player owner, GameMapSetup setup, GameMapConfigurationOverviewGui overviewGui) {
        this.owner = owner;
        this.setup = setup;
        this.overviewGui = overviewGui;
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
                        .addIngredient('a', new EmeraldSpawnerGuiItem(setup))
                        .addIngredient('b', new DiamondSpawnerGuiItem())
                        .addIngredient('c', new ReturnToOverviewGuiItem(overviewGui))
                        .addIngredient('d', new SaveConfigurationGuiItem(setup))
                        .build()
                )
                .setTitle(Component.translatable("mapsetup.gui.configure-spawner.title"))
                .setCloseable(false);
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

}
