package tk.simplexclient.mixin.impl.client;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityLookHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import tk.simplexclient.access.AccessEntityLookHelper;

@Mixin(EntityLookHelper.class)
public abstract class MixinEntityLookHelper implements AccessEntityLookHelper {

    @Override
    @Accessor
    public abstract EntityLiving getEntity();

    @Override
    @Accessor
    public abstract float getDeltaLookPitch();

    @Override
    @Accessor
    public abstract float getDeltaLookYaw();

    @Override
    @Accessor("isLooking")
    public abstract boolean isLooking();

    @Override
    @Accessor("isLooking")
    public abstract void setLooking(boolean looking);
}
