package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tk.simplexclient.access.AccessEntityRenderer;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements AccessEntityRenderer {

    @Override
    @Invoker("loadShader")
    public abstract void loadShader(ResourceLocation resourceLocationIn);
}
