package tk.simplexclient.ui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class ModButton extends GuiButton {

    private int fade;

    private ModuleCreator module;

    public ModButton(int x, int y, int widthIn, int heightIn, ModuleCreator module) {
        super(111111111, x, y, widthIn, heightIn, module.getName().toUpperCase());
        this.module = module;
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
        GLRectUtils.drawRoundedOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, 3.0f, this.module.isEnabled() ? new Color(0, 255, 166, 180).getRGB() : new Color(255, 0, 94, 180).getRGB());
        GLRectUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height,  2.0F, this.enabled ? (this.hovered ? new Color(0, 0, 0, 100).getRGB() : new Color(30, 30, 30, 100).getRGB()) : new Color(70, 70, 70, 50).getRGB());
        GLRectUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 2.0F, a.getRGB());
        SimplexClient.getInstance().getSmoothFont().drawCenteredString(this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, (int) b);
    }

    public boolean onClick(boolean hovered, int mouseX, int mouseY, int mouseButton) {
        if (hovered) {
            module.setEnabled(!module.isEnabled());
            return true;
        } else {
            return false;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
