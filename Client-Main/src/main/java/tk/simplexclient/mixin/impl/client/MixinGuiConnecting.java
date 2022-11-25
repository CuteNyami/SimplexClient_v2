package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.impl.ServerJoinEvent;
import tk.simplexclient.utils.CapeUtils;
import tk.simplexclient.utils.ConnectingUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting extends GuiScreen {

    @Shadow
    @Final
    private static Logger logger;

    @Shadow
    @Final
    private static AtomicInteger CONNECTION_ID;

    @Shadow
    @Final
    private GuiScreen previousGuiScreen;

    @Shadow
    private boolean cancel;

    @Shadow
    private NetworkManager networkManager;

    @Inject(method = "<init>(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/multiplayer/ServerData;)V", at = @At("RETURN"))
    private void init(GuiScreen p_i1181_1_, Minecraft mcIn, ServerData p_i1181_3_, CallbackInfo ci) {
        ConnectingUtils.lastServer = p_i1181_3_;
        ConnectingUtils.lastGuiScreen = p_i1181_1_;
    }

    /**
     * @author CuteNyami
     * @reason ServerJoinEvent
     */
    @Overwrite
    private void connect(final String ip, final int port) {
        logger.info("Connecting to " + ip + ", " + port);

        (new Thread("Server Connector #" + CONNECTION_ID.incrementAndGet()) {
            public void run() {
                InetAddress inetaddress = null;

                try {
                    if (cancel) {
                        return;
                    }

                    inetaddress = InetAddress.getByName(ip);
                    networkManager = NetworkManager.func_181124_a(inetaddress, port, mc.gameSettings.func_181148_f());
                    networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, mc, previousGuiScreen));
                    networkManager.sendPacket(new C00Handshake(47, ip, port, EnumConnectionState.LOGIN));
                    networkManager.sendPacket(new C00PacketLoginStart(mc.getSession().getProfile()));
                    new ServerJoinEvent(mc.getCurrentServerData().serverIP, port).call();
                } catch (UnknownHostException unknownhostexception) {
                    if (cancel) {
                        return;
                    }

                    logger.error("Couldn't connect to server", unknownhostexception);
                    mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", "Unknown host")));
                } catch (Exception exception) {
                    if (cancel) {
                        return;
                    }

                    logger.error("Couldn't connect to server", exception);
                    String s = exception.toString();

                    if (inetaddress != null) {
                        String s1 = inetaddress + ":" + port;
                        s = s.replaceAll(s1, "");
                    }

                    mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed", new ChatComponentTranslation("disconnect.genericReason", s)));
                }
            }
        }).start();
    }

}
