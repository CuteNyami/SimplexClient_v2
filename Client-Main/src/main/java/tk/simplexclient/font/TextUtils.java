package tk.simplexclient.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class TextUtils {
    private static final FontRenderer font;

    public static void drawScaledString(String text, float x, float y, int color, float scale, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        font.drawString(text, x, y, color, shadow);
        GlStateManager.popMatrix();
    }

    public static void drawCenteredScaledString(String text, float x, float y, int color, float scale) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);
        drawCenteredString(text, x / scale, y / scale, color);
        GlStateManager.popMatrix();
    }

    public static void drawScaledString(String text, float x, float y, int color, float scaleX, float scaleY, boolean shadow) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, scaleY);
        font.drawString(text, x, y, color, shadow);
        GlStateManager.popMatrix();
    }

    public static void drawCenteredScaledString(String text, float x, float y, int color, float scaleX, float scaleY) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(scaleX, scaleY, scaleY);
        drawCenteredString(text, (float) (x / scaleX), (float) (y / scaleY), -1);
        GlStateManager.popMatrix();
    }

    public static void drawCenteredString(String text, float x, float y, int color) {
        font.drawStringWithShadow(text, x - font.getStringWidth(text) / 2, y, color);
    }

    static {
        font = Minecraft.getMinecraft().fontRendererObj;
    }
}
