package tk.simplexclient.mixin.impl.client;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.entity.EntityAITasksEntry;
import tk.simplexclient.improvements.AIImprovements;

@Mixin(EntityAITasks.class)
public abstract class MixinEntityAITasks {

    @Inject(method = "addTask", at = @At("TAIL"))
    public void addTask(int priority, EntityAIBase task, CallbackInfo ci) {
        AIImprovements.taskEntries.add(new EntityAITasksEntry(priority, task));
    }

}
