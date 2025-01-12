package net.alphalightning.bedwars.setup.ui.item.translation;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
import com.github.retrooper.packetevents.protocol.nbt.NBTString;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerWindowItems;
import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;

public final class ItemNameTranslationListener implements PacketListener {

    private final BedWarsPlugin plugin;

    public ItemNameTranslationListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        Player player = event.getPlayer();

        if (event.getPacketType() != PacketType.Play.Server.WINDOW_ITEMS) {
            return;
        }

        WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems(event);
        ItemStack item = packet.readItemStackModern();

        if (item == null) {
            return;
        }

        NBTCompound nbt = item.getOrCreateTag();
        NBTCompound display = nbt.getCompoundTagOrNull("display");

        if (display == null) {
            display = new NBTCompound();
        }
        display.setTag("name", new NBTString("{\"text\":\"Test\"}"));
        item.setNBT(display);

        packet.writeItemStack(item);

        plugin.getLogger().info("Paket aktualisiert");
    }
}
