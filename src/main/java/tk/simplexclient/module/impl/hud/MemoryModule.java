package tk.simplexclient.module.impl.hud;

import net.minecraft.client.gui.ScaledResolution;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class MemoryModule extends ModuleCreator {

    public Runtime runtime;

    public MemoryModule() {
        super( "memory", 0, 0);
        runtime = Runtime.getRuntime();
    }

    @Override
    public void render() {
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(0,0,0, 140).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 8, getHeight() + 4);
        this.fr.drawString(getMemory(), getX(), getY(), -1);
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 8, getHeight() + 4);

        this.fr.drawString(getMemory(), getX(), getY(), -1);
    }

    public String getMemory() {
        return runtime == null ? "100% Memory" : (runtime.totalMemory() - runtime.freeMemory()) * 100L / runtime.maxMemory() + "% Memory";
    }

    @Override
    public int getWidth() {
        return (int) fr.getWidth("100% Memory");
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
