package tk.simplexclient.gui.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;

import java.awt.*;

public class PauseScreenButton extends GuiButton {

    public PauseScreenButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        boolean hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
        GLRectUtils.drawRoundedRect(xPosition, yPosition, xPosition + width, yPosition + height, 3F, hovered ? new Color(0, 0, 0, 190).getRGB() : new Color(20, 20, 20, 190).getRGB());
        GLRectUtils.drawRoundedOutline(xPosition, yPosition, xPosition + width, yPosition + height, 3F, 3F, hovered ? new Color(0, 0, 0, 190).getRGB() : new Color(15, 15, 15, 190).getRGB());

        SimplexClient.getInstance().getSmoothFont().drawCenteredString(this.displayString, this.xPosition +  this.width / 2, this.yPosition +  (this.height - 8) / 2, hovered ? new Color(222, 222, 222).getRGB() : new Color(190, 190, 190).getRGB());
    }
}
