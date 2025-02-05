package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.BedWarsPlugin;
import net.alphalightning.bedwars.config.Skin;
import net.alphalightning.bedwars.config.Skins;
import net.alphalightning.bedwars.setup.visual.Visualisation;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class PlayerVisualisation implements Visualisation<NPC> {

    private final BedWarsPlugin plugin;
    private final boolean isSpectator;
    private final Location location;

    private final NPC npc;

    public PlayerVisualisation(BedWarsPlugin plugin, NPC npc, boolean isSpectator, Location location) {
        this.plugin = plugin;
        this.isSpectator = isSpectator;
        this.location = location;
        this.npc = npc;
    }

    @Override
    public void show(@NotNull NPC npc) {
        configureNpc();
        npc.spawn(location);

        Entity entity = npc.getEntity();
        entity.setInvulnerable(false); // Make this true when everything works correctly and the npc must despawn
        entity.setNoPhysics(true);
        entity.setGravity(true);
    }

    private void configureNpc() {
        SkinTrait skinTrait = npc.getOrAddTrait(SkinTrait.class);
        Skins skins = plugin.configuration().skins();

        String skinName = isSpectator ? "Spectator" : "Team";
        Skin skin = isSpectator ? skins.spectator() : skins.team();

        skinTrait.setSkinPersistent(skinName, skin.signature(), skin.value());
    }
}
