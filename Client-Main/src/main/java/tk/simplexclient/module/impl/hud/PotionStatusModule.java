package tk.simplexclient.module.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gl.GlStateManager;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class PotionStatusModule extends ModuleCreator {

    private final Minecraft mc = Minecraft.getMinecraft();

    Collection<PotionEffect> effects;

    public PotionStatusModule() {
        super("potion-status", 0, 0);
    }

    @Override
    public void render() {
        effects = null;
        drawActivePotionEffects(false);
    }

    @Override
    public void renderDummy(int width, int height) {
        effects = null;
        drawActivePotionEffects(true);
    }

    private void drawActivePotionEffects(boolean dummy) {
        Gui gui = new Gui();

        if (dummy || mc.thePlayer == null) {
            effects = Arrays.asList(new PotionEffect(1, 0), new PotionEffect(5, 0));
        } else {
            GlStateManager.enableBlend();
            effects = mc.thePlayer.getActivePotionEffects();
        }

        if (!effects.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int potionX = x;
            int potionY = y;
            int l = 34;

            for (PotionEffect potioneffect : effects) {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                if (!dummy) {
                    GLRectUtils.drawRect(potionX, potionY, potionX + 90, potionY + 32, new Color(0, 0, 0, 140).getRGB());
                } else {
                    GLRectUtils.drawRectOutline(potionX, potionY, potionX + 90, potionY + 32, 0.25f, new Color(0, 0, 0, 160).getRGB());
                    GLRectUtils.drawRect(potionX, potionY, potionX + 90, potionY + 32, new Color(255, 255, 255, 70).getRGB());
                }
                GLRectUtils.drawShadow(potionX, potionY, 90, 32);

                this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));

                if (potion.hasStatusIcon()) {
                    int i1 = potion.getStatusIconIndex();
                    gui.drawTexturedModalRect(potionX + 6, potionY + 7, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }

                String s1 = I18n.format(potion.getName());

                if (potioneffect.getAmplifier() == 1) {
                    s1 = s1 + " " + I18n.format("enchantment.level.2");
                } else if (potioneffect.getAmplifier() == 2) {
                    s1 = s1 + " " + I18n.format("enchantment.level.3");
                } else if (potioneffect.getAmplifier() == 3) {
                    s1 = s1 + " " + I18n.format("enchantment.level.4");
                }

                fr.drawString(s1, (float) (potionX + 10 + 18), (float) (potionY + 6), 16777215);
                String s = Potion.getDurationString(potioneffect);
                fr.drawString(s, (float) (potionX + 10 + 18), (float) (potionY + 6 + 10), 8355711);
                potionY += l;
            }
        }
    }

    @Override
    public int getX() {
        return super.getX();
    }

    @Override
    public int getHeight() {
        return 32 * effects.size();
    }

    @Override
    public int getWidth() {
        return 140;
    }
}
