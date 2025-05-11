package net.alphalightning.bedwars.game.entity;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.map.MapManager;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.game.state.states.InGameState;
import net.alphalightning.bedwars.game.team.Team;
import net.alphalightning.bedwars.game.ui.shop.item.ItemShopGui;
import net.alphalightning.bedwars.game.ui.shop.upgrade.UpgradeShopGui;
import net.alphalightning.bedwars.setup.map.jackson.JacksonLocation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class ShopVillager implements Listener {
    
    private final BedWarsPlugin plugin;
    private final MapManager mapManager;
    private final GameStateContext context;
    private final ItemShopGui itemShopGui;
    private final UpgradeShopGui upgradeShopGui;

    public ShopVillager(BedWarsPlugin plugin, MapManager mapManager, GameStateContext context, ItemShopGui itemShopGui, UpgradeShopGui upgradeShopGui) {
        this.plugin = plugin;
        this.mapManager = mapManager;
        this.context = context;
        this.itemShopGui = itemShopGui;
        this.upgradeShopGui = upgradeShopGui;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    
    public void createVillagers() {
        VillagerManager villagerManager = new VillagerManager(plugin); 
        for (JacksonLocation villager: mapManager.selected().shopVillager()) {
            villagerManager.createVillagers(villager, VillagerType.SHOP);
        }
        for (JacksonLocation villager: mapManager.selected().upgradeVillager()) {
            villagerManager.createVillagers(villager, VillagerType.UPGRADE);
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!(context.currentState() instanceof InGameState)) return;
        
        Player player = event.getPlayer();
        
        Entity villager = event.getRightClicked();
        
        if (!(villager instanceof Villager)) return;
        
        List<MetadataValue> metadataValuesvalues = villager.getMetadata("type");
        if (metadataValuesvalues.isEmpty()) return;
        
        FixedMetadataValue value = (FixedMetadataValue) metadataValuesvalues.getFirst();
        
        if (value.getOwningPlugin() == null) return;
        if (!value.getOwningPlugin().equals(plugin)) return;
        
        VillagerType type = (VillagerType) value.value();

        switch (type) {
            case SHOP -> itemShopGui.showGui(player);
            case UPGRADE -> upgradeShopGui.showGui(player);
            case null -> {}
        }
    }
}
