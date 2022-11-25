package tk.simplexclient.event.impl;

import lombok.AllArgsConstructor;
import net.minecraft.item.ItemStack;
import tk.simplexclient.event.Event;

@AllArgsConstructor
public class TransformFirstPersonItemEvent extends Event {

    public final ItemStack itemToRender;
    public final float equipProgress;
    public final float swingProgress;

}
