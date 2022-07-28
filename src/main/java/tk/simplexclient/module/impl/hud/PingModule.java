package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class PingModule extends ModuleCreator {

    public PingModule() {
        super("ping", 0, 0);
    }

    @Override
    public void render() {
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + 40, getY() + getHeight() + 2, new Color(0,0,0, 140).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        this.fr.drawString(getPlayerPing() + " ms", (getX() + 40 / 2) - (getFontWidth() / 2 + 9), getY(), -1);
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + 40, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + 40, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        this.fr.drawString(getPlayerPing() + " ms", (getX() + 40 / 2) - (getFontWidth() / 2 + 9), getY(), -1);
    }

    public String getPlayerPing() {
        return Minecraft.getMinecraft().isSingleplayer() ? "-1" : Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()) == null ? "0000" : String.valueOf(Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime());
    }

    public int getFontWidth() {
        return (int) fr.getWidth(getPlayerPing());
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
