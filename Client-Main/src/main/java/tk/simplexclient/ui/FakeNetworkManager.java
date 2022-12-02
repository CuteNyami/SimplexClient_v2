package tk.simplexclient.ui;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;

public class FakeNetworkManager extends NetworkManager {
    public FakeNetworkManager(EnumPacketDirection packetDirection) {
        super(packetDirection);
    }
}
