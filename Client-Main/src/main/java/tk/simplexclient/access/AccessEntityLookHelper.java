package tk.simplexclient.access;

import net.minecraft.entity.EntityLiving;

public interface AccessEntityLookHelper {

    EntityLiving getEntity();

    float getDeltaLookPitch();

    float getDeltaLookYaw();

    boolean isLooking();

    void setLooking(boolean looking);

    float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_);

}
