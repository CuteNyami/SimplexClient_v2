package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.EventUpdate;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class TargetHUDModule extends ModuleCreator {

    private final Minecraft mc = Minecraft.getMinecraft();

    private Entity target;

    public TargetHUDModule() {
        super("targethud", 0, 0);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        target = mc.pointedEntity;
    }

    @Override
    public void render() {
        if (target == null) {
            GLRectUtils.drawRoundedRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 2F, new Color(0, 0, 0, 140).getRGB());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/entity/steve.png"));
            Gui.drawModalRectWithCustomSizedTexture(getX() + 4, getY() + 4, 28, 28, 28, 28, 225, 225);
            SimplexClient.getInstance().getSmoothFont().drawString("unknown", getX() + 28 + 8, getY() + 5, -1);

            Gui.drawRect(getX() + 28 + 8, getY() + 16, getX() + 28 + 8 + 20 * 3, getY() + 18, new Color(3, 252, 252).getRGB());
            Gui.drawRect(getX() + 28 + 8, getY() + 24, getX() + 28 + 8 + 3 * 7, getY() + 26, new Color(28, 145, 255).getRGB());
        } else {
            if (target instanceof AbstractClientPlayer) {
                GLRectUtils.drawRoundedRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 2F, new Color(0, 0, 0, 140).getRGB());

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                ResourceLocation skin = ((AbstractClientPlayer) target).getLocationSkin();
                Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
                Gui.drawModalRectWithCustomSizedTexture(getX() + 4, getY() + 4, 28, 28, 28, 28, 225, 225);

                SimplexClient.getInstance().getSmoothFont().drawString(target.getName(), getX() + 28 + 8, getY() + 5, -1);

                Gui.drawRect(getX() + 28 + 8, getY() + 16, (int) (getX() + 28 + 8 + ((AbstractClientPlayer) target).getHealth() * 3), getY() + 18, new Color(3, 252, 252).getRGB());
                Gui.drawRect(getX() + 28 + 8, getY() + 24, getX() + 28 + 8 + ((AbstractClientPlayer) target).hurtTime * 6, getY() + 26, new Color(28, 145, 255).getRGB());
            }
        }
    }

    @Override
    public void renderDummy(int width, int height) {
        GLRectUtils.drawRoundedOutline(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 2F, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRoundedRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 2F, new Color(255, 255, 255, 70).getRGB());

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/entity/steve.png"));
        Gui.drawModalRectWithCustomSizedTexture(getX() + 4, getY() + 4, 28, 28, 28, 28, 225, 225);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        SimplexClient.getInstance().getSmoothFont().drawString("unknown", getX() + 28 + 8, getY() + 5, -1);

        Gui.drawRect(getX() + 28 + 8, getY() + 16, getX() + 28 + 8 + 20 * 3, getY() + 18, new Color(3, 252, 252).getRGB());
        Gui.drawRect(getX() + 28 + 8, getY() + 24, getX() + 28 + 8 + 3 * 7, getY() + 26, new Color(28, 145, 255).getRGB());
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 36;
    }
}
