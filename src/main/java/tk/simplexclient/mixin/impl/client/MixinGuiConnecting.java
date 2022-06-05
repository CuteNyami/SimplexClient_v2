package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.utils.ConnectingUtils;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting extends GuiScreen {

    @Inject(method = "<init>(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/multiplayer/ServerData;)V", at = @At("RETURN"))
    private void init(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_, CallbackInfo ci) {
        ConnectingUtils.lastServer = p_i1181_3_;
        ConnectingUtils.lastGuiScreen = p_i1181_1_;
    }

}
