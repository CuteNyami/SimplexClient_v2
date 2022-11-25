package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.EntityAttackEvent;
import tk.simplexclient.event.impl.EntityDamageEvent;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class ComboCounterModule extends ModuleCreator {

    //@Option(text = "Width", min = 40, max = 100)
    private int width = 40;

    //@Option(text = "Height", min = 0, max = 100)
    private int height = 0;

    //@Option(text = "Background")
    private boolean background = true;

    private long hitTime = -1;
    private int combo, target;

    public ComboCounterModule() {
        super("combo-counter", 0, 0);
    }

    @Override
    public void render() {
        if (background) {
            GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(0, 0, 0, 140).getRGB());
            GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        }
        this.fr.drawString(getComboText(false), (getX() + getWidth() / 2) - (getFontWidth() / 2 + 2), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);

        if (System.currentTimeMillis() - hitTime > 2000) {
            combo = 0;
        }
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        this.fr.drawString(getComboText(false), (getX() + getWidth() / 2) - (getFontWidth() / 2 + 2), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);
    }

    public void dealHit() {
        combo++;
        target = -1;
        hitTime = System.currentTimeMillis();
    }

    public void takeHit() {
        combo = 0;
    }

    @EventTarget
    public void onEntityAttack(EntityAttackEvent event) {
        target = event.target.getEntityId();
    }

    @EventTarget
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.entity.getEntityId() == target) {
            dealHit();
        } else if (event.entity == Minecraft.getMinecraft().thePlayer) {
            takeHit();
        }
    }

    public String getComboText(boolean dummy) {
        if (dummy || combo == 0) {
            return "No Combo";
        } else if (combo == 1) {
            return "1 Combo";
        } else {
            return String.format("%s Combo", combo);
        }
    }

    public int getDummyFontWidth() {
        return (int) fr.getWidth(getComboText(true)) + 1;
    }

    public int getFontWidth() {
        return (int) fr.getWidth(getComboText(false)) + 1;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT + height;
    }
}
