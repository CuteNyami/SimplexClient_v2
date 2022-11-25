package tk.simplexclient.gui.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import tk.simplexclient.font.FontRenderer;

import java.awt.*;

public class GuiButtonExt extends GuiButton {

    private int packedFGColour;

    private final FontRenderer fontRenderer;

    public GuiButtonExt(int id, int xPos, int yPos, String displayString) {
        super(id, xPos, yPos, displayString);
        fontRenderer = new FontRenderer("smooth", 15.0F);
    }

    public GuiButtonExt(int id, int xPos, int yPos, int width, int height, String displayString) {
        super(id, xPos, yPos, width, height, displayString);
        fontRenderer = new FontRenderer("smooth", 15.0F);
    }

    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            Gui.drawRect(this.xPosition, this.yPosition + 5, this.xPosition + this.width, this.yPosition + 10, new Color(40, 40, 40).getRGB());
            this.mouseDragged(mc, mouseX, mouseY);

            String buttonText = this.displayString;
            int strWidth = mc.fontRendererObj.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRendererObj.getStringWidth("...");

            if (strWidth > width - 6 && strWidth > ellipsisWidth) buttonText = mc.fontRendererObj.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";

            fontRenderer.drawString(buttonText, this.xPosition + this.width / 2 - 49, this.yPosition + (this.height - 35) / 2, new Color(170, 170, 170).getRGB());
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
