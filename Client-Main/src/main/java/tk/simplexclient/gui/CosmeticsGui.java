package tk.simplexclient.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Mouse;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.cosmetics.Cosmetic;
import tk.simplexclient.ui.buttons.DefaultButton;

import java.io.IOException;

public class CosmeticsGui extends GuiScreen {

    @Override
    public void initGui() {
        int x = 5;
        int y = 5;
        for (Cosmetic cosmetic : SimplexClient.getInstance().getCosmeticsHandler().getAllCosmetics()) {
            this.buttonList.add(new DefaultButton(x, y, cosmetic.getName()) {
                @Override
                public void onClick() {
                    if (cosmetic.enabled) {
                        SimplexClient.getInstance().getCosmeticsHandler().disableCosmetic(cosmetic);
                    } else {
                        SimplexClient.getInstance().getCosmeticsHandler().enableCosmetic(cosmetic);
                    }
                }
            });
            if (y >= (height - 15)) {
                x += 205;
                y = 5;
            } else {
                y += 25;
            }
        }
    }

    float rotate = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        float targetHeight = (height / 5.0F) / 1.8F;
        drawEntityOnScreen(
                (width) - 40,
                (int) ((height / 2) + (mc.thePlayer.height * targetHeight)) - (int) (mc.thePlayer.height * targetHeight) / 2,
                targetHeight,
                rotate,
                180,
                mc.thePlayer);

        if (Mouse.isButtonDown(0)) {
            float mouseOffset = mouseX - (width - 40);
                rotate = mouseOffset *2;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiButton button : this.buttonList) {
            if (button instanceof DefaultButton) {
                DefaultButton defaultButton = (DefaultButton) button;
                if (defaultButton.mousePressed(this.mc, mouseX, mouseY)) {
                    defaultButton.onClick();
                }
            }
        }
       super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public static void drawEntityOnScreen(int posX, int posY, float scale, float yawRotate, float pitchRotate, EntityLivingBase ent) {
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.translate(posX, posY, 50.0F);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(pitchRotate, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(yawRotate, 0.0F, 1.0F, 0.0F);
        float f2 = ent.renderYawOffset;
        float f3 = ent.rotationYaw;
        float f4 = ent.rotationPitch;
        float f5 = ent.prevRotationYawHead;
        float f6 = ent.rotationYawHead;
        RenderHelper.enableStandardItemLighting();
        ent.renderYawOffset = (float) Math.atan(yawRotate / 40.0F);
        ent.rotationYaw = (float) Math.atan(yawRotate / 40.0F);
        ent.rotationPitch = -((float) Math.atan(0 / 40.0F)) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        try {
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.setPlayerViewY(180.0F);
            rendermanager.setRenderShadow(false);
            rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, true);
            rendermanager.setRenderShadow(true);
        } finally {
            ent.renderYawOffset = f2;
            ent.rotationYaw = f3;
            ent.rotationPitch = f4;
            ent.prevRotationYawHead = f5;
            ent.rotationYawHead = f6;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.translate(0.0F, 0.0F, 20.0F);
        }
    }

}