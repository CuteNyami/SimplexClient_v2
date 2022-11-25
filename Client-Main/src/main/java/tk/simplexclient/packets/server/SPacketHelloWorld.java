package tk.simplexclient.packets.server;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import tk.simplexclient.packets.packet.Packet;

public class SPacketHelloWorld extends Packet {

    public SPacketHelloWorld() {
        super("simplex_client:api");
    }

    @Override
    public void handlePacket(S3FPacketCustomPayload packet) {
        if (getChannel().equals(packet.getChannelName())) {
            PacketBuffer buffer = packet.getBufferData();
            String message = buffer.readStringFromBuffer(32767);
            System.out.println(message);
        }
    }
}
