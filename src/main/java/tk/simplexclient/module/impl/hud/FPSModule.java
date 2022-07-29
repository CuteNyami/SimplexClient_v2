package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class FPSModule extends ModuleCreator {
    public FPSModule() {
        super("fps", 20, 20);
    }

    //@Option(text = "Width", min = 40, max = 100)
    private int width = 40;

    //@Option(text = "Height", min = 0, max = 100)
    private int height = 0;

    //@Option(text = "Background")
    private boolean background = true;

    @Override
    public void render() {
        if (background) {
            GLRectUtils.drawRect(getX() - 4, getY() - 2,  getX() + getWidth(), getY() + getHeight() + 2, new Color(0, 0, 0, 140).getRGB());
            GLRectUtils.drawShadow(getX() - 4,getY() - 2, getWidth() + 4, getHeight() + 4);
        }
        this.fr.drawString(Minecraft.getDebugFPS() + " FPS", (getX() + getWidth() / 2) - (getFontWidth() / 2 + 9), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4,getY() - 2, getWidth() + 4, getHeight() + 4);
        this.fr.drawString(Minecraft.getDebugFPS() + " FPS", (getX() + getWidth() / 2) - (getFontWidth() / 2 + 9), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);
    }

    public int getFontWidth() {
        return (int) fr.getWidth(String.valueOf(Minecraft.getDebugFPS())) + 1;
    }

    @Override
    public int getWidth() {
        return (int) width;
    }

    @Override
    public int getHeight() {
        return (int) (fr.FONT_HEIGHT + height);
    }
}