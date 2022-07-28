package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
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

    private final ResourceLocation defaultSkin = new ResourceLocation("textures/entity/steve.png");

    public TargetHUDModule() {
        super("targethud", 0, 0);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        target = mc.pointedEntity;
    }

    @Override
    public void render() {
        if (target == null || !(target instanceof AbstractClientPlayer)) {
            GLRectUtils.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), new Color(0, 0, 0, 140).getRGB());
            GLRectUtils.drawShadow(getX(), getY(), getWidth(), getHeight());
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            Minecraft.getMinecraft().getTextureManager().bindTexture(defaultSkin);
            Gui.drawModalRectWithCustomSizedTexture(getX() + 4, getY() + 4, 28, 28, 28, 28, 225, 225);
            SimplexClient.getInstance().getSmoothFont().drawString("unknown", getX() + 28 + 8, getY() + 5, -1);

            Gui.drawRect(getX() + 28 + 8, getY() + 16, getX() + 28 + 8 + 20 * 3, getY() + 18, new Color(3, 252, 252).getRGB());
            Gui.drawRect(getX() + 28 + 8, getY() + 24, getX() + 28 + 8 + 3 * 7, getY() + 26, new Color(28, 145, 255).getRGB());
        }
        if (target instanceof AbstractClientPlayer) {
            GLRectUtils.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), new Color(0, 0, 0, 140).getRGB());
            GLRectUtils.drawShadow(getX(), getY(), getWidth(), getHeight());

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            ResourceLocation skin = ((AbstractClientPlayer) target).getLocationSkin();
            Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
            Gui.drawModalRectWithCustomSizedTexture(getX() + 4, getY() + 4, 28, 28, 28, 28, 225, 225);

            SimplexClient.getInstance().getSmoothFont().drawString(target.getName(), getX() + 28 + 8, getY() + 5, -1);

            Gui.drawRect(getX() + 28 + 8, getY() + 16, (int) (getX() + 28 + 8 + ((AbstractClientPlayer) target).getHealth() * 3), getY() + 18, new Color(3, 252, 252).getRGB());
            Gui.drawRect(getX() + 28 + 8, getY() + 24, getX() + 28 + 8 + ((AbstractClientPlayer) target).hurtTime * 6, getY() + 26, new Color(28, 145, 255).getRGB());
        }
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GLRectUtils.drawRectOutline(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0.25F, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), new Color(255, 255, 255, 70).getRGB());
        GLRectUtils.drawShadow(getX(), getY(), getWidth(), getHeight());

        Minecraft.getMinecraft().getTextureManager().bindTexture(defaultSkin);
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
