package net.alphalightning.bedwars.setup.ui;

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

    public ConfigureItemSpawnerGui(Player owner) {
        this.owner = owner;
        this.gui = createGui();
    }

    private Single createGui() {
        return Window.single().setGui(Gui.normal()
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
        ).setTitle(Component.translatable("mapsetup.gui.configure-spawner.title"));
    }

    public void showGui() {
        gui.open(owner);
    }

}
