package tk.simplexclient.module.impl;

import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeModule extends ModuleCreator {

    private final DateFormat dateFormat = new SimpleDateFormat("hh:mm aa");

    public TimeModule() {
        super("time", 100, 300);
    }

    @Override
    public void render() {
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(0,0,0, 140).getRGB());
        this.fr.drawString(getTime(), getX() + 1, getY(), -1);
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());

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
