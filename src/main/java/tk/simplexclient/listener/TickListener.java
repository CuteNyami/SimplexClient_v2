package tk.simplexclient.listener;

import net.minecraft.client.Minecraft;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.Listener;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.module.dragging.GuiModuleDrag;

public class TickListener implements Listener {

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (SimplexClient.getInstance().CLICK_GUI.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiModuleDrag());
        }
    }
}
