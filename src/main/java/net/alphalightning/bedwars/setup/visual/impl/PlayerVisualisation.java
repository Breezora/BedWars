package net.alphalightning.bedwars.setup.visual.impl;

import net.alphalightning.bedwars.setup.visual.Visualisation;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class PlayerVisualisation implements Visualisation<NPC> {

    private final Location location;

    public PlayerVisualisation(Location location) {
        this.location = location;
    }

    @Override
    public void show(@NotNull NPC npc) {
        npc.spawn(location);

        Entity entity = npc.getEntity();
        entity.setInvulnerable(false); // Make this true when everything works correctly and the npc must despawn
        entity.setNoPhysics(false);
        entity.setGravity(true);

        entity.setGlowing(true);
    }
}
