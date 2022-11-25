package tk.simplexclient.improvements;

import lombok.SneakyThrows;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.util.MathHelper;
import tk.simplexclient.access.AccessEntityLookHelper;

import java.lang.reflect.Method;

public class FixedEntityLookHelper extends EntityLookHelper {

    private final Class<?> clazz = EntityLookHelper.class;

    public FixedEntityLookHelper(EntityLiving entitylivingIn) {
        super(entitylivingIn);
    }

    @SneakyThrows
    @Override
    public void onUpdateLook() {
        EntityLiving entity = ((AccessEntityLookHelper) this).getEntity();

        entity.rotationPitch = 0.0F;

        if (((AccessEntityLookHelper) this).isLooking()) {
            ((AccessEntityLookHelper) this).setLooking(false);

            double d0 = getLookPosX() - entity.posX;
            double d1 = getLookPosY() - (entity.posY + (double) entity.getEyeHeight());
            double d2 = getLookPosZ() - entity.posZ;
            double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

            float f = (float) (tan(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f1 = (float) (-(tan(d1, d3) * 180.0D / Math.PI));
            entity.rotationPitch = updateRotation(entity, entity.rotationPitch, f1, ((AccessEntityLookHelper) this).getDeltaLookPitch());
            entity.rotationYawHead = updateRotation(entity, entity.rotationYawHead, f, ((AccessEntityLookHelper) this).getDeltaLookYaw());
        } else {
            entity.rotationYawHead = updateRotation(entity, entity.rotationYawHead, entity.renderYawOffset, 10.0F);
        }

        float f2 = MathHelper.wrapAngleTo180_float(entity.rotationYawHead - entity.renderYawOffset);

        if (!entity.getNavigator().noPath()) {
            if (f2 < -75.0F) {
                entity.rotationYawHead = entity.renderYawOffset - 75.0F;
            }

            if (f2 > 75.0F) {
                entity.rotationYawHead = entity.renderYawOffset + 75.0F;
            }
        }
    }

    @SneakyThrows
    public float updateRotation(EntityLiving entity, float p_75652_1_, float p_75652_2_, float p_75652_3_) {
        Method updateRotationMethod = clazz.getDeclaredMethod("updateRotation", float.class, float.class, float.class);
        updateRotationMethod.setAccessible(true);
        return (float) updateRotationMethod.invoke(entity.lookHelper, p_75652_1_, p_75652_2_, p_75652_3_);
    }

    public static float tan(double a, double b) {
        return FastTrig.atan2(a, b);
    }
}
