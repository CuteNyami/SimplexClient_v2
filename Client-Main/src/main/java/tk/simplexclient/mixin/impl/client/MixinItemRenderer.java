package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.Event;
import tk.simplexclient.event.impl.TransformFirstPersonItemEvent;
import tk.simplexclient.gui.mod.GuiTweaks;
import tk.simplexclient.module.ModuleConfig;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    private ModuleConfig config;

    @Shadow
    public ItemStack itemToRender;

    /**
     * @author CuteNyami
     * @reason
     */

    @Overwrite
    public void transformFirstPersonItem(float p_178096_1_, float p_178096_2_) {
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, p_178096_1_ * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(p_178096_2_ * p_178096_2_ * 3.1415927F);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(p_178096_2_) * 3.1415927F);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);

        config = SimplexClient.getInstance().getModuleConfig();

        if (Minecraft.getMinecraft().currentScreen instanceof GuiTweaks) {
            GlStateManager.scale(GuiTweaks.val, GuiTweaks.val, GuiTweaks.val);
        } else {
            GlStateManager.scale(config.getItemSize(), config.getItemSize(), config.getItemSize());
        }
    }

    @Inject(method = "transformFirstPersonItem", at = @At("HEAD"))
    public void transformFirstPersonItem(float equipProgress, float swingProgress, CallbackInfo ci) {
        Event event = new TransformFirstPersonItemEvent(itemToRender, equipProgress, swingProgress);
        event.call();
    }

}
