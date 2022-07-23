package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class PingModule extends ModuleCreator {

    public PingModule() {
        super("ping", 400, 400);
    }

    @Override
    public void render() {
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(0,0,0, 140).getRGB());
        this.fr.drawString(getPlayerPing(), getX(), getY(), -1);
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());

        this.fr.drawString(getPlayerPing(), getX(), getY(), -1);
    }

    public String getPlayerPing() {
        return Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()) == null ? "0000 ms" : Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID()).getResponseTime() + " ms";
    }

    @Override
    public int getWidth() {
        return (int) fr.getWidth("0000 ms");
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
