package tk.simplexclient.gui.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gl.GlStateManager;

import java.awt.*;

public class SplashProgress {

    private static final int MAX = 7;
    private static int progress = 0;
    private static String current;
    private static ResourceLocation background;
    private static FontRenderer fr;

    public static void update() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) return;
        draw(Minecraft.getMinecraft().getTextureManager());
    }

    public static void setProgress(int givenProgress, String text) {
        progress = givenProgress;
        current = text;
        update();
    }

    public static void draw(TextureManager textureManager) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int scaleFactor = scaledResolution.getScaleFactor();

        Framebuffer framebuffer = new Framebuffer(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor, true);
        framebuffer.bindFramebuffer(false);

        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        if (background == null) {
            background = new ResourceLocation("simplex/menu/splash.png");
        }

        textureManager.bindTexture(background);

        GlStateManager.resetColor();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        Gui.drawModalRectWithCustomSizedTexture(-21 / 90, -1 / 90, 0.0f, 0.0f, scaledResolution.getScaledWidth() + 20, scaledResolution.getScaledHeight() + 20, (float) (scaledResolution.getScaledWidth() + 21), (float) scaledResolution.getScaledHeight() + 20);
        drawProgress();
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledResolution.getScaledWidth() * scaleFactor, scaledResolution.getScaledHeight() * scaleFactor);

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);

        Minecraft.getMinecraft().updateDisplay();
    }

    public static void drawProgress() {
        if (Minecraft.getMinecraft().gameSettings == null || Minecraft.getMinecraft().getTextureManager() ==  null) return;
        if (fr == null) {
            fr = new FontRenderer("smooth", 15.0F);
        }

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        double progress1 = progress;
        double calculation = (progress1 / MAX) * scaledResolution.getScaledWidth() / 2 - 23;

        GlStateManager.resetColor();
        resetTextureState();

        fr.drawString(current, 20, scaledResolution.getScaledHeight() - 25, -1);

        String step = progress + "/" + MAX;
        fr.drawString(step, scaledResolution.getScaledWidth() - 20 - fr.getStringWidth(step), scaledResolution.getScaledHeight() - 25, -1);

        GlStateManager.resetColor();
        resetTextureState();

        int rectX = scaledResolution.getScaledWidth() / 2 - 96;
        int rectY = scaledResolution.getScaledHeight() / 2 + 2;
        int rectX1 = scaledResolution.getScaledWidth() / 2 - 96;

        GLRectUtils.drawRect(rectX, rectY, (float) (rectX + calculation), rectY + 5, -1);
        GLRectUtils.drawRectOutline(rectX1 - 2, rectY - 2, rectX1 + 193, rectY + 7, 2F,-1);
    }

    private static void resetTextureState() {
        GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
    }

}
