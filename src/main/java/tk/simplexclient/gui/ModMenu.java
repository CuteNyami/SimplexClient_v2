package tk.simplexclient.gui;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gui.elements.ToggleButton;
import tk.simplexclient.gui.mod.GuiModSettings;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ModMenu extends GuiScreen {

    private FontRenderer fontRenderer;

    private static final int MOD_Y_INC = 20;

    private int scrollY = 0, grabStartY = -1, grabScrollY;

    private final ArrayList<ModButton> modButtons = new ArrayList<>();

    private final ArrayList<ToggleButton> modToggleButtons = new ArrayList<>();

    private ScaledResolution sr;

    @Override
    public void initGui() {
        sr = new ScaledResolution(Minecraft.getMinecraft());
        fontRenderer = new FontRenderer("smooth", 20.0F);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        /* Mod Menu Background Values */
        //float bgX = (float) this.width / 2 - 35;
        float bgX = (float) this.width / 2;
        float bgY = (float) this.height / 2;
        float bgWidth = 200;
        float bgHeight = 150;

        /* Mod Menu Background */
        //RoundedShaderRenderer.getInstance().drawRound(sr,bgX - (bgWidth / 2), bgY - (bgHeight / 2), bgWidth, bgHeight, 5F, new Color(40, 40, 40));
        //RoundedShaderRenderer.getInstance().drawRound(sr,bgX - ((bgWidth - 140) / 2) + 140, bgY - (bgHeight / 2), bgWidth - 140, bgHeight, 5F, new Color(40, 40, 40));
        //RoundedShaderRenderer.getInstance().drawRound(sr,bgX - 75 - ((bgWidth - 150) / 2), bgY - (bgHeight / 2), bgWidth - 150, bgHeight, 5, new Color(30, 30, 30));
        //RoundedShaderRenderer.getInstance().drawRound(sr,bgX - 55 - ((bgWidth - 190) / 2), bgY - (bgHeight / 2), bgWidth - 190, bgHeight, 0, new Color(30, 30, 30));

        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - (bgWidth / 2), bgY - (bgHeight / 2), bgWidth, bgHeight, 5F, new Color(40, 40, 40));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 75 - ((bgWidth - 150) / 2), bgY - (bgHeight / 2), bgWidth - 150, bgHeight, 5, new Color(30, 30, 30));
        RoundedShaderRenderer.getInstance().drawRound(sr, bgX - 55 - ((bgWidth - 190) / 2), bgY - (bgHeight / 2), bgWidth - 190, bgHeight, 0, new Color(30, 30, 30));

        fontRenderer.drawString("Simplex", this.width / 2 - 92, this.height / 2 - 69, new Color(170, 170, 170).getRGB());
        //fontRenderer.drawString("Profiles", this.width / 2 + 90, this.height / 2 - 69, new Color(170, 170, 170).getRGB());

        int y = height / 2 - 65;

        int contentHeight = 0;

        this.modButtons.clear();
        this.modToggleButtons.clear();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        glScissor((double) width / 2 - 135, (double) height / 2 - 67, 370, 137);

        for (ModuleCreator module : SimplexClient.getInstance().getModuleManager().getModules()) {
            this.modButtons.add(new ModButton(this.width / 2 - 33, y - this.scrollY, 120, 15, 5F,
                    new Color(30, 30, 30),
                    new Color(25, 25, 25),
                    module));
            this.modToggleButtons.add(new ToggleButton(this.width / 2 + 62, y + 3 - this.scrollY, 20, 9, module.isEnabled(), module));
            contentHeight += MOD_Y_INC;
            y += MOD_Y_INC;
        }

        for (ModButton button : modButtons) {
            button.drawButton(mouseX, mouseY);
        }

        for (ToggleButton button : modToggleButtons) {
            button.drawButton(mc, mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        int wheel = Mouse.getDWheel();

        if (grabStartY != -1) {
            scrollY = grabScrollY - (mouseY - grabStartY);
        }

        if (!(modButtons.size() > 5)) return;
        if (wheel < 0) {
            scrollY += 3;
        } else if (wheel > 0) {
            scrollY -= 3;
        }

        int minScroll = 0;
        int maxScroll = contentHeight - 137;

        if (scrollY < minScroll) scrollY = minScroll;
        if (scrollY > maxScroll) scrollY = maxScroll;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        grabStartY = mouseY;
        grabScrollY = scrollY;

        for (ToggleButton button : modToggleButtons) {
            button.onClick();
            if (button.hovered) return;
        }

        for (ModButton modButton : modButtons) {
            modButton.onClick(mouseX, mouseY);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        grabStartY = -1;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
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

    private static class ModButton {

        @Getter
        private final int x, y, width, height;

        @Getter
        private final float radius;

        @Getter
        private final Color normal, hovered;

        @Getter
        private final ModuleCreator module;

        private final ScaledResolution sr;

        @Getter
        private boolean isHovered;

        private ModButton(int x, int y, int width, int height, float radius, Color normal, Color hovered, ModuleCreator module) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.radius = radius;
            this.normal = normal;
            this.hovered = hovered;
            this.module = module;
            this.sr = new ScaledResolution(Minecraft.getMinecraft());
        }

        public void drawButton(int mouseX, int mouseY) {
            isHovered = (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height);
            Color color = normal;

            if (isHovered && SimplexClient.getInstance().getSettingsBuilder().getModsWithSettings().contains(module)) {
                color = hovered;
            }

            RoundedShaderRenderer.getInstance().drawRound(sr, x, y, width, height, radius, color);
            SimplexClient.getInstance().getSmoothFont().drawString(this.module.getName().toUpperCase(), this.x + this.width / 2 - 55, this.y + (this.height - 8) / 2 - 1, new Color(170, 170, 170).getRGB());
        }

        public void onClick(int mouseX, int mouseY) {
            if (mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height) {
                if (SimplexClient.getInstance().getSettingsBuilder().getModsWithSettings().contains(module)) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                    Minecraft.getMinecraft().displayGuiScreen(new GuiModSettings(module));
                }
            }
        }
    }
}
