package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.gui.GuiSelectWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.module.impl.DiscordRP;

@Mixin(GuiSelectWorld.class)
public class MixinGuiSelectWorld {

    @Inject(method = "initGui", at = @At("HEAD"))
    public void initGui(CallbackInfo ci) {
        //DiscordRP.update("Idle", "Singleplayer Menu");
    }

}
