package tk.simplexclient.module.dragging;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.module.ModuleCreator;

public class GuiModuleDrag extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void initGui() {
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (ModuleCreator m : SimplexClient.getInstance().getModuleManager().getModule()) {
            if (m.isEnabled()) {
                m.renderDummy(mouseX, mouseY);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
    }
}
