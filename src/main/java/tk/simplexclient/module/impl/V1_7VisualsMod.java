package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.access.AccessEntityLivingBase;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.Listener;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.event.impl.TransformFirstPersonItemEvent;
import tk.simplexclient.module.ModuleCreator;

/**
 * Sol Client - an open source Minecraft client
 * Copyright (C) 2020-2022  TheKodeToad and Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * @author TheKodeToad
 */
public class V1_7VisualsMod extends ModuleCreator implements Listener {

    private final Minecraft mc = Minecraft.getMinecraft();

    public V1_7VisualsMod() {
        super(7, "1.7-visuals", 120, 120);
    }

    @Override
    public void render() {

    }

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (mc.thePlayer != null && mc.thePlayer.capabilities.allowEdit && isEnabled() && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && mc.thePlayer != null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.gameSettings.keyBindUseItem.isKeyDown() && mc.thePlayer.getItemInUseCount() > 0) {
            if ((!mc.thePlayer.isSwingInProgress || mc.thePlayer.swingProgressInt >= ((AccessEntityLivingBase) mc.thePlayer).accessArmSwingAnimationEnd() / 2 || mc.thePlayer.swingProgressInt < 0)) {
                mc.thePlayer.swingProgressInt = -1;
                mc.thePlayer.isSwingInProgress = true;
            }

            mc.effectRenderer.addBlockHitEffects(mc.objectMouseOver.getBlockPos(), mc.objectMouseOver.sideHit);
        }
    }

    @EventTarget
    public void onItemTransform(TransformFirstPersonItemEvent event) {
        // https://github.com/sp614x/optifine/issues/2098
        if (mc.thePlayer.isUsingItem() && event.itemToRender.getItem() instanceof ItemBow) {
            GlStateManager.translate(-0.01f, 0.05f, -0.06f);
        } else if (event.itemToRender.getItem() instanceof ItemFishingRod) {
            GlStateManager.translate(0.08f, -0.027f, -0.33f);
            GlStateManager.scale(0.93f, 1.0f, 1.0f);
        }
    }

    public static void oldDrinking(ItemStack itemToRender, AbstractClientPlayer clientPlayer, float partialTicks) {
        float var14 = clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
        float var15 = 1.0F - var14 / itemToRender.getMaxItemUseDuration();
        float var16 = 1.0F - var15;
        var16 = var16 * var16 * var16;
        var16 = var16 * var16 * var16;
        var16 = var16 * var16 * var16;
        var16 -= 0.05F;
        float var17 = 1.0F - var16;
        GlStateManager.translate(0.0F, MathHelper.abs(MathHelper.cos(var14 / 4F * (float) Math.PI) * 0.11F)
                * (float) ((double) var15 > 0.2D ? 1 : 0), 0.0F);
        GlStateManager.translate(var17 * 0.6F, -var17 * 0.5F, 0.0F);
        GlStateManager.rotate(var17 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(var17 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(var17 * 30.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0, -0.0F, 0.06F);
        GlStateManager.rotate(-4F, 1, 0, 0);
    }

    public static void oldBlocking() {
        GlStateManager.scale(0.83F, 0.88F, 0.85F);
        GlStateManager.translate(-0.3F, 0.1F, 0.0F);
    }
}
