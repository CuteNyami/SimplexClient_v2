package tk.simplexclient.listener;

import net.minecraft.client.Minecraft;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.Listener;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.event.impl.EventUpdate;
import tk.simplexclient.event.impl.ServerJoinEvent;
import tk.simplexclient.module.dragging.GuiModuleDrag;

public class TickListener implements Listener {

    private final Minecraft mc = Minecraft.getMinecraft();

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (SimplexClient.getInstance().CLICK_GUI.isPressed()) {
            mc.displayGuiScreen(new GuiModuleDrag());
        }
    }
}
