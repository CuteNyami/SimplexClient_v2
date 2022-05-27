package tk.simplexclient.mixin.impl.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.access.AccessMinecraft;
import tk.simplexclient.module.impl.V1_7VisualsMod;

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
public abstract class MixinV1_7VisualsMod {

    @Mixin(ItemRenderer.class)
    public static abstract class MixinItemRenderer {

        @Redirect(method = "renderItemInFirstPerson", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;transformFirstPersonItem(FF)V"))
        public void allowUseAndSwing(ItemRenderer itemRenderer, float equipProgress, float swingProgress) {
            transformFirstPersonItem(equipProgress,
                    swingProgress == 0.0F && SimplexClient.getInstance().getModuleManager().v1_7Visual.isEnabled() ?
                            mc.thePlayer.getSwingProgress(AccessMinecraft.getInstance().getTimerSC().renderPartialTicks) :
                            swingProgress);
        }

        @Inject(method = "func_178103_d", at = @At("RETURN"))
        public void oldBlocking(CallbackInfo callback) {
            if(SimplexClient.getInstance().getModuleManager().v1_7Visual.isEnabled()) {
                V1_7VisualsMod.oldBlocking();
            }
        }

        @Inject(method = "func_178104_a", at = @At("HEAD"), cancellable = true)
        public void oldDrinking(AbstractClientPlayer clientPlayer, float partialTicks, CallbackInfo callback) {
            if(SimplexClient.getInstance().getModuleManager().v1_7Visual.isEnabled()) {
                callback.cancel();
                V1_7VisualsMod.oldDrinking(itemToRender, clientPlayer, partialTicks);
            }
        }

        @Shadow
        public abstract void transformFirstPersonItem(float equipProgress, float swingProgress);

        @Shadow
        @Final
        public
        Minecraft mc;

        @Shadow
        public ItemStack itemToRender;

    }

    @Mixin(EntityRenderer.class)
    public static abstract class MixinEntityRenderer {

        private float eyeHeightSubtractor;
        private long lastEyeHeightUpdate;

        @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
        public float smoothSneaking(Entity entity) {
            if(SimplexClient.getInstance().getModuleManager().v1_7Visual.isEnabled()
                    && entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                float height = player.getEyeHeight();
                if(player.isSneaking()) {
                    height += 0.08F;
                }
                float actualEyeHeightSubtractor = player.isSneaking() ? 0.08F : 0;
                long sinceLastUpdate = System.currentTimeMillis() - lastEyeHeightUpdate;
                lastEyeHeightUpdate = System.currentTimeMillis();
                if(actualEyeHeightSubtractor > eyeHeightSubtractor) {
                    eyeHeightSubtractor += sinceLastUpdate / 500f;
                    if(actualEyeHeightSubtractor < eyeHeightSubtractor) {
                        eyeHeightSubtractor = actualEyeHeightSubtractor;
                    }
                }
                else if(actualEyeHeightSubtractor < eyeHeightSubtractor) {
                    eyeHeightSubtractor -= sinceLastUpdate / 500f;
                    if(actualEyeHeightSubtractor > eyeHeightSubtractor) {
                        eyeHeightSubtractor = actualEyeHeightSubtractor;
                    }
                }
                return height - eyeHeightSubtractor;
            }
            return entity.getEyeHeight();
        }

        @Shadow
        public Minecraft mc;

    }

    @Mixin(LayerArmorBase.class)
    public static class MixinLayerArmorBase {

        @Inject(method = "shouldCombineTextures", at = @At("HEAD"), cancellable = true)
        public void oldArmour(CallbackInfoReturnable<Boolean> callback) {
            if(SimplexClient.getInstance().getModuleManager().v1_7Visual.isEnabled()) {
                callback.setReturnValue(true);
            }
        }

    }
}
