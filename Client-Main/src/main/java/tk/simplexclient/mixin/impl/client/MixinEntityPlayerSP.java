package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.event.impl.EventUpdate;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    private final EventUpdate event = new EventUpdate();

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void onUpdate(CallbackInfo ci) {
        event.call();
    }

}
