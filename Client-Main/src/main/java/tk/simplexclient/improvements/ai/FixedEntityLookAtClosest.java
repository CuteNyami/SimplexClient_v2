package tk.simplexclient.improvements.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIWatchClosest;

public class FixedEntityLookAtClosest extends EntityAIWatchClosest {
    public FixedEntityLookAtClosest(EntityLiving entity, Class clazz, float distance) {
        super(entity, clazz, distance);
    }

    public FixedEntityLookAtClosest(EntityLiving entity, Class clazz, float distance, float updateRndTrigger) {
        super(entity, clazz, distance, updateRndTrigger);
    }
}
