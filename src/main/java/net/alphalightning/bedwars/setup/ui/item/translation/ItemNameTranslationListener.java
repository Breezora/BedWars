package net.alphalightning.bedwars.setup.ui.item.translation;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.item.type.ItemTypes;
import com.github.retrooper.packetevents.protocol.nbt.NBTCompound;
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
            return;
        }

        for (ItemStack item : items) {
            if (!item.is(ItemTypes.RED_BED)) {
                continue;
            }

            changeItemName(item);
            plugin.getLogger().info("Updated packet");
        }
    }

    /*
     Optional[TextComponentImpl{content="Test", style=StyleImpl{obfuscated=false, bold=false, strikethrough=false, underlined=false, italic=false, color=NamedTextColor{name="white", value="#ffffff"}, clickEvent=null, hoverEvent=null, insertion=null, font=null}, children=[]}], StaticComponentType[minecraft:custom_data]=Optional[Compound{{PublicBukkitValues=Compound{{bedwars:slot=Byte(12)}}}}]}]
     */

    private void changeItemName(ItemStack item) {
        NBTCompound nbt = getOrCreateNBTCompound(item.getNBT());
        NBTCompound display = getOrCreateNBTCompound(nbt.getCompoundTagOrNull("minecraft:custom_name"));

        plugin.getLogger().info("Item: " + item);
    }

    private NBTCompound getOrCreateNBTCompound(NBTCompound nbt) {
        return nbt != null ? nbt : new NBTCompound();
    }
}
