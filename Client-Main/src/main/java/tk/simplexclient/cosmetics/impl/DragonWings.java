package tk.simplexclient.cosmetics.impl;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.cosmetics.Cosmetic;
import tk.simplexclient.cosmetics.CosmeticModelBase;

public class DragonWings extends Cosmetic {
    private static final ResourceLocation TEXTURE = new ResourceLocation("cosmetics/wings.png");
    private final DragonWings.WingsModel wingsModel;

    public DragonWings(RenderPlayer player) {
        super(player, "Dragon Wings");
        this.wingsModel = new DragonWings.WingsModel(player);
    }

    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        double z = player.getCurrentArmor(2) != null ? 0.2D : 0.13D;

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0D, 0.15D, z);

        if (player.isSneaking()) {
            GL11.glTranslated(0.0D, 0.15D, z);
        }

        this.renderPlayer.bindTexture(TEXTURE);
        this.wingsModel.render(player, limbSwing, limbSwingAmount, ageInTicks, headPitch, headPitch, scale);
        GlStateManager.popMatrix();
    }

    public static class WingsModel extends CosmeticModelBase {
        private final ModelRenderer wing;
        private final ModelRenderer wingTip;

        public WingsModel(RenderPlayer player) {
            super(player);
            this.setTextureOffset("wing.bone", 0, 0);
            this.setTextureOffset("wing.skin", -10, 8);
            this.setTextureOffset("wingtip.bone", 0, 5);
            this.setTextureOffset("wingtip.skin", -10, 18);
            this.wing = new ModelRenderer(this, "wing");
            this.wing.setTextureSize(30, 30);
            this.wing.setRotationPoint(-2.0F, 0.0F, 0.0F);
            this.wing.addBox("bone", -10.0F, -1.0F, -1.0F, 10, 2, 2);
            this.wing.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            this.wingTip = new ModelRenderer(this, "wingtip");
            this.wingTip.setTextureSize(30, 30);
            this.wingTip.setRotationPoint(-10.0F, 0.0F, 0.0F);
            this.wingTip.addBox("bone", -10.0F, -0.5F, -0.5F, 10, 1, 1);
            this.wingTip.addBox("skin", -10.0F, 0.0F, 0.5F, 10, 0, 10);
            this.wing.addChild(this.wingTip);
        }

        public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
            float f = (float) (System.currentTimeMillis() % 1000L) / 1000.0F * (float) Math.PI * 2.0F;

            this.wing.rotateAngleX = (float) Math.toRadians(-80.0D) - (float) Math.cos(f) * 0.2F;
            this.wing.rotateAngleY = (float) Math.toRadians(30.0D) + (float) Math.sin(f) * 0.4F;
            this.wing.rotateAngleZ = (float) Math.toRadians(20.0D);
            this.wingTip.rotateAngleZ = -((float) (Math.sin(f + 2.0F) + 0.5D)) * 0.75F;

            GlStateManager.pushMatrix();
            GlStateManager.scale(0.6F, 0.6F, 0.6F);
            this.wing.render(0.0625F);
            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();
            GlStateManager.scale(-0.6F, 0.6F, 0.6F);
            this.wing.render(0.0625F);
            GlStateManager.popMatrix();

        }
    }
}