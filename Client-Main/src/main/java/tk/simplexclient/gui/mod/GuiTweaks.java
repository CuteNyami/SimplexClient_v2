package tk.simplexclient.gui.mod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gui.elements.ColorPicker;
import tk.simplexclient.gui.elements.GuiSlider;
import tk.simplexclient.module.settings.SettingsManager;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiTweaks extends GuiScreen {

    public static GuiSlider slider;
    public static double val = 0.4F;

    private SettingsManager builder;

    private ScaledResolution sr;

    private FontRenderer fontRenderer;

    private final ArrayList<ColorPicker> colorPickers = new ArrayList<>();

    @Override
    public void initGui() {
        sr = new ScaledResolution(mc);
        //this.buttonList.add(new ImageButton(0, width / 2 + 110, height / 2 - 72, 10, 10, 5, "close"));
        slider = new GuiSlider(3, this.width / 2 - 30, this.height / 2 - 50, 100, 20, "Item size ", "", 0.1D, 10.0D, SimplexClient.getInstance().getModuleConfig().getItemSize(), false, false);
        fontRenderer = new FontRenderer("smooth", 20.0F);

        this.buttonList.add(slider);
        this.colorPickers.clear();
        //this.colorPickers.add(new ColorPicker(this.width / 2 - 27, this.height / 2 - 30, 60, 50, true));

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        val = slider.getValue();

        float bgX = (float) this.width / 2;
        float bgY = (float) this.height / 2;
        float bgWidth = 200;
        float bgHeight = 150;

        /* Mod Menu Background */
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - (bgWidth / 2), bgY - (bgHeight / 2), bgWidth, bgHeight, 5F, new Color(40, 40, 40));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 75 - ((bgWidth - 150) / 2), bgY - (bgHeight / 2), bgWidth - 150, bgHeight, 5, new Color(30, 30, 30));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 55 - ((bgWidth - 190) / 2), bgY - (bgHeight / 2), bgWidth - 190, bgHeight, 0, new Color(30, 30, 30));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX + 60 - (bgWidth / 2), bgY + 10 - (bgHeight / 2), bgWidth - 70, bgHeight - 20, 5F, new Color(30, 30, 30));

        SimplexClient.getInstance().getSmoothFont().drawString("Item Size", this.width / 2 - 30, this.height / 2 - 60, new Color(170, 170, 170).getRGB());
        fontRenderer.drawString("Simplex \nTweaks", this.width / 2 - 92, this.height / 2 - 69, new Color(170, 170, 170).getRGB());

        for (ColorPicker colorPicker : colorPickers) {
            colorPicker.init();
            colorPicker.render(mouseX, mouseY);
        }

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

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (ColorPicker colorPicker : colorPickers) {
            colorPicker.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (ColorPicker colorPicker : colorPickers) {
            colorPicker.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
}
