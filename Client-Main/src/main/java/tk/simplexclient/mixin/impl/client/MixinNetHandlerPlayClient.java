package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.impl.EntityDamageEvent;
import tk.simplexclient.packets.packet.Packet;
import tk.simplexclient.packets.packet.PacketManager;
import tk.simplexclient.utils.CapeUtils;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Shadow
    private WorldClient clientWorldController;

    @Inject(method = "handleEntityStatus", at = @At("RETURN"))
    public void handleEntityStatus(S19PacketEntityStatus packetIn, CallbackInfo callback) {
        if(packetIn.getOpCode() == 2) {
            new EntityDamageEvent(packetIn.getEntity(clientWorldController)).call();
        }
    }

    @Inject(method = "handleCustomPayload", at = @At("TAIL"))
    public void inject(S3FPacketCustomPayload packetIn, CallbackInfo ci) {
        for (Packet packet : PacketManager.packetList) {
            packet.handlePacket(packetIn);
        }
    }

    @Inject(method = "handleJoinGame", at = @At("TAIL"))
    public void handleJoinGame(S01PacketJoinGame packetIn, CallbackInfo ci) {
        SimplexClient.getInstance().setHaveCape(CapeUtils.haveCape(Minecraft.getMinecraft().getSession().getUsername()));
        SimplexClient.getInstance().sendPayloadToServer("REGISTER", "simplex_client:api");
        SimplexClient.getInstance().sendPayloadToServer("simplex_client:api", "[SimplexClient] Sending packet to Server!");
    }

}
