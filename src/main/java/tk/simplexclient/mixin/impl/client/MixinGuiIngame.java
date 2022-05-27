package tk.simplexclient.mixin.impl.client;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.spongepowered.asm.mixin.Overwrite;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gui.mod.GuiModMenu;
import tk.simplexclient.module.ModuleCreator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.module.dragging.GuiModuleDrag;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    public void renderModules(float partialTicks, CallbackInfo ci) {
        if (!(mc.currentScreen instanceof GuiModuleDrag) && !mc.gameSettings.showDebugInfo) {
            for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
                if (module.isEnabled()) {
                    module.render();
                }
            }
        }
    }

    /**
     * @author CuteNyami
     */
    @Overwrite
    public boolean showCrosshair() {
        if (mc.currentScreen instanceof GuiModMenu || mc.currentScreen instanceof GuiModuleDrag) {
            return false;
        } else if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
            return false;
        } else if (this.mc.playerController.isSpectator()) {
            if (this.mc.pointedEntity != null) {
                return true;
            } else {
                if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
                    return this.mc.theWorld.getTileEntity(blockpos) instanceof IInventory;
                }

                return false;
            }
        } else {
            return true;
        }
    }

}
