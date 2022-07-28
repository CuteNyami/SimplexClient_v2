package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;

import java.awt.*;
import java.text.DecimalFormat;

@Mixin(RenderTNTPrimed.class)
public class MixinRenderTNTPrimed extends Render<EntityTNTPrimed> {

    private final DecimalFormat timeFormatter = new DecimalFormat("0.0");

    public MixinRenderTNTPrimed(RenderManager renderManager) {
        super(renderManager);
    }

    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityTNTPrimed;DDDFF)V", at = @At("HEAD"))
    public void doRender(EntityTNTPrimed entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (SimplexClient.getInstance().getModuleManager().tntTimer.isEnabled()) {
            int fuseTimer = entity.fuse;
            double distance = entity.getDistanceSqToEntity(getRenderManager().livingPlayer);
            if (fuseTimer < 1 || distance > 4096) return;

            String time = this.timeFormatter.format((fuseTimer - partialTicks) / 20);
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();

            GlStateManager.pushMatrix();
            GL11.glTranslatef((float) (x + 0.0), (float) (y + entity.height + 0.5), (float) z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
            GlStateManager.translate(0.0F, 9.374999F, 0.0F);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            int i = fontrenderer.getStringWidth(time) / 2;
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            vertexbuffer.pos(-i - 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            vertexbuffer.pos(-i - 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            vertexbuffer.pos(i + 1, 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            vertexbuffer.pos(i + 1, -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);
            float green = Math.min(fuseTimer / 60F, 1f);
            Color color = new Color(1f - green, green, 0);
            fontrenderer.drawStringWithShadow(time, (float) -fontrenderer.getStringWidth(time) / 2, 0, color.getRGB());
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public ResourceLocation getEntityTexture(EntityTNTPrimed entityTNTPrimed) {
        return TextureMap.locationBlocksTexture;
    }
}
