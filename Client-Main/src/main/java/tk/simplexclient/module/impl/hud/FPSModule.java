package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import tk.simplexclient.font.TextUtils;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;

import java.awt.*;

public class FPSModule extends ModuleCreator {

    @Option(y = -10)
    private String text = "%fps% FPS";

    @Option(text = "Outline", y = -8)
    private Color outlineColor = new Color(255, 255, 255);

    @Option(text = "Background", y = 15)
    private boolean background = true;

    @Option(text = "Shadow", y = 35)
    private boolean shadow = true;

    @Option(text = "Outline", y = 55)
    private boolean outline = false;

    @Option(text = "MC Font", y = 75)
    private boolean mcFont = true;

    @Option(text = "Outline Width", min = 0.25F, max = 5F, y = -18)
    private double outlineWidth = 0.25F;

    public FPSModule() {
        super("fps", 0, 0);
    }

    @Override
    public void render() {
        if (getValBoolean("shadow")) {
            GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        }
        if (getValBoolean("background")) {
            GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(0, 0, 0, 140).getRGB());
        }
        if (getValBoolean("outline")) {
            GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, (float) -getValDouble("outlineWidth"), getValColorRGB("outlineColor"));
        }
        drawString(getText(), 0, 0, getValBoolean("mcFont"), -1);
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        drawString(getText(), 0, 0, getValBoolean("mcFont"), -1);
    }

    public String getText() {
        return getValString("text").replace("%fps%", String.valueOf(Minecraft.getDebugFPS())).replace('&', getValBoolean("mcFont") ? '§' : '&');
    }

    public int getFontWidth() {
        return (int) fr.getWidth(getText()) + 1;
    }

    @Override
    public int getWidth() {
        return 40;
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
