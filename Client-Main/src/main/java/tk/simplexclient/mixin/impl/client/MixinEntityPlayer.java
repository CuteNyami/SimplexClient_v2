package tk.simplexclient.mixin.impl.client;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.event.impl.EntityAttackEvent;

@Mixin(targets = "net.minecraft.entity.player.EntityPlayer")
public class MixinEntityPlayer {

    private EntityAttackEvent entityAttackEvent;

    @Inject(method = "attackTargetEntityWithCurrentItem", at = @At("HEAD"))
    public void attackTargetEntityWithCurrentItem(Entity targetEntity, CallbackInfo ci) {
        if (targetEntity.canAttackWithItem()) {
            entityAttackEvent = new EntityAttackEvent(targetEntity);
            entityAttackEvent.call();
        }
    }

}
