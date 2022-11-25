package tk.simplexclient.packets.packet;

import tk.simplexclient.packets.packet.Packet;
import tk.simplexclient.packets.server.SPacketHelloWorld;

import java.util.ArrayList;
import java.util.List;

public class PacketManager {

    public static List<Packet> packetList = new ArrayList<>();

    public static void registerPackets() {
        packetList.add(new SPacketHelloWorld());
    }

}
