package tk.simplexclient.gui.mod;

import lombok.Getter;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.elements.GuiSlider;
import tk.simplexclient.gui.elements.ToggleButton;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;
import tk.simplexclient.module.settings.SettingsManager;
import tk.simplexclient.ui.buttons.round.ImageButton;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;

public class GuiModSettings extends GuiScreen {

    @Getter
    private final ModuleCreator module;

    private SettingsManager builder;

    public GuiModSettings(ModuleCreator module) {
        this.module = module;
    }

    @Override
    public void initGui() {
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
                    ToggleButton toggleButton = new ToggleButton(this.width / 2 - 115, (this.height / 2 - 50) + y, 10, optionAnnotation.text(), builder.getToggleValue(module, field));
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
        GLRectUtils.drawRect((float) width / 2 - 125, (float) height / 2 - 75, (float) (width / 2 - 125) + 250, (float) (height / 2 - 75) + 150, new Color(0, 0, 0, 140).getRGB());
        GLRectUtils.drawRoundedOutline((float) width / 2 - 125, (float) height / 2 - 75, (float) (width / 2 - 125) + 250, (float) (height / 2 - 75) + 150, 1.5F, 3.0F, new Color(255, 255, 255, 140).getRGB());

        GLRectUtils.drawRect((float) width / 2 - 125, (float) height / 2 - 60, (float) (width / 2 - 125) + 250, (float) (height / 2 - 60) + 1.5F, new Color(255, 255, 255, 140).getRGB());
        SimplexClient.getInstance().getSmoothFont().drawString(module.getName().toLowerCase() + " Settings", width / 2 - 113, height / 2 - 72, -1);

        for (GuiSlider slider : builder.getSliderList()) {
            slider.drawButton(mc, mouseX, mouseY);
        }

        for (ToggleButton toggle : builder.getToggleList()) {
            toggle.drawButton(mc, mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiSlider slider : builder.getSliderList()) {
            slider.mousePressed(mc, mouseX, mouseY);
        }
        for (ToggleButton toggle : builder.getToggleList()) {
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
