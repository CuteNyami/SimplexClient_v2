package tk.simplexclient.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import tk.simplexclient.event.Event;

@AllArgsConstructor
public class EntitySpawnEvent extends Event {

    public final Entity entity;

    public final World world;

}
