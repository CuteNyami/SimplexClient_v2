package tk.simplexclient.module.impl.hud;

import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeModule extends ModuleCreator {

    private final DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");

    public TimeModule() {
        super("time", 0, 0);
    }

    @Override
    public void render() {
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(0,0,0, 140).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 8, getHeight() + 4);
        this.fr.drawString(getTime(), getX() + 1, getY(), -1);
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 8, getHeight() + 4);

        this.fr.drawString(getTime(), getX() + 1, getY(), -1);
    }

    public String getTime() {
        return dateFormat.format(new Date()).toString();
    }

    @Override
    public int getWidth() {
        return (int) fr.getWidth("00:00 am");
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
