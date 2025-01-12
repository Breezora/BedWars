package net.alphalightning.bedwars.setup.ui.item.translation;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import net.alphalightning.bedwars.BedWarsPlugin;

import java.util.List;

public final class ItemNameTranslationListener implements PacketListener {

    private final BedWarsPlugin plugin;

    public ItemNameTranslationListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.WINDOW_ITEMS) {
            return;
        }

        WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(event);
        List<ItemStack> items = packet.getItems();

        if (items.isEmpty()) {
            plugin.getLogger().info("No items present in pac-ket");
            return;
        }

        for (ItemStack item : items) {
            if (!item.is(ItemTypes.RED_BED)) {
                continue;
            }
            plugin.getLogger().info("Found red bed in packet");
        }

        plugin.getLogger().info("Updated packet");
    }
}
