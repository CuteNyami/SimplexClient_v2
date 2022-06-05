package tk.simplexclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

public class ConnectingUtils {

    public static ServerData lastServer = null;

    public static GuiScreen lastGuiScreen = null;

    public static GuiScreen connect() {
        if (lastServer == null || lastGuiScreen == null) return null;
        return new GuiConnecting(lastGuiScreen, Minecraft.getMinecraft(), lastServer);
    }

}
