package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.event.Event;
import tk.simplexclient.event.impl.TransformFirstPersonItemEvent;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Shadow public ItemStack itemToRender;

    @Inject(method = "transformFirstPersonItem", at = @At("HEAD"))
    public void transformFirstPersonItem(float equipProgress, float swingProgress, CallbackInfo ci) {
        Event event = new TransformFirstPersonItemEvent(itemToRender, equipProgress, swingProgress);
        event.call();
    }

}
