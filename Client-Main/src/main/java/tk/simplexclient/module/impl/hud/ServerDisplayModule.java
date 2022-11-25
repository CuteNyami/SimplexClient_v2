package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class ServerDisplayModule extends ModuleCreator {

    public ServerDisplayModule() {
        super("serverdisplay", 0,0);
    }

    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render() {
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + 40, getY() + getHeight() + 2, new Color(0,0,0, 140).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 6);
        this.fr.drawString(getServerIp(), (getX() + 40 / 2) - (getFontWidth() / 2 + 9), getY(), -1);
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + 40, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + 40, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 6);
        this.fr.drawString(getServerIp(), (getX() + 40 / 2) - (getFontWidth() / 2 + 9), getY(), -1);
    }

    public String getServerIp() {
        return mc.getCurrentServerData() == null ? "unknown" : mc.getCurrentServerData().serverIP;
    }

    public int getFontWidth() {
        return (int) fr.getWidth(String.valueOf(getServerIp())) + 1;
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
