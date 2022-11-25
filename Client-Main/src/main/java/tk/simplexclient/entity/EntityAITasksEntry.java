package tk.simplexclient.entity;

import net.minecraft.entity.ai.EntityAIBase;

public class EntityAITasksEntry {
    /**
     * The EntityAIBase object.
     */
    public EntityAIBase action;
    /**
     * Priority of the EntityAIBase
     */
    public int priority;

    public EntityAITasksEntry(int priorityIn, EntityAIBase task) {
        this.priority = priorityIn;
        this.action = task;
    }
}
