package tk.simplexclient.module.dragging;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gui.mod.GuiModMenu;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.ui.buttons.round.RoundedButton;

import java.io.IOException;

public class GuiModuleDrag extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void initGui() {
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
        this.buttonList.add(new RoundedButton(0, this.width / 2 - 40, this.height / 2 - 7, 80, 15, "Mods"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (ModuleCreator m : SimplexClient.getInstance().getModuleManager().getModules()) {
            if (m.isEnabled()) {
                m.renderDummy(mouseX, mouseY);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiModMenu());
        }
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
        SimplexClient.getInstance().getModuleConfig().saveModuleConfig();
    }
}
