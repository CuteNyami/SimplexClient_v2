package tk.simplexclient.gui.mod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.elements.GuiSlider;
import tk.simplexclient.module.settings.SettingsManager;
import tk.simplexclient.ui.buttons.round.ImageButton;

import java.awt.*;
import java.io.IOException;

public class GuiTweaks extends GuiScreen {

    public static GuiSlider slider;
    public static double val = 0.4F;

    private SettingsManager builder;

    @Override
    public void initGui() {
        this.buttonList.add(new ImageButton(0, width / 2 + 110, height / 2 - 72, 10, 10, 5, "close"));
        slider = new GuiSlider(3, this.width / 2 - 115, this.height / 2 - 60, 100, 20, "Item size ", "", 0.1D, 10.0D, SimplexClient.getInstance().getModuleConfig().getItemSize(), false, true);
        this.buttonList.add(slider);
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        val = slider.getValue();
        GLRectUtils.drawRect((float) width / 2 - 125, (float) height / 2 - 75, (float) (width / 2 - 125) + 250, (float) (height / 2 - 75) + 150, new Color(0, 0, 0, 140).getRGB());
        GLRectUtils.drawRoundedOutline((float) width / 2 - 125, (float) height / 2 - 75, (float) (width / 2 - 125) + 250, (float) (height / 2 - 75) + 150, 1.5F, 3.0F, new Color(255, 255, 255, 140).getRGB());

        GLRectUtils.drawRect((float) width / 2 - 125, (float) height / 2 - 60, (float) (width / 2 - 125) + 250, (float) (height / 2 - 60) + 1.5F, new Color(255, 255, 255, 140).getRGB());
        SimplexClient.getInstance().getSmoothFont().drawString("Minecraft Tweaks", width / 2 - 113, height / 2 - 72, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }



    @Override
    public void onGuiClosed() {
        SimplexClient.getInstance().getModuleConfig().saveItemSize(val);
        super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(null);
        }
        super.actionPerformed(button);
    }
}
