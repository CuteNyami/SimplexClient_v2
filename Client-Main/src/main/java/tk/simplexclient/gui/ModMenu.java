package tk.simplexclient.gui;

import net.minecraft.client.gui.GuiScreen;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.impl.hud.FPSModule;

import java.awt.*;

public class ModMenu extends GuiScreen {

    private FontRenderer fontRenderer;

    private FontRenderer fontRenderer1;

    private ModuleCreator module;

    @Override
    public void initGui() {
        fontRenderer = new FontRenderer("inter-bold", 15.0F);
        fontRenderer1 = new FontRenderer("inter-bold", 12.0F);
        module = new FPSModule();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawRoundedRectangle(width / 2 - 175, height / 2 - 75, 230, 150, 10f, new Color(7, 8, 9).getRGB());
        drawRectangle(width / 2 - 175, height / 2 - 45, 230, 1, new Color(34, 35, 41).getRGB());

        drawRoundedRectangle(width / 2 + 57, height / 2 - 75, 120, 150, 10f, new Color(7, 8, 9).getRGB());
        drawRectangle(width / 2 + 57, height / 2 - 45, 120, 1, new Color(34, 35, 41).getRGB());

        fontRenderer.drawString("Modules", width / 2 - 160, height / 2 - 65, -1);
        fontRenderer.drawString(module.getName().toUpperCase() + " MOD", width / 2 + 72, height / 2 - 65, -1);

        fontRenderer1.drawString("RESET", width / 2 + 145, height / 2 - 64, new Color(118, 106, 255).getRGB());
    }

    private void drawRoundedRectangle(float x, float y, float x1, float y1, float radius, int color) {
        GLRectUtils.drawRoundedRect(x, y, x + x1, y + y1, radius, color);
    }

    private void drawRoundedGradientRectangle(float x, float y, float x1, float y1, float radius, int color) {
        //GLRectUtils.drawRoundedOutlineGradient(x, y, x + x1, y + y1, radius, color);
    }

    private void drawRectangle(int x, int y, int x1, int y1, int color) {
        GLRectUtils.drawRect(x, y, x + x1, y + y1, color);
    }


}
