package tk.simplexclient.gui.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLUtils;
import net.minecraft.client.renderer.vertex.*;

import java.awt.*;

import net.minecraft.client.renderer.*;

public class GuiUtils extends GLUtils {
    public static final GuiUtils INSTANCE;

    public static void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int borderSize, float zLevel) {
        drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, borderSize, borderSize, borderSize, borderSize, zLevel);
    }

    public static void drawContinuousTexturedBox(ResourceLocation res, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int borderSize, float zLevel) {
        drawContinuousTexturedBox(res, x, y, u, v, width, height, textureWidth, textureHeight, borderSize, borderSize, borderSize, borderSize, zLevel);
    }

    public static void drawContinuousTexturedBox(ResourceLocation res, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
        drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, topBorder, bottomBorder, leftBorder, rightBorder, zLevel);
    }

    public static void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight,
                                                 int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);

        int fillerWidth = textureWidth - leftBorder - rightBorder;
        int fillerHeight = textureHeight - topBorder - bottomBorder;
        int canvasWidth = width - leftBorder - rightBorder;
        int canvasHeight = height - topBorder - bottomBorder;
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;

        drawTexturedModalRect(x, y, u, v, leftBorder, topBorder, zLevel);
        drawTexturedModalRect(x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
        drawTexturedModalRect(x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
        drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);

        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); i++) {
            drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, (i == xPasses ? remainderWidth : fillerWidth), topBorder, zLevel);
            drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder, zLevel);

            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++)
                drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight), zLevel);
        }

        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); j++) {
            drawTexturedModalRect(x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
            drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, (j == yPasses ? remainderHeight : fillerHeight), zLevel);
        }
    }

    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, float zLevel) {
        float uScale = 1f / 0x100;
        float vScale = 1f / 0x100;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer wr = tessellator.getWorldRenderer();
        wr.begin(7, DefaultVertexFormats.POSITION_TEX);
        wr.pos(x, y + height, zLevel).tex(u * uScale, ((v + height) * vScale)).endVertex();
        wr.pos(x + width, y + height, zLevel).tex((u + width) * uScale, ((v + height) * vScale)).endVertex();
        wr.pos(x + width, y, zLevel).tex((u + width) * uScale, (v * vScale)).endVertex();
        wr.pos(x, y, zLevel).tex(u * uScale, (v * vScale)).endVertex();
        tessellator.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(final float x, final float y, final float u, final float v, final int width, final int height, final float textureWidth, final float textureHeight) {
        final float f = 1.0f / textureWidth;
        final float f2 = 1.0f / textureHeight;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) x, (double) (y + height), 0.0).tex((double) (u * f), (double) ((v + height) * f2)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0).tex((double) ((u + width) * f), (double) ((v + height) * f2)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0).tex((double) ((u + width) * f), (double) (v * f2)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0).tex((double) (u * f), (double) (v * f2)).endVertex();
        tessellator.draw();
    }

    public static void drawHollowRect(int x, int y, int w, int h, int color) {
        SimplexClient.getInstance().getGui().drawHorizontalLine(x, x + w, y, color);
        SimplexClient.getInstance().getGui().drawHorizontalLine(x, x + w, y + h, color);
        SimplexClient.getInstance().getGui().drawVerticalLine(x, y + h, y, color);
        SimplexClient.getInstance().getGui().drawVerticalLine(x + w, y + h, y, color);
    }

    public static void drawScaledCustomSizeModalRect(final float x, final float y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight) {
        final float f = 1.0f / tileWidth;
        final float f2 = 1.0f / tileHeight;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos((double) x, (double) (y + height), 0.0).tex((double) (u * f), (double) ((v + vHeight) * f2)).endVertex();
        worldrenderer.pos((double) (x + width), (double) (y + height), 0.0).tex((double) ((u + uWidth) * f), (double) ((v + vHeight) * f2)).endVertex();
        worldrenderer.pos((double) (x + width), (double) y, 0.0).tex((double) ((u + uWidth) * f), (double) (v * f2)).endVertex();
        worldrenderer.pos((double) x, (double) y, 0.0).tex((double) (u * f), (double) (v * f2)).endVertex();
        tessellator.draw();
    }

    public static int glToRGB(final float red, final float green, final float blue, final float alpha) {
        return new Color((int) red * 255, (int) green * 255, (int) blue * 255, (int) alpha * 255).getRGB();
    }

    public static float rgbToGl(final int rgb) {
        return rgb / 255.0f;
    }

    public static void setGlColor(final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }

    public static void setGlColor(final int color, final float alpha) {
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }

    public static int getRGB(final int color, final int alpha) {
        return new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, alpha).getRGB();
    }

    public static Color getColor(final int color) {
        return new Color(color, true);
    }

    public static int getAlpha(final int color) {
        return color >> 24 & 0xFF;
    }

    public static int hsvToRgb(int hue, final int saturation, final int value) {
        hue %= 360;
        final float s = saturation / 100.0f;
        final float v = value / 100.0f;
        final float c = v * s;
        final float h = hue / 60.0f;
        final float x = c * (1.0f - Math.abs(h % 2.0f - 1.0f));
        float r = 0.0f;
        float g = 0.0f;
        float b = 0.0f;
        switch (hue / 60) {
            case 0: {
                r = c;
                g = x;
                b = 0.0f;
                break;
            }
            case 1: {
                r = x;
                g = c;
                b = 0.0f;
                break;
            }
            case 2: {
                r = 0.0f;
                g = c;
                b = x;
                break;
            }
            case 3: {
                r = 0.0f;
                g = x;
                b = c;
                break;
            }
            case 4: {
                r = x;
                g = 0.0f;
                b = c;
                break;
            }
            case 5: {
                r = c;
                g = 0.0f;
                b = x;
                break;
            }
            default: {
                return 0;
            }
        }
        final float m = v - c;
        return (int) ((r + m) * 255.0f) << 16 | (int) ((g + m) * 255.0f) << 8 | (int) ((b + m) * 255.0f);
    }

    public static int[] rgbToHsv(final int rgb) {
        final float r = ((rgb & 0xFF0000) >> 16) / 255.0f;
        final float g = ((rgb & 0xFF00) >> 8) / 255.0f;
        final float b = (rgb & 0xFF) / 255.0f;
        final float M = (r > g) ? Math.max(r, b) : Math.max(g, b);
        final float m = (r < g) ? Math.min(r, b) : Math.min(g, b);
        final float c = M - m;
        float h;
        if (M == r) {
            for (h = (g - b) / c; h < 0.0f; h += 6.0f) {
            }
            h %= 6.0f;
        } else if (M == g) {
            h = (b - r) / c + 2.0f;
        } else {
            h = (r - g) / c + 4.0f;
        }
        h *= 60.0f;
        final float s = c / M;
        return new int[]{(c == 0.0f) ? -1 : ((int) h), (int) (s * 100.0f), (int) (M * 100.0f)};
    }

    public static int getIntermediateColor(final int a, final int b, final float percent) {
        final float avgRed = (a >> 16 & 0xFF) * percent + (b >> 16 & 0xFF) * (1.0f - percent);
        final float avgGreen = (a >> 8 & 0xFF) * percent + (b >> 8 & 0xFF) * (1.0f - percent);
        final float avgBlue = (a >> 0 & 0xFF) * percent + (b >> 0 & 0xFF) * (1.0f - percent);
        final float avgAlpha = (a >> 24 & 0xFF) * percent + (b >> 24 & 0xFF) * (1.0f - percent);
        try {
            return new Color(avgRed / 255.0f, avgGreen / 255.0f, avgBlue / 255.0f, avgAlpha / 255.0f).getRGB();
        } catch (IllegalArgumentException e) {
            return Integer.MIN_VALUE;
        }
    }

    public static int convertPercentToValue(final float percent) {
        return (int) (percent * 255.0f);
    }

    static {
        INSTANCE = new GuiUtils();
    }
}
