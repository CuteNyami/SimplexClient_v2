package tk.simplexclient.gui.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.animations.Animate;
import tk.simplexclient.animations.Easing;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.shader.RoundedShaderRenderer;
import tk.simplexclient.ui.buttons.ModButton;
import tk.simplexclient.ui.buttons.round.ImageButton;
import tk.simplexclient.ui.elements.InputField;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiModMenu extends GuiScreen {

    private final ArrayList<InputField> inputFields = new ArrayList<>();

    private final FontRenderer smoothFr = SimplexClient.getInstance().getSmoothFont();

    private InputField searchField;

    private int scrollY = 0;

    private ImageButton imageButton;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));

        searchField = new InputField(width / 2 - 20, height / 2 - 55, 60, 20, false, "", "", 30, InputField.InputFlavor.MOD_PROFILE_NAME);

        imageButton = new ImageButton(0, width / 2 + 110, height / 2 - 72, 10, 10, 5, "close");

        this.inputFields.clear();
        this.inputFields.add(searchField);
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Window Background

        GLRectUtils.drawRect((float) width / 2 - 125, (float) height / 2 - 75, (float) (width / 2 - 125) + 250, (float) (height / 2 - 75) + 150, new Color(0, 0, 0, 140).getRGB());
        GLRectUtils.drawRect((float) width / 2 - 30, (float) height / 2 - 75, (float) (width / 2 - 30) + 1, (float) (height / 2 - 75) + 150, new Color(255, 255, 255, 140).getRGB());

        GLRectUtils.drawRect((float) width / 2 - 125, (float) height / 2 - 60, (float) (width / 2 - 125) + 250, (float) (height / 2 - 60) + 1.5F, new Color(255, 255, 255, 140).getRGB());
        GLRectUtils.drawRoundedOutline((float) width / 2 - 125, (float) height / 2 - 75, (float) (width / 2 - 125) + 250, (float) (height / 2 - 75) + 150, 1.5F, 3.0F, new Color(255, 255, 255, 140).getRGB());

        smoothFr.drawString("Downloaded Plugins", width / 2 - 113, height / 2 - 72, -1);
        smoothFr.drawString("Modules", width / 2 + 30, height / 2 - 72, -1);

        for (InputField field : inputFields) {
            GLRectUtils.drawRoundedRect(field.x, field.y, field.x + field.getWidth(), field.y + field.getHeight() - 10, 4.0F, new Color(0, 0, 0, 120).getRGB());
            GLRectUtils.drawRoundedOutline(field.x, field.y, field.x + field.getWidth(), field.y + field.getHeight() - 10, 2.0F, 2.8F, new Color(255, 255, 255, 120).getRGB());
            field.render();
        }

        if (searchField.getText().equals("") && !searchField.isFocused()) {
            smoothFr.drawString("Search...", searchField.x + 2, searchField.y, new Color(169, 169, 169).getRGB());
        }

        int y = height / 2 - 40;

        int wheel = Mouse.getDWheel();

        if (wheel < 0) {
            scrollY += 3;
        } else if (wheel > 0) {
            scrollY -= 3;
        }

        this.buttonList.clear();
        this.buttonList.add(new BlueButton(123456789, width / 2 + 50, height / 2 + 60, 70, 10, "Reset Positions"));
        this.buttonList.add(imageButton);

        for (GuiButton button : buttonList) {
            button.drawButton(mc, mouseX, mouseY);
        }

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        glScissor((double) width / 2 - 100, (double) height / 2 - 43, 370, 100);

        for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
            if (module.getName().contains(searchField.getText().toLowerCase())) {
                this.buttonList.add(new ModButton(module.getId(), width / 2 - 20, y - scrollY, 135, 15, module));
                y += 20;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        mc.entityRenderer.stopUseShader();
        SimplexClient.getInstance().getModuleConfig().saveModuleConfig();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (InputField field : inputFields) {
            field.onClick(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(null);
        } else if (button.id == 123456789) {
            for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
                module.getDrag().setX(0);
                module.getDrag().setY(0);
            }
            SimplexClient.getLogger().info("The mod positions have been reset!");
        }

        for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
            if (button.id == module.getId()) {
                module.setEnabled(!module.isEnabled());
            }
        }
        super.actionPerformed(button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (InputField field : inputFields) {
            scrollY = 0;
            field.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    private void glScissor(double x, double y, double width, double height) {

        y += height;

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        Minecraft mc = Minecraft.getMinecraft();

        GL11.glScissor((int) ((x * mc.displayWidth) / scaledResolution.getScaledWidth()),
                (int) (((scaledResolution.getScaledHeight() - y) * mc.displayHeight) / scaledResolution.getScaledHeight()),
                (int) (width * mc.displayWidth / scaledResolution.getScaledWidth()),
                (int) (height * mc.displayHeight / scaledResolution.getScaledHeight()));
    }

    private static class BlueButton extends GuiButton {

        private int fade;

        public BlueButton(int buttonId, int x, int y, int width, int height, String buttonText) {
            super(buttonId, x, y, width, height, buttonText);
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {
                this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);

                if (!this.hovered) {
                    this.fade = 90;
                } else {
                    if (this.fade != 230) {
                        this.fade += 5;
                    }
                }
            }
            float b = this.hovered ? new Color(168, 168, 168).getRGB() : new Color(205, 205, 205, 255).getRGB();
            Color a = new Color(0, 0, 0, this.fade);
            GLRectUtils.drawRoundedOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0f, 6.0f, new Color(0, 79, 97, 212).getRGB());
            GLRectUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, this.enabled ? (this.hovered ? new Color(0, 110, 121, 255).getRGB() : new Color(0, 148, 184, 255).getRGB()) : new Color(0, 159, 183, 192).getRGB());
            GLRectUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, a.getRGB());
            SimplexClient.getInstance().getSmoothFont().drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2 - 1, (int) b);
        }
    }
}
