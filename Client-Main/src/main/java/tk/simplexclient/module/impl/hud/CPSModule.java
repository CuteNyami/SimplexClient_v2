package tk.simplexclient.module.impl.hud;

import org.lwjgl.input.Mouse;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CPSModule extends ModuleCreator {

    private List<Long> clicksLMB = new ArrayList<Long>();

    private List<Long> clicksRMB = new ArrayList<Long>();

    private boolean wasPressedLMB;
    private long lastPressedLMB;

    private boolean wasPressedRMB;
    private long lastPressedRMB;

    @Option(y = -10)
    private String text = "%lmb% | %rmb% CPS";

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

    public CPSModule() {
        super("cps", 0, 0);
    }

    @Override
    public void render() {
        final boolean pressedLMB = Mouse.isButtonDown(0);

        if (pressedLMB != this.wasPressedLMB) {
            this.lastPressedLMB = System.currentTimeMillis();
            this.wasPressedLMB = pressedLMB;
            if (pressedLMB) {
                this.clicksLMB.add(this.lastPressedLMB);
            }
        }

        final boolean pressedRMB = Mouse.isButtonDown(1);

        if (pressedRMB != this.wasPressedRMB) {
            this.lastPressedRMB = System.currentTimeMillis();
            this.wasPressedRMB = pressedRMB;
            if (pressedRMB) {
                this.clicksRMB.add(this.lastPressedRMB);
            }
        }

        if (getValBoolean("shadow")) {
            GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        }
        if (getValBoolean("background")) {
            GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(0, 0, 0, 140).getRGB());
        }
        if (getValBoolean("outline")) {
            GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, (float) -getValDouble("outlineWidth"), getValColorRGB("outlineColor"));
        }

        drawString(getText(), getValBoolean("mcFont") ? 5 : 0, 0, getValBoolean("mcFont"), -1);
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);

        drawString(getText(), getValBoolean("mcFont") ? 5 : 0, 0, getValBoolean("mcFont"), -1);
    }

    public String getText() {
        return getValString("text").replace("%lmb%", String.valueOf(getLMB())).replace("%rmb%", String.valueOf(getRMB())).replace('&', getValBoolean("mcFont") ? '§' : '&');
    }

    public int getFontWidth() {
        return (int) fr.getWidth(getText()) + 1;
    }

    public int getLMB() {
        final long time = System.currentTimeMillis();
        this.clicksLMB.removeIf(aLong -> aLong + 1000 < time);
        return this.clicksLMB.size();
    }

    public int getRMB() {
        final long time = System.currentTimeMillis();
        this.clicksRMB.removeIf(aLong -> aLong + 1000 < time);
        return this.clicksRMB.size();
    }

    @Override
    public int getWidth() {
        return 50;
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
