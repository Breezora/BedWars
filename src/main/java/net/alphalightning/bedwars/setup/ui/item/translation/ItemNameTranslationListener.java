package net.alphalightning.bedwars.setup.ui.item.translation;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import net.alphalightning.bedwars.BedWarsPlugin;
import org.bukkit.entity.Player;

public final class ItemNameTranslationListener implements PacketListener {

    private final BedWarsPlugin plugin;
    private int count = 0; // Debug

    public ItemNameTranslationListener(BedWarsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPacketSend(PacketSendEvent event) {
        Player player = event.getPlayer();

        if (event.getPacketType() != PacketType.Play.Server.SET_SLOT) {
            return;
        }

        plugin.getLogger().info("Packet gesendet: " + ++count);
    }
}
