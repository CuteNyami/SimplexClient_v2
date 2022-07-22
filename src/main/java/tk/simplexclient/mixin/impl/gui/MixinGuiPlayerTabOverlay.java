package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tk.simplexclient.SimplexClient;

import java.awt.*;

@Mixin(GuiPlayerTabOverlay.class)
public class MixinGuiPlayerTabOverlay {

    @Redirect(method = "renderPlayerlist", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiPlayerTabOverlay;drawPing(IIILnet/minecraft/client/network/NetworkPlayerInfo;)V"))
    public void writePing(GuiPlayerTabOverlay instance, int p_175245_1_, int p_175245_2_, int p_175245_3_, NetworkPlayerInfo networkPlayerInfoIn) {
        int pingStringWidth = (int) SimplexClient.getInstance().getSmoothFont().getWidth("1000");
        int pingColor = new Color(0,0,0).getRGB();

        //networkPlayerInfoIn.getResponseTime() + ""

        int textX = p_175245_1_ + p_175245_2_ - pingStringWidth - 1;

        if(networkPlayerInfoIn.getResponseTime() < 150) pingColor = new Color(68, 183, 69).getRGB();
        if(networkPlayerInfoIn.getResponseTime() >= 150) pingColor = new Color(0, 112, 0).getRGB();
        if(networkPlayerInfoIn.getResponseTime() > 299) pingColor = new Color(221, 67, 67).getRGB();

        SimplexClient.getInstance().getSmoothFont().drawStringWithShadow("1000", textX, p_175245_3_, pingColor);
    }
}
