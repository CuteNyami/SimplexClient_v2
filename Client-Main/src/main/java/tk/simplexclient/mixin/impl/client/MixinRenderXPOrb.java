package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tk.simplexclient.event.impl.XPOrbOverlayEvent;

@Mixin(RenderXPOrb.class)
public class MixinRenderXPOrb extends Render<EntityXPOrb> {

    @Shadow
    @Final
    public static ResourceLocation experienceOrbTextures;

    public MixinRenderXPOrb(RenderManager renderManager) {
        super(renderManager);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void doRender(EntityXPOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        this.bindEntityTexture(entity);
        int i = entity.getTextureByXP();
        float f = (float) (i % 4 * 16) / 64.0F;
        float f1 = (float) (i % 4 * 16 + 16) / 64.0F;
        float f2 = (float) (i / 4 * 16) / 64.0F;
        float f3 = (float) (i / 4 * 16 + 16) / 64.0F;
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        int j = entity.getBrightnessForRender(partialTicks);
        int k = j % 65536;
        int l = j / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f9 = ((float) entity.xpColor + partialTicks) / 2.0F;

        l = (int) ((MathHelper.sin(f9 + 0.0F) + 1.0F) * 0.5F * 255.0F);
        int j1 = (int) ((MathHelper.sin(f9 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
        GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        float f7 = 0.3F;
        GlStateManager.scale(0.3F, 0.3F, 0.3F);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);

        XPOrbOverlayEvent event = new XPOrbOverlayEvent(63, 255, 12, 128);
        event.call();
        if (!event.isCancelled()) {
            vertexbuffer.pos(0.0F - f5, 0.0F - f6, 0.0D).tex(f, f3).color(event.r, event.g, event.b, event.a).normal(0.0F, 1.0F, 0.0F).endVertex();
            vertexbuffer.pos(f4 - f5, 0.0F - f6, 0.0D).tex(f1, f3).color(event.r, event.g, event.b, event.a).normal(0.0F, 1.0F, 0.0F).endVertex();
            vertexbuffer.pos(f4 - f5, (1.0F - f6), 0.0D).tex(f1, f2).color(event.r, event.g, event.b, event.a).normal(0.0F, 1.0F, 0.0F).endVertex();
            vertexbuffer.pos(0.0F - f5, 1.0F - f6, 0.0D).tex(f, f2).color(event.r, event.g, event.b, event.a).normal(0.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
        }
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    /**
     * @author Mojang
     * @reason
     */
    @Overwrite
    @Override
    public ResourceLocation getEntityTexture(EntityXPOrb entity) {
        return experienceOrbTextures;
    }


}
