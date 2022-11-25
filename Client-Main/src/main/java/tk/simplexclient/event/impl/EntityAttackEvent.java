package tk.simplexclient.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.entity.Entity;
import tk.simplexclient.event.Event;

@AllArgsConstructor
public class EntityAttackEvent extends Event {

    public final Entity target;

}
