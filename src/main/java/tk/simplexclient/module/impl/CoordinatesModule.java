package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;

import java.awt.*;

public class CoordinatesModule extends ModuleCreator {

    private final Minecraft mc = Minecraft.getMinecraft();

    public CoordinatesModule() {
        super("coordinates", 130, 130);
    }

    @Option(text = "Test2 Slider", min = 1, max = 100)
    private int test = 50;

    @Override
    public void render() {
        Gui.drawRect(getX() - 5, getY() - 3, getX() + getWidth(), getY() + getHeight() + 2, new Color(0, 0, 0, 160).getRGB());

        fr.drawString(String.format("X: %.0f", mc.getRenderViewEntity().posX), getX(), getY(), -1);
        fr.drawString(String.format("Y: %.0f", mc.getRenderViewEntity().posY), getX(), getY() + fr.FONT_HEIGHT + 2, -1);
        fr.drawString(String.format("Z: %.0f", mc.getRenderViewEntity().posZ), getX(), getY() + (fr.FONT_HEIGHT * 2) + 4, -1);

        String direction = "";
        switch (getDirectionFacing()) {
            case 0:
                direction = "S";
                break;
            case 1:
                direction = "SW";
                break;
            case 2:
                direction = "W";
                break;
            case 3:
                direction = "NW";
                break;
            case 4:
                direction = "N";
                break;
            case 5:
                direction = "NE";
                break;
            case 6:
                direction = "E";
                break;
            case 7:
                direction = "SE";
                break;
            default:
                break;
        }
        fr.drawString(direction, getX() + 54, getY() + fr.FONT_HEIGHT + 2, -1);
    }

    @Override
    public void renderDummy(int width, int height) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);

        GLRectUtils.drawRectOutline(getX() - 5, getY() - 3, getX() + getWidth(), getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 5, getY() - 3, getX() + getWidth(), getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        fr.drawString(String.format("X: %.0f", mc.getRenderViewEntity().posX), getX(), getY(), -1);
        fr.drawString(String.format("Y: %.0f", mc.getRenderViewEntity().posY), getX(), getY() + fr.FONT_HEIGHT + 2, -1);
        fr.drawString(String.format("Z: %.0f", mc.getRenderViewEntity().posZ), getX(), getY() + (fr.FONT_HEIGHT * 2) + 4, -1);

        String direction = "";
        switch (getDirectionFacing()) {
            case 0:
                direction = "S";
                break;
            case 1:
                direction = "SW";
                break;
            case 2:
                direction = "W";
                break;
            case 3:
                direction = "NW";
                break;
            case 4:
                direction = "N";
                break;
            case 5:
                direction = "NE";
                break;
            case 6:
                direction = "E";
                break;
            case 7:
                direction = "SE";
                break;
            default:
                break;
        }
        fr.drawString(direction, getX() + 54, getY() + fr.FONT_HEIGHT + 2, -1);
    }

    private int getDirectionFacing() {
        int yaw = (int) mc.getRenderViewEntity().rotationYaw;
        yaw += 360;
        yaw += 22;
        yaw %= 360;
        return yaw / 45;
    }

    private String getCoordsPlaceholder() {
        return "Y: 10000      NE";
    }

    @Override
    public int getWidth() {
        return (int) fr.getWidth(getCoordsPlaceholder()) + 25;
    }

    @Override
    public int getHeight() {
        return (fr.FONT_HEIGHT * 4) -2;
    }
}
