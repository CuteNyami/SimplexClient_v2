package tk.simplexclient.mixin.impl.item;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tk.simplexclient.SimplexClient;

@Mixin(RenderEntityItem.class)
public abstract class MixinRenderEntityItem extends Render<EntityItem> {

    @Shadow
    protected abstract int func_177078_a(ItemStack stack);

    public MixinRenderEntityItem(RenderManager renderManager) {
        super(renderManager);
    }

    /**
     * @author CuteNymai
     */
    @Overwrite
    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
        ItemStack itemstack = itemIn.getEntityItem();
        Item item = itemstack.getItem();
        if (SimplexClient.getInstance().getModuleManager().itemPhysics.isEnabled()) {

            if (item == null) {
                return 0;
            } else {

                boolean flag = p_177077_9_.isGui3d();
                int i = this.func_177078_a(itemstack);
                float f = 0.25F;

                float f1 = -0.125f;
                if (!flag) f1 = -0.175f;

                float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
                GlStateManager.translate((float) p_177077_2_, (float) p_177077_4_ + f1 + 0.25F * f2, (float) p_177077_6_);

                if (flag || this.renderManager.options != null) {
                    float f3 = (((float) itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
                }

                if (!flag) {
                    float f6 = -0.0F * (float) (i - 1) * 0.5F;
                    float f4 = -0.0F * (float) (i - 1) * 0.5F;
                    float f5 = -0.046875F * (float) (i - 1) * 0.5F;

                    if (itemIn.onGround) GlStateManager.rotate(180, 0.0f, 1.0f, 1.0f);

                }

                float speed = 10;
                if (!itemIn.onGround) {
                    float rotAmount = ((float) itemIn.getAge() * speed) % 360;
                    GlStateManager.rotate(rotAmount, 1f, 0f, 1f);
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                return i;
            }
        } else {

            if (item == null) {
                return 0;
            } else {
                boolean flag = p_177077_9_.isGui3d();
                int i = this.func_177078_a(itemstack);
                float f = 0.25F;
                float f1 = MathHelper.sin(((float) itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
                float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
                GlStateManager.translate((float) p_177077_2_, (float) p_177077_4_ + f1 + 0.25F * f2, (float) p_177077_6_);

                if (flag || this.renderManager.options != null) {
                    float f3 = (((float) itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
                    GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
                }

                if (!flag) {
                    float f6 = -0.0F * (float) (i - 1) * 0.5F;
                    float f4 = -0.0F * (float) (i - 1) * 0.5F;
                    float f5 = -0.046875F * (float) (i - 1) * 0.5F;
                    GlStateManager.translate(f6, f4, f5);
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                return i;
            }
        }
    }
}