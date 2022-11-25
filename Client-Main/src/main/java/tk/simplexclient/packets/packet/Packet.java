package tk.simplexclient.packets.packet;

import lombok.Getter;
import net.minecraft.network.play.server.S3FPacketCustomPayload;

public abstract class Packet {

    @Getter
    private final String channel;
    public Packet(String channel) {
        this.channel = channel;
    }

    public abstract void handlePacket(S3FPacketCustomPayload packet);

}
