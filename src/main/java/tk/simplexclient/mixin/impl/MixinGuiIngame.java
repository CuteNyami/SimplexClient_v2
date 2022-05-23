package tk.simplexclient.mixin.impl;

import tk.simplexclient.SimplexClient;
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

    private final Minecraft MC = Minecraft.getMinecraft();

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    public void renderModules(float partialTicks, CallbackInfo ci) {
        if (!(MC.currentScreen instanceof GuiModuleDrag) && !MC.gameSettings.showDebugInfo) {
            for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModule()) {
                if (module.isEnabled()) {
                    module.render();
                }
            }
        }
    }

}
