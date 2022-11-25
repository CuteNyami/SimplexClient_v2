package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class ArmorStatusModule extends ModuleCreator {

    private final Minecraft mc = Minecraft.getMinecraft();

    public ArmorStatusModule() {
        super("armorstatus", 0, 0);
    }

    @Override
    public void render() {
        for (int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
            ItemStack itemStack = mc.thePlayer.inventory.armorInventory[i];
            renderItemStack(i + 1, itemStack);
        }
        renderItemStack(0, mc.thePlayer.inventory.getCurrentItem());
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GLRectUtils.drawRectOutline(getX() - 5, getY() - 20, getX() + getWidth() - 4, getY() + getHeight() + 8, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawShadow(getX() - 5, getY() - 20, getWidth() + 1, getHeight() + 28);
        Gui.drawRect(getX() - 5, getY() - 20, getX() + getWidth() - 4, getY() + getHeight() + 8, new Color(255, 255, 255, 70).getRGB());
        renderItemStack(4, new ItemStack(Items.diamond_helmet));
        renderItemStack(3, new ItemStack(Items.diamond_chestplate));
        renderItemStack(2, new ItemStack(Items.diamond_leggings));
        renderItemStack(1, new ItemStack(Items.diamond_boots));
        renderItemStack(0, new ItemStack(Items.diamond_sword));
    }

    private void renderItemStack(int i, ItemStack itemStack) {
        if (itemStack == null) return;
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        int yAdd = (-16 * i) + 48;

        if (itemStack.getItem().isDamageable()) {
            SimplexClient.getInstance().getSmoothFont().drawString((itemStack.getMaxDamage() - itemStack.getItemDamage()) + "", getX() + 20, getY() + yAdd, -1);
        }

        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, getX(), getY() + yAdd - 3);
        mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, itemStack,  getX(), getY() + yAdd - 3, "");

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    @Override
    public int getWidth() {
        return 45;
    }

    @Override
    public int getHeight() {
        return 54;
    }
}
