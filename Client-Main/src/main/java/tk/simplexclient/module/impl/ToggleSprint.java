package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.EventUpdate;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class ToggleSprint extends ModuleCreator {

    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean sprintingToggled = false;

    private String text = "[Sprinting (Toggled)]";

    public ToggleSprint() {
        super("togglesprint", 0, 0);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        float f = 0.8F;

        if(mc.gameSettings.keyBindSprint.isPressed()) {
            if(mc.thePlayer.isSprinting() && !sprintingToggled) sprintingToggled = true;
            else if(!mc.thePlayer.isSprinting()) sprintingToggled = !sprintingToggled;
        }

        boolean flags = !mc.thePlayer.movementInput.sneak &&
                (mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F || mc.thePlayer.capabilities.allowFlying) &&
                !mc.thePlayer.isPotionActive(Potion.blindness) &&
                mc.thePlayer.movementInput.moveForward >= f &&
                !mc.thePlayer.isSprinting() &&
                !mc.thePlayer.isUsingItem() &&
                !mc.thePlayer.isCollidedHorizontally &&
                !mc.gameSettings.keyBindBack.isKeyDown() &&
                sprintingToggled;

        if (flags) { mc.thePlayer.setSprinting(true); }

        if(mc.thePlayer.isSprinting()) text = ("[Sprinting " + (sprintingToggled ? "(Toggled)]" : "(Vanilla)]"));
    }

    @Override
    public void render() {
        if (sprintingToggled || mc.thePlayer.isSprinting()) {
            GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(0, 0, 0, 140).getRGB());
            GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 8, getHeight() + 4);
            this.fr.drawString(text, (getX() + getWidth() / 2) - (getFontWidth() / 2 + 1), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);
        }
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GLRectUtils.drawRectOutline(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX() - 4, getY() - 2, getX() + getWidth() + 4, getY() + getHeight() + 2, new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX() - 4, getY() - 2, getWidth() + 8, getHeight() + 4);
        this.fr.drawString(text, (getX() + getWidth() / 2) - (getFontWidth() / 2 + 1), (getY() + getHeight() / 2) - (fr.FONT_HEIGHT / 2 + 1), -1);
    }

    public int getFontWidth() {
        return (int) fr.getWidth(text) + 1;
    }

    @Override
    public int getWidth() {
        return 70;
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT;
    }
}
