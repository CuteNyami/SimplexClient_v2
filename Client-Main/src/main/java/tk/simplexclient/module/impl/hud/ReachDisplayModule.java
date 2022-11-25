package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.EntityAttackEvent;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;
import java.text.DecimalFormat;

public class ReachDisplayModule extends ModuleCreator {

    private double reach = 0;
    private long lastAttackTime;

    private final DecimalFormat formatter = new DecimalFormat("0.00");

    //@Option(text = "Background")
    private boolean background = true;

    public ReachDisplayModule() {
        super("reach-display", 0, 0);
    }

    @Override
    public void render() {
        if (background) {
            GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(0, 0, 0, 140).getRGB());
            GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        }
        this.fr.drawString(formatter.format(reach) + " blocks", (getX() + getWidth() / 2) - (getFontWidth() / 2 + 2), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth(), getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 4, getHeight() + 4);
        this.fr.drawString(formatter.format(reach) + " blocks", (getX() + getWidth() / 2) - (getFontWidth() / 2 + 2), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);
    }

    @EventTarget
    public void onEntityAttack(EntityAttackEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit.getEntityId() == event.target.getEntityId()) {
            final Vec3 vec3 = mc.getRenderViewEntity().getPositionEyes(1.0F);
            reach = mc.objectMouseOver.hitVec.distanceTo(vec3);
        }
        lastAttackTime = System.currentTimeMillis();
    }

    public int getFontWidth() {
        return (int) fr.getWidth(formatter.format(reach) + " blocks") + 1;
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
