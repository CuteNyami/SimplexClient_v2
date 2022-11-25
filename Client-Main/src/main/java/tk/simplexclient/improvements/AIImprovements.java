package tk.simplexclient.improvements;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import tk.simplexclient.access.AccessEntityLookHelper;
import tk.simplexclient.entity.EntityAITasksEntry;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.EntitySpawnEvent;

import java.util.Iterator;
import java.util.List;

public class AIImprovements {

    public static boolean REMOVE_LOOK_AI = true;
    public static boolean REMOVE_LOOK_IDLE = true;
    public static boolean REPLACE_LOOK_HELPER = true;

    public static List<EntityAITasksEntry> taskEntries = Lists.newArrayList();

    public void init() {
        FastTrig.init();
    }

    @EventTarget
    public void onEntitySpawn(EntitySpawnEvent event) {
        Entity entity = event.entity;
        if (entity instanceof EntityLiving) {
            EntityLiving living = (EntityLiving) entity;
            if (REMOVE_LOOK_AI || REMOVE_LOOK_IDLE) {
                Iterator<EntityAITasksEntry> iterator = taskEntries.iterator();
                while (iterator.hasNext()) {
                    EntityAITasksEntry obj = iterator.next();
                    if (obj != null) {
                        if (REMOVE_LOOK_AI && obj.action instanceof EntityAIWatchClosest) {
                            iterator.remove();
                        } else if (REMOVE_LOOK_IDLE && obj.action instanceof EntityAILookIdle) {
                            iterator.remove();
                        }
                    }
                }
            }

            if (REPLACE_LOOK_HELPER && (living.getLookHelper() == null || living.getLookHelper().getClass() == EntityLookHelper.class)) {
                EntityLookHelper oldHelper = living.lookHelper;
                living.lookHelper = new FixedEntityLookHelper(living);

                living.lookHelper.setLookPosition(oldHelper.getLookPosX(), oldHelper.getLookPosY(), oldHelper.getLookPosZ(), ((AccessEntityLookHelper) oldHelper).getDeltaLookYaw(), ((AccessEntityLookHelper) oldHelper).getDeltaLookPitch());
                ((AccessEntityLookHelper) living.lookHelper).setLooking(((AccessEntityLookHelper) oldHelper).isLooking());
            }
        }


    }
}
