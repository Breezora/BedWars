package net.alphalightning.bedwars.game.state.states;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.DyedItemColor;
import io.papermc.paper.datacomponent.item.Unbreakable;
import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.game.map.MapManager;
import net.alphalightning.bedwars.game.state.AbstractGameState;
import net.alphalightning.bedwars.game.state.GameStateContext;
import net.alphalightning.bedwars.game.team.Team;
import net.alphalightning.bedwars.game.team.allocator.DynamicTeamAllocator;
import net.alphalightning.bedwars.game.team.allocator.TeamAllocator;
import net.alphalightning.bedwars.translation.NamedTranslationArgument;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemBuilder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InGameState extends AbstractGameState implements Listener {

    private final BedWarsPlugin plugin;
    private final MapManager mapManager;
    private List<Team> teams;

    public InGameState(@NotNull BedWarsPlugin plugin, GameStateContext context, MapManager mapManager) {
        super(context);
        this.plugin = plugin;
        this.mapManager = mapManager;
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }


    @Override
    public void start() {
        TranslatableComponent component = Component.translatable("state.ingame.start");

        Bukkit.broadcast(component);
        context.logger().info(component);

        allocateTeams();
        placeBeds();
        createVillagers();
        teleportPlayers();
        preparePlayers();
    }

    @Override
    public void stop() {
        context.logger().info(Component.translatable("state.ingame.stop"));
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (isArmor(item)) {
            event.setCancelled(true);
            return;
        }
        if (item.getType() == Material.WOODEN_SWORD) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> event.getPlayer().spigot().respawn(), 1L);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        for (Team team : teams) {
            Location spawn = team.spawnpoint();

            if (team.players().contains(player)) {
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.teleport(spawn), 1L);
            }
        }
    }

    @EventHandler
    public void onPlayerBed(PlayerBedEnterEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block clicked = event.getClickedBlock();
        if (clicked == null) {
            return;
        }

        if (clicked.getType().name().endsWith("_BED")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!(context.currentState() instanceof InGameState)) return;

        List<MetadataValue> metadataValues = block.getMetadata("team");
        if (metadataValues.isEmpty()) return;

        FixedMetadataValue value = (FixedMetadataValue) metadataValues.getFirst();
        if (value.getOwningPlugin() == null) return;
        if (!value.getOwningPlugin().equals(plugin)) return;

        Team destroyedTeam = (Team) value.value();
        Team destroyerTeam = findTeamByPlayer(player);

        if (destroyedTeam == null) {
            throw new IllegalStateException("Block " + block.getLocation() + " has no team metadata");
        }

        if (destroyerTeam == null) {
            event.setCancelled(true);
            throw new IllegalStateException("Player " + player.getName() + " is not on a team");
        }

        if (destroyerTeam.equals(destroyedTeam)) {
            player.sendMessage(Component.translatable("state.ingame.break.own"));
            event.setCancelled(true);
            return;
        }

        event.setDropItems(false);

        deleteBed(destroyedTeam);
        sendDestruction(player, destroyerTeam, destroyedTeam);
    }

    private void deleteBed(Team team) {
        Location bottom = team.bedBottomHalf();
        Location top = team.bedTopHalf();

        Block bottomBlock = bottom.getBlock();
        Block topBlock = top.getBlock();

        removeMetadata(topBlock);
        removeMetadata(bottomBlock);
    }

    private void removeMetadata(Block block) {
        List<MetadataValue> metadataValues = block.getMetadata("team");
        if (metadataValues.isEmpty()) return;

        FixedMetadataValue value = (FixedMetadataValue) metadataValues.getFirst();
        if (value.getOwningPlugin() == null) return;
        if (!value.getOwningPlugin().equals(plugin)) return;

        block.removeMetadata("team", plugin);
    }

    private void allocateTeams() {
        TeamAllocator teamAllocator = new DynamicTeamAllocator(mapManager.selected().teams());
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        int maxTeams = mapManager.selected().teams().size();
        int teamSize = mapManager.selected().teamSize();

        teams = teamAllocator.allocateTeams(players, maxTeams, teamSize);
    }

    private Team findTeamByPlayer(Player player) {
        for (Team team : teams) {
            if (!team.players().contains(player)) continue;
            return team;
        }
        return null;
    }

    private void teleportPlayers() {
        for (Team team : teams) {
            Location spawn = team.spawnpoint();

            for (Player player : team.players()) {
                player.teleport(spawn);
            }
        }
    }

    private void preparePlayers() {
        for (Team team : teams) {
            int color = team.color();

            ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();
            ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();
            ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();
            ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .set(DataComponentTypes.DYED_COLOR, DyedItemColor.dyedItemColor(Color.fromRGB(color), false))
                    .build();

            ItemStack woodSword = new ItemBuilder(Material.WOODEN_SWORD)
                    .set(DataComponentTypes.UNBREAKABLE, Unbreakable.unbreakable())
                    .build();

            for (Player player : team.players()) {
                player.setGameMode(GameMode.SURVIVAL);

                player.getInventory().setArmorContents(new ItemStack[]{boots, leggings, chestplate, helmet});
                player.getInventory().setItem(0, woodSword);
            }
        }
    }

    private boolean isArmor(ItemStack item) {
        return switch (item.getType()) {
            case LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS, LEATHER_BOOTS, DIAMOND_BOOTS, DIAMOND_LEGGINGS,
                 CHAINMAIL_BOOTS, CHAINMAIL_LEGGINGS, IRON_BOOTS, IRON_LEGGINGS -> true;
            default -> false;
        };
    }

    private void placeBeds() {
        for (Team team : teams) {
            Location bottomHalfLocation = team.bedBottomHalf();
            Location topHalfLocation = team.bedTopHalf();
            BlockFace blockFace = getBedFacing(bottomHalfLocation, topHalfLocation);
            Material bedMaterial = switch (team.name().toLowerCase()) {
                case "white" -> Material.WHITE_BED;
                case "light_gray" -> Material.LIGHT_GRAY_BED;
                case "dark_gray" -> Material.GRAY_BED;
                case "black" -> Material.BLACK_BED;
                case "brown" -> Material.BROWN_BED;
                case "red" -> Material.RED_BED;
                case "orange" -> Material.ORANGE_BED;
                case "yellow" -> Material.YELLOW_BED;
                case "light_green" -> Material.LIME_BED;
                case "green" -> Material.GREEN_BED;
                case "cyan" -> Material.CYAN_BED;
                case "light_blue" -> Material.LIGHT_BLUE_BED;
                case "blue" -> Material.BLUE_BED;
                case "purple" -> Material.PURPLE_BED;
                case "magenta" -> Material.MAGENTA_BED;
                case "pink" -> Material.PINK_BED;
                default -> throw new IllegalArgumentException("Unknown team name: " + team.name());
            };

            createBed(topHalfLocation, bedMaterial, Bed.Part.HEAD, blockFace, team);
            createBed(bottomHalfLocation, bedMaterial, Bed.Part.FOOT, blockFace, team);
        }
    }

    private void createBed(Location location, Material material, Bed.Part part, BlockFace blockFace, Team team) {
        Block block = location.getBlock();
        block.setType(material);

        Bed bed = (Bed) block.getBlockData();
        bed.setPart(part);
        bed.setFacing(blockFace);
        block.setBlockData(bed);
        block.setMetadata("team", new FixedMetadataValue(plugin, team));
    }

    private void sendDestruction(Player breaker, Team destroyer, Team destroyed) {
        Component coloredPlayerName = Component.text(breaker.getName()).style(Style.style().color(TextColor.color(destroyer.color())).build());

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5F, 1.0F);
            player.sendMessage(Component.empty());
            player.sendMessage(Component.translatable("state.ingame.destroy.bed",
                    NamedTranslationArgument.component("team", Component.translatable("team." + destroyed.name().replace("_", "-"))),
                    NamedTranslationArgument.component("player", coloredPlayerName))
            );
            player.sendMessage(Component.empty());
        }
        //Send title to team players of the destroyed bed
        for (Team team : teams) {
            if (!destroyed.equals(team)) continue;

            for (Player player : team.players()) {
                Title.Times times = Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(2), Duration.ofSeconds(1));
                Title title = Title.title(Component.translatable("state.ingame.destroy.own"), Component.empty(), times);
                player.showTitle(title);
            }
        }
    }

    private void createVillagers() {

    }

    private BlockFace getBedFacing(@NotNull Location bottom, @NotNull Location top) {
        int dx = top.getBlockX() - bottom.getBlockX();
        int dz = top.getBlockZ() - bottom.getBlockZ();

        if (dx == 1) return BlockFace.EAST;
        if (dx == -1) return BlockFace.WEST;
        if (dz == 1) return BlockFace.SOUTH;
        if (dz == -1) return BlockFace.NORTH;

        throw new IllegalArgumentException("Invalid bed orientation: locations are not adjacent in a cardinal direction");
    }

}
