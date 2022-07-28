package tk.simplexclient.gui.mod;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.ModMenu;
import tk.simplexclient.gui.elements.GuiSlider;
import tk.simplexclient.gui.elements.ToggleBoxButton;
import tk.simplexclient.gui.elements.ToggleButton;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;
import tk.simplexclient.module.settings.SettingsManager;
import tk.simplexclient.shader.RoundedShaderRenderer;
import tk.simplexclient.ui.buttons.round.ImageButton;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GuiModSettings extends GuiScreen {

    @Getter
    private final ModuleCreator module;

    private SettingsManager builder;

    public GuiModSettings(ModuleCreator module) {
        this.module = module;
    }

    private FontRenderer fontRenderer;

    private ScaledResolution sr;

    @Override
    public void initGui() {
        sr = new ScaledResolution(Minecraft.getMinecraft());
        fontRenderer = new FontRenderer("smooth", 20.0F);
        builder = SimplexClient.getInstance().getSettingsBuilder();

        builder.getSliderMap().clear();
        builder.getSliderList().clear();

        builder.getToggleMap().clear();
        builder.getToggleList().clear();

        int y = 0;

        Field[] fields = module.getClass().getDeclaredFields();
        for (Field field : fields) {
            Option optionAnnotation = field.getAnnotation(Option.class);

            if (optionAnnotation != null) {
                if (field.getType() == int.class || field.getType() == float.class || field.getType() == double.class) {
                    GuiSlider slider = new GuiSlider(3, this.width / 2 - 115, (this.height / 2 - 50) + y, 100, 20, optionAnnotation.text(), " ", optionAnnotation.min(), optionAnnotation.max(), builder.getSliderValue(module, field), false, true, field);
                    builder.getSliderMap().put(field, slider);
                    builder.getSliderList().add(slider);
                }
                if (field.getType() == boolean.class) {
                    ToggleBoxButton toggleButton = new ToggleBoxButton(this.width / 2 - 115, (this.height / 2 - 50) + y, 10, optionAnnotation.text(), builder.getToggleValue(module, field));
                    builder.getToggleMap().put(field, toggleButton);
                    builder.getToggleList().add(toggleButton);
                }
                y += 20;
            }
        }
        buttonList.add(new ImageButton(0, width / 2 + 110, height / 2 - 72, 10, 10, 5, "close"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float bgX = (float) this.width / 2;
        float bgY = (float) this.height / 2;
        float bgWidth = 200;
        float bgHeight = 150;

        /* Mod Menu Background */
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - (bgWidth / 2), bgY - (bgHeight / 2), bgWidth, bgHeight, 5F, new Color(40, 40, 40));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 75 - ((bgWidth - 150) / 2), bgY - (bgHeight / 2), bgWidth - 150, bgHeight, 5, new Color(30, 30, 30));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 55 - ((bgWidth - 190) / 2), bgY - (bgHeight / 2), bgWidth - 190, bgHeight, 0, new Color(30, 30, 30));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX + 60 - (bgWidth / 2), bgY + 10 - (bgHeight / 2), bgWidth - 70, bgHeight - 20, 5F, new Color(30, 30, 30));

        fontRenderer.drawString("Simplex", this.width / 2 - 92, this.height / 2 - 69, new Color(170, 170, 170).getRGB());

        /*
        for (GuiSlider slider : builder.getSliderList()) {
            slider.drawButton(mc, mouseX, mouseY);
        }

        for (ToggleBoxButton toggle : builder.getToggleList()) {
            toggle.drawButton(mc, mouseX, mouseY);
        }

         */

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiSlider slider : builder.getSliderList()) {
            slider.mousePressed(mc, mouseX, mouseY);
        }
        for (ToggleBoxButton toggle : builder.getToggleList()) {
            toggle.onClick();
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (GuiSlider slider : builder.getSliderList()) {
            slider.mouseReleased(mouseX, mouseY);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void onGuiClosed() {
        Field[] fields = module.getClass().getDeclaredFields();
        for (Field field : fields) {
            Option optionAnnotation = field.getAnnotation(Option.class);
            if (optionAnnotation != null && (field.getType() == int.class || field.getType() == float.class || field.getType() == double.class)) {
                SimplexClient.getInstance().getModuleConfig().append(field.toString(), builder.getSliderMap().get(field).getValue());
                SimplexClient.getInstance().getModuleConfig().save();
            }
            if (optionAnnotation != null && field.getType() == boolean.class) {
                SimplexClient.getInstance().getModuleConfig().append(field.toString(), builder.getToggleMap().get(field).isToggled());
                SimplexClient.getInstance().getModuleConfig().save();
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(null);
        }
        super.actionPerformed(button);
    }
}
