package tk.simplexclient.gui.mod;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.utils.GuiUtils;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.ui.buttons.ModButton;
import tk.simplexclient.ui.buttons.round.ImageButton;
import tk.simplexclient.ui.elements.InputField;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiModMenu extends GuiScreen {

    private final ArrayList<InputField> inputFields = new ArrayList<>();

    private final ArrayList<ModButton> modButtons = new ArrayList<>();

    private final FontRenderer smoothFr = SimplexClient.getInstance().getSmoothFont();

    private static final int MOD_Y_INC = 20;

    private InputField searchField;
    private ImageButton imageButton;

    private int scrollY = 0;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        /*
            ((AccessEntityRenderer) mc.entityRenderer).loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
         */

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

        this.buttonList.clear();
        this.buttonList.add(new BlueButton(123456789, width / 2 + 50, height / 2 + 60, 70, 10, "Reset Positions"));
        this.buttonList.add(imageButton);

        this.modButtons.clear();

        for (GuiButton button : buttonList) {
            button.drawButton(mc, mouseX, mouseY);
        }

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        glScissor((double) width / 2 - 100, (double) height / 2 - 43, 370, 100);

        int contentHeight = 0;

        for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
            if (module.getName().contains(this.searchField.getText().toLowerCase())) {
                this.modButtons.add(new ModButton(this.width / 2 - 20, y - this.scrollY, 120, 15, module));
                if (SimplexClient.getInstance().getSettingsBuilder().getModsWithSettings().contains(module)) {
                    this.buttonList.add(new SettingsButton(this.width / 2 + 100, y - this.scrollY, 15, 15, 10, module));
                }
                contentHeight += MOD_Y_INC;
                y += MOD_Y_INC;
            }
        }

        for (ModButton button : modButtons) {
            button.drawButton(mc, mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        int wheel = Mouse.getDWheel();

        if (!(modButtons.size() > 5)) return;
        if (wheel < 0) {
            scrollY += 3;
        } else if (wheel > 0) {
            scrollY -= 3;
        }

        int minScroll = 0;
        int maxScroll = contentHeight - 100;

        if (scrollY < minScroll) scrollY = minScroll;
        if (scrollY > maxScroll) scrollY = maxScroll;
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
        for (ModButton button : modButtons) {
            button.onClick((mouseX >= button.xPosition && mouseY >= button.yPosition && mouseX < button.xPosition + button.getWidth() && mouseY < button.yPosition + button.getHeight()) && (mouseX >= width / 2 - 100 && mouseY >= height / 2 - 43 && mouseX < width / 2 - 100 + 370 && mouseY < height / 2 - 43 + 100), mouseX, mouseY, mouseButton);
        }
        for (GuiButton button : buttonList) {
            if (button instanceof SettingsButton) {
                SettingsButton settingsButton = (SettingsButton) button;
                settingsButton.onClick(mouseX, mouseY, mouseButton);
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(null);
        } else if (button.id == 123456789) {
            for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
                if (module.isEnabled()) {
                    module.setX(0);
                    module.setY(0);
                }
            }
            SimplexClient.getLogger().info("The mod positions have been reset!");
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

    private static class SettingsButton extends GuiButton {

        @Getter
        private final int iconSize;

        @Getter
        private final ModuleCreator module;

        public SettingsButton(int x, int y, int width, int height, int iconSize, ModuleCreator module) {
            super(500, x, y, width, height, "");
            this.iconSize = iconSize;
            this.module = module;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            mc.getTextureManager().bindTexture(new ResourceLocation("simplex/icons/options.png"));

            int b = 10;
            if (iconSize != 0) {
                b = iconSize;
            }
            GlStateManager.enableBlend();

            GuiUtils.setGlColor(new Color(255, 255, 255, 255).getRGB());
            GuiUtils.drawScaledCustomSizeModalRect(this.xPosition + (float) (this.width - b) / 2, this.yPosition + (float) (this.height - b) / 2, 0f, 0f, b, b, b, b, b, b);
        }

        public void onClick(int mouseX, int mouseY, int mouseButton) {
            if ((mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + getWidth() && mouseY < yPosition + getHeight())) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiModSettings(module));
            }
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
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
