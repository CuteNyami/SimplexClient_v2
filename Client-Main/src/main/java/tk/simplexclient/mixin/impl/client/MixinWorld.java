package tk.simplexclient.mixin.impl.client;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tk.simplexclient.event.impl.EntitySpawnEvent;

@Mixin(World.class)
public class MixinWorld {

    @Inject(method = "spawnEntityInWorld", at = @At("HEAD"))
    public void spawnEntityInWorld(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        EntitySpawnEvent event = new EntitySpawnEvent(entityIn, entityIn.getEntityWorld());
        if (event.isCancelled()) return;
        //event.call();
    }

}
