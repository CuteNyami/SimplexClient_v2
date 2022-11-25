package tk.simplexclient.gui.mod.settings;

import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.animations.Delta;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.elements.ColorPicker;
import tk.simplexclient.gui.elements.GuiSlider;
import tk.simplexclient.gui.elements.ToggleBoxButton;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;
import tk.simplexclient.module.settings.SettingsManager;
import tk.simplexclient.shader.RoundedShaderRenderer;
import tk.simplexclient.ui.elements.InputField;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;

public class GuiModSettings extends GuiScreen {

    @Getter
    private static ModuleCreator module = null;

    private SettingsManager builder;

    public GuiModSettings(ModuleCreator module) {
        GuiModSettings.module = module;
    }

    private FontRenderer fontRenderer;

    private FontRenderer smallFontRenderer;

    private ScaledResolution sr;

    private int currentPage = 0;

    private float target, current;

    @SneakyThrows
    @Override
    public void initGui() {
        target = 1f;
        Keyboard.enableRepeatEvents(true);
        sr = new ScaledResolution(Minecraft.getMinecraft());
        fontRenderer = new FontRenderer("smooth", 20.0F);
        smallFontRenderer = new FontRenderer("smooth", 10.0F);
        builder = SimplexClient.getInstance().getSettingsBuilder();

        builder.getSliderMap().clear();
        builder.getSliderList().clear();

        builder.getToggleMap().clear();
        builder.getToggleList().clear();

        builder.getColorMap().clear();
        builder.getColorList().clear();

        builder.getInputMap().clear();
        builder.getInputList().clear();

        int y = 0;
        int toggleY = 0;

        Field[] fields = module.getClass().getDeclaredFields();
        for (Field field : fields) {
            Option optionAnnotation = field.getAnnotation(Option.class);
            if (optionAnnotation != null) {
                if (optionAnnotation.page() == currentPage) {
                    if (field.getType() == int.class || field.getType() == float.class || field.getType() == double.class) {
                        GuiSlider slider = new GuiSlider(3, this.width / 2 - 25, (this.height / 2 - 50) + y + optionAnnotation.y(), 100, 20, optionAnnotation.text(), " ", optionAnnotation.min(), optionAnnotation.max(), builder.getSliderValue(module, field), false, true, field);
                        builder.getSliderMap().put(field, slider);
                        builder.getSliderList().add(slider);
                    }
                    if (field.getType() == boolean.class) {
                        ToggleBoxButton toggleButton = new ToggleBoxButton(this.width / 2 - 25, (this.height / 2 - 50) + y - toggleY + optionAnnotation.y(), 10, optionAnnotation.text(), builder.getToggleValue(module, field));
                        builder.getToggleMap().put(field, toggleButton);
                        builder.getToggleList().add(toggleButton);
                        toggleY += 30;
                    }
                    if (field.getType() == String.class) {
                        field.setAccessible(true);
                        String configText = SimplexClient.getInstance().getModuleConfig().get(field.toString(), String.class);
                        String text = configText != null ? configText : (String) field.get(module);
                        InputField inputField = new InputField(this.width / 2 - 25, (this.height / 2 - 50) + y + optionAnnotation.y(), 60, 20, false, text, "", 30, InputField.InputFlavor.NORMAL);

                        builder.getInputMap().put(field, inputField);
                        builder.getInputList().add(inputField);
                    }
                    if (field.getType() == Color.class) {
                        ColorPicker colorPicker = new ColorPicker(this.width / 2 - 25, (this.height / 2 - 50) + y + optionAnnotation.y(), 60, 30, optionAnnotation.colorPickerAlphaSlider(), optionAnnotation.text());

                        float[] colorFloat = SimplexClient.getInstance().getModuleConfig().get(field.toString(), float[].class);
                        field.setAccessible(true);
                        Color color = (Color) field.get(module);
                        float[] color1 = colorFloat != null ? colorFloat : Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

                        colorPicker.color[0] = color1[0];
                        colorPicker.color[1] = color1[1];
                        colorPicker.color[2] = color1[2];

                        if (colorFloat != null) {
                            colorPicker.color[3] = color1[3];
                        } else {
                            colorPicker.color[3] = color.getAlpha() / 255.0F;
                        }

                        builder.getColorPickerMap().put(field, colorPicker);
                        builder.getColorList().add(colorPicker);
                    }
                    y += 20;
                }
            }
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float bgX = (float) this.width / 2;
        float bgY = (float) this.height / 2;
        float bgWidth = 200;
        float bgHeight = 150;

        float x = bgX - (bgWidth / 2);
        float y = bgY - (bgHeight / 2);

        GL11.glPushMatrix();
        GL11.glTranslated((x + (x + bgWidth)) / 2, (y + (y + bgHeight)) / 2, 0.0);

        GL11.glScaled(current, current, 0.0);
        GL11.glTranslated(-(x + (x + bgWidth)) / 2, -(y + (y + bgHeight)) / 2, 0.0);

        if (current != target) {
            this.current += (target - current) / 20.0 * (Delta.DELTATIME * 0.1f);
        }

        /* Settings Background */
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - (bgWidth / 2), bgY - (bgHeight / 2), bgWidth, bgHeight, 5F, new Color(40, 40, 40));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 75 - ((bgWidth - 150) / 2), bgY - (bgHeight / 2), bgWidth - 150, bgHeight, 5, new Color(30, 30, 30));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 55 - ((bgWidth - 190) / 2), bgY - (bgHeight / 2), bgWidth - 190, bgHeight, 0, new Color(30, 30, 30));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX + 60 - (bgWidth / 2), bgY + 10 - (bgHeight / 2), bgWidth - 70, bgHeight - 20, 5F, new Color(30, 30, 30));

        fontRenderer.drawString("Simplex", this.width / 2 - 92, this.height / 2 - 69, new Color(170, 170, 170).getRGB());
        smallFontRenderer.drawString(module.getName().toUpperCase(), this.width / 2 - 92, this.height / 2 - 55, new Color(170, 170, 170).getRGB());

        for (GuiSlider slider : builder.getSliderList()) {
            slider.drawButton(mc, mouseX, mouseY);
        }

        for (InputField field : builder.getInputList()) {
            GLRectUtils.drawRect(field.x, field.y, field.x + field.getWidth(), field.y + field.getHeight() - 10, new Color(40, 40, 40).getRGB());
            GLRectUtils.drawShadow(field.x, field.y, field.getWidth(), field.getHeight() - 10);
            field.render();
        }

        for (ToggleBoxButton button : builder.getToggleList()) {
            button.drawButton(mc, mouseX, mouseY);
        }

        for (ColorPicker picker : builder.getColorList()) {
            picker.init();
            picker.render(mouseX, mouseY);
        }
        GL11.glPopMatrix();

        if (target == 0 && current < 0.8) {
            mc.displayGuiScreen(null);
        }

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
        for (ColorPicker picker : builder.getColorList()) {
            picker.mouseClicked(mouseX, mouseY, mouseButton);
        }
        for (InputField field : builder.getInputList()) {
            field.onClick(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (GuiSlider slider : builder.getSliderList()) {
            slider.mouseReleased(mouseX, mouseY);
        }
        for (ColorPicker picker : builder.getColorList()) {
            picker.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @SneakyThrows
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        Field[] fields = module.getClass().getDeclaredFields();
        for (Field field : fields) {
            Option optionAnnotation = field.getAnnotation(Option.class);
            if (optionAnnotation != null && optionAnnotation.page() == currentPage) {
                if (field.getType() == int.class || field.getType() == float.class || field.getType() == double.class) {
                    SimplexClient.getInstance().getModuleConfig().append(field.toString(), builder.getSliderMap().get(field).getValue());
                    SimplexClient.getInstance().getModuleConfig().save();
                }
                if (field.getType() == boolean.class) {
                    SimplexClient.getInstance().getModuleConfig().append(field.toString(), builder.getToggleMap().get(field).isToggled());
                    SimplexClient.getInstance().getModuleConfig().save();
                }
                if (field.getType() == Color.class) {
                    ColorPicker picker = builder.getColorPickerMap().get(field);
                    SimplexClient.getInstance().getModuleConfig().append(field.toString(), new Float[]{picker.color[0], picker.color[1], picker.color[2], picker.color[3]});
                    SimplexClient.getInstance().getModuleConfig().append(field + " color", picker.getSelectedColorFinalAsColor());
                    SimplexClient.getInstance().getModuleConfig().save();
                }
                if (field.getType() == String.class) {
                    SimplexClient.getInstance().getModuleConfig().append(field.toString(), builder.getInputMap().get(field).getText());
                }
            }
        }
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == 1 /* ESC */) {
            if (target == 0) {
                mc.displayGuiScreen(null);
            }
            target = 0.f;
        }
        for (InputField field : builder.getInputList()) {
            field.keyTyped(typedChar, keyCode);
        }
    }

}
