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
        int pingColor = new Color(0,0,0).getRGB();

        int pingStringWidth = (int) SimplexClient.getInstance().getSmoothFont().getWidth(String.valueOf(networkPlayerInfoIn.getResponseTime()));
        int textX = p_175245_1_ + p_175245_2_ - pingStringWidth;

        if(networkPlayerInfoIn.getResponseTime() < 150) pingColor = new Color(68, 183, 69).getRGB();
        if(networkPlayerInfoIn.getResponseTime() >= 150) pingColor = new Color(0, 112, 0).getRGB();
        if(networkPlayerInfoIn.getResponseTime() > 299) pingColor = new Color(221, 67, 67).getRGB();

        SimplexClient.getInstance().getSmoothFont().drawString(String.valueOf(networkPlayerInfoIn.getResponseTime()), p_175245_2_ + p_175245_1_ - SimplexClient.getInstance().getSmoothFont().getStringWidth(String.valueOf(networkPlayerInfoIn.getResponseTime())) - 1, p_175245_3_, pingColor);
    }
}
