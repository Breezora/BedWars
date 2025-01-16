package net.alphalightning.bedwars.setup.ui.item;

import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.AbstractGui;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.AbstractBoundItem;

public abstract class AbstractNormalGuiBoundItem extends AbstractBoundItem {

    @Override
    public void bind(@NotNull Gui gui) {
        if (!(gui instanceof AbstractGui)) {
            throw new IllegalStateException("The item can only be used inside a normal gui");
        }
        super.bind(gui);
    }

    @Override
    public @NotNull AbstractGui getGui() {
        return (AbstractGui) super.getGui();
    }
}
