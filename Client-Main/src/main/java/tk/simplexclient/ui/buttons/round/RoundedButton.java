package tk.simplexclient.ui.buttons.round;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;

import java.awt.*;

public class RoundedButton extends GuiButton {

    private int fade;

    private final float radius;

    public RoundedButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.radius = 3.0f;
    }

    public RoundedButton(int buttonId, int x, int y, int widthIn, int heightIn, float radius, int color, int color1, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.radius = radius;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);

            if (!this.hovered) {
                this.fade = 90;
            } else {
                if (this.fade  != 230) {
                    this.fade += 5;
                }
            }
        }
        float b = this.hovered ? new Color(168, 168, 168).getRGB() : new Color(205, 205, 205, 255).getRGB();
        Color a = new Color(0, 0, 0, this.fade);
        GLRectUtils.drawRoundedOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, radius, 2.0f, new Color(0, 0, 0, 170).getRGB());
        GLRectUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, radius, this.enabled ? (this.hovered ? new Color(0, 0, 0, 100).getRGB() : new Color(30, 30, 30, 100).getRGB()) : new Color(70, 70, 70, 50).getRGB());
        GLRectUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, radius - 1F, a.getRGB());
        SimplexClient.getInstance().getSmoothFont().drawCenteredString(this.displayString, this.xPosition +  this.width / 2, this.yPosition +  (this.height - 8) / 2, (int) b);
        this.mouseDragged(mc, mouseX, mouseY);
    }
}
