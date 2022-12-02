package tk.simplexclient.ui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public abstract class DefaultButton extends GuiButton {

    protected boolean hovered;

    public DefaultButton(int x, int y, String buttonText) {
        super(9999, x, y, buttonText);
    }

    public DefaultButton(int x, int y, int widthIn, int heightIn, String buttonText) {
        super(9999, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
        super.drawButton(mc, mouseX, mouseY);
    }

    public abstract void onClick();
}
