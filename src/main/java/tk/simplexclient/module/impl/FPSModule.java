package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class FPSModule extends ModuleCreator {
    public FPSModule() {
        super(1, "fps", 100, 100);
    }

    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render() {
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(0,0,0, 140).getRGB());
        this.fr.drawString(Minecraft.getDebugFPS() + " FPS", getX(), getY(), -1);
    }

    @Override
    public void renderDummy() {
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);

        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        this.fr.drawString(Minecraft.getDebugFPS() + " FPS", getX(), getY(), -1);
    }

    @Override
    public int getWidth() {
        return (int) fr.getWidth("1000 FPS") + 1;
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
