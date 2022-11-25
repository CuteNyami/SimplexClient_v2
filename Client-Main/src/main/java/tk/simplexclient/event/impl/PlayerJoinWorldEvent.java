package tk.simplexclient.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import tk.simplexclient.event.Event;

@AllArgsConstructor
public class PlayerJoinWorldEvent extends Event {

    public final World world;

    public final Entity entity;

}
