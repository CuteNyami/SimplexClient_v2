package tk.simplexclient.module.dragging;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.*;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.access.AccessEntityRenderer;
import tk.simplexclient.gui.DeprecatedModMenu;
import tk.simplexclient.gui.ModMenu;
import tk.simplexclient.gui.mod.GuiModMenu;
import tk.simplexclient.gui.mod.GuiTweaks;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.ui.buttons.round.RoundedButton;

import java.io.IOException;
import java.util.Optional;

public class GuiModuleDrag extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();

    public Optional<ModuleCreator> selected = Optional.empty();

    private int prevX;
    private int prevY;

    private ScaledResolution res = new ScaledResolution(mc);

    @Override
    public void initGui() {
        res = new ScaledResolution(mc);
        /*
            ((AccessEntityRenderer) mc.entityRenderer).loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
         */
        this.buttonList.add(new RoundedButton(0, this.width / 2 - 40, this.height / 2 - 7, 80, 15, "Mods"));
        //this.buttonList.add(new RoundedButton(1, this.width / 2 + 42, this.height / 2 - 7, 15, 15, "..."));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glEnable(GL11.GL_BLEND);
        for (ModuleCreator m : SimplexClient.getInstance().getModuleManager().getModules()) {
            if (m.isEnabled()) {
                m.renderDummy(this.width, this.height);
            }
        }
        selected.ifPresent(m -> {
           // m.setPos(mouseX + m.x - prevX, mouseY + m.getY() - prevY);
            m.setX(mouseX + m.x - prevX);
            m.setY(mouseY + m.y - prevY);
            prevX = mouseX;
            prevY = mouseY;
        });
        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(new DeprecatedModMenu());
        }
        super.actionPerformed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        prevX = mouseX;
        prevY = mouseY;

        selected = SimplexClient
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
        SimplexClient.getInstance().getModuleConfig().saveModuleConfig();
    }
}