package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiSlot.class)
public abstract class MixinGuiSlot {

    @Shadow public boolean field_178041_q;

    @Shadow public int mouseX;

    @Shadow public int mouseY;

    @Shadow public abstract void drawBackground();

    @Shadow public abstract int getScrollBarX();

    @Shadow public abstract void bindAmountScrolled();

    @Shadow @Final public Minecraft mc;

    @Shadow public int left;

    @Shadow public int top;

    @Shadow public int width;

    @Shadow public abstract int getListWidth();

    @Shadow public float amountScrolled;

    @Shadow public boolean hasListHeader;

    @Shadow public abstract void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_);

    @Shadow public abstract void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn);

    @Shadow public abstract void overlayBackground(int startY, int endY, int startAlpha, int endAlpha);

    @Shadow public int bottom;

    @Shadow public int height;

    @Shadow public int right;

    @Shadow public abstract int func_148135_f();

    @Shadow public abstract int getContentHeight();

    @Shadow public abstract void func_148142_b(int p_148142_1_, int p_148142_2_);

    /**
     * @author Nyami
     * @reason idk
     */
    @Overwrite
    public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
        if (this.field_178041_q)
        {
            this.mouseX = mouseXIn;
            this.mouseY = mouseYIn;
            //this.drawBackground();
            this.mc.getTextureManager().bindTexture(new ResourceLocation("simplex/menu/main/bg.png"));
            Gui.drawModalRectWithCustomSizedTexture(-21 / 90, -1 / 90, 0.0f, 0.0f, this.width + 20, this.height + 20, (float)(this.width + 21), (float)(this.height + 20));
            int i = this.getScrollBarX();
            int j = i + 6;
            this.bindAmountScrolled();
            /*
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            float f = 32.0F;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)this.left, (double)this.bottom, 0.0D).tex((double)((float)this.left / f), (double)((float)(this.bottom + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)this.bottom, 0.0D).tex((double)((float)this.right / f), (double)((float)(this.bottom + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)this.top, 0.0D).tex((double)((float)this.right / f), (double)((float)(this.top + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
            worldrenderer.pos((double)this.left, (double)this.top, 0.0D).tex((double)((float)this.left / f), (double)((float)(this.top + (int)this.amountScrolled) / f)).color(32, 32, 32, 255).endVertex();
            tessellator.draw();

             */
            int k = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            int l = this.top + 4 - (int)this.amountScrolled;

            if (this.hasListHeader)
            {
               // this.drawListHeader(k, l, tessellator);
            }

            this.drawSelectionBox(k, l, mouseXIn, mouseYIn);
            GlStateManager.disableDepth();
            int i1 = 4;
            //this.overlayBackground(0, this.top, 255, 255);
            //this.overlayBackground(this.bottom, this.height, 255, 255);
            /*
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
            GlStateManager.disableAlpha();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)this.left, (double)(this.top + i1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
            worldrenderer.pos((double)this.right, (double)(this.top + i1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
            worldrenderer.pos((double)this.right, (double)this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)this.left, (double)this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            tessellator.draw();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldrenderer.pos((double)this.left, (double)this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            worldrenderer.pos((double)this.right, (double)(this.bottom - i1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
            worldrenderer.pos((double)this.left, (double)(this.bottom - i1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
            tessellator.draw();
            int j1 = this.func_148135_f();

            if (j1 > 0)
            {
                int k1 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
                k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
                int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;

                if (l1 < this.top)
                {
                    l1 = this.top;
                }

                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos((double)i, (double)this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos((double)j, (double)this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos((double)j, (double)this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                worldrenderer.pos((double)i, (double)this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                tessellator.draw();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos((double)i, (double)(l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                worldrenderer.pos((double)j, (double)(l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                worldrenderer.pos((double)j, (double)l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                worldrenderer.pos((double)i, (double)l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                tessellator.draw();
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldrenderer.pos((double)i, (double)(l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                worldrenderer.pos((double)(j - 1), (double)(l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                worldrenderer.pos((double)(j - 1), (double)l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                worldrenderer.pos((double)i, (double)l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                tessellator.draw();
            }

             */

            this.func_148142_b(mouseXIn, mouseYIn);
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
        }
    }

}
