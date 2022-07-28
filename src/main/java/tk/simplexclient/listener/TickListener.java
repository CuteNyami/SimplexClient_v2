package tk.simplexclient.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.Listener;
import tk.simplexclient.event.impl.ClientTickEvent;
import tk.simplexclient.event.impl.EventUpdate;
import tk.simplexclient.gui.ModMenu;
import tk.simplexclient.gui.mod.TestGui;
import tk.simplexclient.module.dragging.GuiModuleDrag;
import tk.simplexclient.module.impl.ToggleSprint;

public class TickListener implements Listener {

    private final Minecraft mc = Minecraft.getMinecraft();

    @EventTarget
    public void onTick(ClientTickEvent event) {
        if (SimplexClient.getInstance().CLICK_GUI.isPressed()) {
            mc.displayGuiScreen(new GuiModuleDrag());
        }
        if (SimplexClient.getInstance().TEST_GUI.isPressed()) {
            mc.displayGuiScreen(new TestGui());
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {

    }
}
