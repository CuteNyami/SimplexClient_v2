package tk.simplexclient.gl;

import net.minecraft.util.ResourceLocation;
import tk.simplexclient.font.FontUtils;
import tk.simplexclient.gui.utils.GuiUtils;
import tk.simplexclient.math.MathUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

/**
 * This class was not coded by any of the SimplexClient developers!
 *
 * @author Unknown
 */
public class GLRectUtils extends FontUtils {

    private static boolean lineAA;

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawRect(float left, float top, float right, float bottom, final int color) {
        if (left < right) {
            final float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            final float j = top;
            top = bottom;
            bottom = j;
        }
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GuiUtils.setGlColor(color);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double) left, (double) bottom, 0.0).endVertex();
        worldrenderer.pos((double) right, (double) bottom, 0.0).endVertex();
        worldrenderer.pos((double) right, (double) top, 0.0).endVertex();
        worldrenderer.pos((double) left, (double) top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawColoredRect(final float x, final float y, final float x2, final float y2, final int color, final int color2) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        GuiUtils.setGlColor(color);
        worldRenderer.pos((double) x, (double) y2, 0.0).endVertex();
        GuiUtils.setGlColor(color2);
        worldRenderer.pos((double) x2, (double) y2, 0.0).endVertex();
        worldRenderer.pos((double) x2, (double) y, 0.0).endVertex();
        GuiUtils.setGlColor(color);
        worldRenderer.pos((double) x, (double) y, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRectangle(final int left, final int top, final int right, final int bottom, final int coltl, final int coltr, final int colbl, final int colbr, final int zLevel) {
        drawGradientRectangle(left, top, right, (float) bottom, coltl, coltr, colbl, colbr, zLevel);
    }

    public static void drawGradientRectangle(final float left, final float top, final float right, final float bottom, final int coltl, final int coltr, final int colbl, final int colbr, final int zLevel) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer buffer = tessellator.getWorldRenderer();
        buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos((double) right, (double) top, (double) zLevel).color((coltr & 0xFF0000) >> 16, (coltr & 0xFF00) >> 8, coltr & 0xFF, (coltr & 0xFF000000) >>> 24).endVertex();
        buffer.pos((double) left, (double) top, (double) zLevel).color((coltl & 0xFF0000) >> 16, (coltl & 0xFF00) >> 8, coltl & 0xFF, (coltl & 0xFF000000) >>> 24).endVertex();
        buffer.pos((double) left, (double) bottom, (double) zLevel).color((colbl & 0xFF0000) >> 16, (colbl & 0xFF00) >> 8, colbl & 0xFF, (colbl & 0xFF000000) >>> 24).endVertex();
        buffer.pos((double) right, (double) bottom, (double) zLevel).color((colbr & 0xFF0000) >> 16, (colbr & 0xFF00) >> 8, colbr & 0xFF, (colbr & 0xFF000000) >>> 24).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawRectOutline(final float left, final float top, final float right, final float bottom, final float width, final int color) {
        //final float width = 0.55f;
        drawRect(left - width, top - width, right + width, top, color);
        drawRect(right, top, right + width, bottom, color);
        drawRect(left - width, bottom, right + width, bottom + width, color);
        drawRect(left - width, top, left, bottom, color);
    }

    /**
     * Render a rounded rectangle
     */
    public static void drawRoundedRect(final float nameInt1, final float nameInt2, final float nameInt3, final float nameInt4, final float radius, final int color) {
        final float f1 = (color >> 24 & 0xFF) / 255.0f;
        final float f2 = (color >> 16 & 0xFF) / 255.0f;
        final float f3 = (color >> 8 & 0xFF) / 255.0f;
        final float f4 = (color & 0xFF) / 255.0f;
        GlStateManager.color(f2, f3, f4, f1);
        drawRoundedRect(nameInt1, nameInt2, nameInt3, nameInt4, radius);
    }

    public static void drawRoundedRect(final float nameFloat1, final float nameFloat2, final float nameFloat3, final float nameFloat4, final float nameFloat5) {
        final int i = 18;
        final float f1 = 90.0f / i;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.enableColorMaterial();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glBegin(5);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat2);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat4);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat2);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat4);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(nameFloat1, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat1, nameFloat4 - nameFloat5);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat4 - nameFloat5);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(nameFloat3, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat3, nameFloat4 - nameFloat5);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat4 - nameFloat5);
        GL11.glEnd();
        GL11.glBegin(6);
        float f2 = nameFloat3 - nameFloat5;
        float f3 = nameFloat2 + nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 + nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 - nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = nameFloat1 + nameFloat5;
        f3 = nameFloat2 + nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 - nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 - nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = nameFloat1 + nameFloat5;
        f3 = nameFloat4 - nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 - nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 + nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = nameFloat3 - nameFloat5;
        f3 = nameFloat4 - nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 + nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 + nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawShadow(float x, float y, float width, float height) {
        drawTexturedRect(x - 4, y - 4, 4, 4, "panel_top_left");
        drawTexturedRect(x - 4, y + height, 4, 4, "panel_bottom_left");
        drawTexturedRect(x + width, y + height, 4, 4, "panel_bottom_right");
        drawTexturedRect(x + width, y - 4, 4, 4, "panel_top_right");
        drawTexturedRect(x - 4, y, 4, height, "panel_left");
        drawTexturedRect(x + width, y, 4, height, "panel_right");
        drawTexturedRect(x, y - 4, width, 4, "panel_top");
        drawTexturedRect(x, y + height, width, 4, "panel_bottom");
    }

    public static void drawTexturedRect(float x, float y, float width, float height, String image) {
        GL11.glPushMatrix();
        final boolean enableBlend = GL11.glIsEnabled(GL11.GL_BLEND);
        final boolean disableAlpha = !GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
        if (!enableBlend) GL11.glEnable(GL11.GL_BLEND);
        if (!disableAlpha) GL11.glDisable(GL11.GL_ALPHA_TEST);
        mc.getTextureManager().bindTexture(new ResourceLocation("simplex/shadow/" + image + ".png"));
        GlStateManager.color(1F, 1F, 1F, 1F);
        drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        if (!enableBlend) GL11.glDisable(GL11.GL_BLEND);
        if (!disableAlpha) GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glPopMatrix();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, (y + height), 0.0D).tex((u * f), ((v + height) * f1)).endVertex();
        worldrenderer.pos((x + width), (y + height), 0.0D).tex(((u + width) * f), ((v + height) * f1)).endVertex();
        worldrenderer.pos((x + width), y, 0.0D).tex(((u + width) * f), (v * f1)).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex((u * f), (v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawRoundedOutline(final int x, final int y, final int x2, final int y2, final float radius, final float width, final int color) {
        GuiUtils.setGlColor(color);
        drawRoundedOutline(x, y, x2, y2, radius, width);
    }

    public static void drawRoundedOutline(final float x, final float y, final float x2, final float y2, final float radius, final float width, final int color) {
        GuiUtils.setGlColor(color);
        drawRoundedOutline(x, y, x2, y2, radius, width);
    }

    /*
    public static void drawRoundedRect(final float nameFloat1, final float nameFloat2, final float nameFloat3, final float nameFloat4, final float nameFloat5, int color, int color1) {
        final int i = 18;
        final float f1 = 90.0f / i;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.enableColorMaterial();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glBegin(5);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat2);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat4);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat2);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat4);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(nameFloat1, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat1, nameFloat4 - nameFloat5);
        GL11.glVertex2f(nameFloat1 + nameFloat5, nameFloat4 - nameFloat5);
        GL11.glEnd();
        GL11.glBegin(5);
        GL11.glVertex2f(nameFloat3, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat2 + nameFloat5);
        GL11.glVertex2f(nameFloat3, nameFloat4 - nameFloat5);
        GL11.glVertex2f(nameFloat3 - nameFloat5, nameFloat4 - nameFloat5);
        GL11.glEnd();
        GL11.glBegin(6);
        float f2 = nameFloat3 - nameFloat5;
        float f3 = nameFloat2 + nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 + nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 - nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = nameFloat1 + nameFloat5;
        f3 = nameFloat2 + nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 - nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 - nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = nameFloat1 + nameFloat5;
        f3 = nameFloat4 - nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 - nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 + nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glBegin(6);
        f2 = nameFloat3 - nameFloat5;
        f3 = nameFloat4 - nameFloat5;
        GL11.glVertex2f(f2, f3);
        for (int j = 0; j <= i; ++j) {
            final float f4 = j * f1;
            GL11.glVertex2f((float) (f2 + nameFloat5 * Math.cos(Math.toRadians(f4))), (float) (f3 + nameFloat5 * Math.sin(Math.toRadians(f4))));
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

     */
    public static void drawSmoothRoundedRect(float x, float y, float x1, float y1, float radius, int color) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        x1 *= 2.0D;
        y1 *= 2.0D;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        setColor(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        int i;
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        GL11.glLineWidth(1);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void setColor(int color) {
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        GL11.glColor4f(r, g, b, a);
    }

    private static void drawRoundedOutline(final float x, final float y, final float x2, final float y2, final float radius, final float width) {
        final int i = 18;
        final int j = 90 / i;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.enableColorMaterial();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        if (width != 1.0f) {
            GL11.glLineWidth(width);
        }
        GL11.glBegin(3);
        GL11.glVertex2f(x + radius, y);
        GL11.glVertex2f(x2 - radius, y);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y + radius);
        GL11.glVertex2f(x2, y2 - radius);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2 - radius, y2 - 0.1f);
        GL11.glVertex2f(x + radius, y2 - 0.1f);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x + 0.1f, y2 - radius);
        GL11.glVertex2f(x + 0.1f, y + radius);
        GL11.glEnd();
        float f1 = x2 - radius;
        float f2 = y + radius;
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = 90 - k * j;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        f1 = x2 - radius;
        f2 = y2 - radius;
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 270;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y2 - radius;
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 90;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y + radius;
        for (int k = 0; k <= i; ++k) {
            final int m = 270 - k * j;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        if (width != 1.0f) {
            GL11.glLineWidth(1.0f);
        }
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
    }

    public static void drawRoundedOutlineGradient(final float x, final float y, final float x2, final float y2, final float radius, final float width, final int color, final int color2) {
        final int i = 18;
        final int j = 90 / i;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.enableColorMaterial();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        if (width != 1.0f) {
            GL11.glLineWidth(width);
        }
        GuiUtils.setGlColor(color);
        GL11.glShadeModel(7425);
        GL11.glBegin(3);
        GL11.glVertex2f(x + radius, y);
        GL11.glVertex2f(x2 - radius, y);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2, y + radius);
        GuiUtils.setGlColor(color2);
        GL11.glVertex2f(x2, y2 - radius);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex2f(x2 - radius, y2 - 0.1f);
        GL11.glVertex2f(x + radius, y2 - 0.1f);
        GL11.glEnd();
        GL11.glBegin(3);
        GuiUtils.setGlColor(color2);
        GL11.glVertex2f(x + 0.1f, y2 - radius);
        GuiUtils.setGlColor(color);
        GL11.glVertex2f(x + 0.1f, y + radius);
        GL11.glEnd();
        float f1 = x2 - radius;
        float f2 = y + radius;
        GuiUtils.setGlColor(color);
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = 90 - k * j;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        f1 = x2 - radius;
        f2 = y2 - radius;
        GuiUtils.setGlColor(color2);
        GL11.glBegin(3);
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 270;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 - radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GuiUtils.setGlColor(color2);
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y2 - radius;
        for (int k = 0; k <= i; ++k) {
            final int m = k * j + 90;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        GuiUtils.setGlColor(color);
        GL11.glBegin(3);
        f1 = x + radius;
        f2 = y + radius;
        for (int k = 0; k <= i; ++k) {
            final int m = 270 - k * j;
            GL11.glVertex2f((float) (f1 + radius * MathUtil.getRightAngle(m)), (float) (f2 + radius * MathUtil.getAngle(m)));
        }
        GL11.glEnd();
        if (width != 1.0f) {
            GL11.glLineWidth(1.0f);
        }
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
    }
}
