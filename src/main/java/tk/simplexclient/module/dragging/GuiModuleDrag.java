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
import java.util.Optional;

public class GuiModuleDrag extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();

    public Optional<ModuleCreator> selected = Optional.empty();

    private int prevX;
    private int prevY;

    @Override
    public void initGui() {
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
        this.buttonList.add(new RoundedButton(0, this.width / 2 - 40, this.height / 2 - 7, 80, 15, "Mods"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (ModuleCreator m : SimplexClient.getInstance().getModuleManager().getModules()) {
            if (m.isEnabled()) {
                m.renderDummy();
            }
        }
        selected.ifPresent(m -> {
            m.setX(mouseX + m.getX() - prevX);
            m.setY(mouseY + m.getY() - prevY);

            prevX = mouseX;
            prevY = mouseY;
        });
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
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        prevX = mouseX;
        prevY = mouseY;

        selected =
                SimplexClient
                .getInstance()
                .getModuleManager()
                .getModules()
                .stream()
                .filter(m ->
                        m.isEnabled()
                        && mouseX >= m.getX()
                        && mouseX <= m.getX() + m.getWidth()
                        && mouseY >= m.getY()
                        && mouseY <= m.getY() + m.getHeight())
                .findFirst();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        selected = Optional.empty();
        prevX = 0;
        prevY = 0;

    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
        SimplexClient.getInstance().getModuleConfig().saveModuleConfig();
    }
}
