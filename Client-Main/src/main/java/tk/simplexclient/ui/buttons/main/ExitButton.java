package tk.simplexclient.ui.buttons.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.utils.GuiUtils;

import java.awt.*;

public class ExitButton extends GuiButton {

    private final ResourceLocation ICON;

    public ExitButton(final int buttonId, final int x, final int y, final int width, final int height) {
        super(buttonId, x, y, width, height, "");
        this.ICON = new ResourceLocation("simplex/icons/exit.png");
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            GLRectUtils.drawRoundedOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 3.0f, 2.0f, (new Color(0, 0, 0, 170).getRGB()));
            GLRectUtils.drawRoundedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 3.0f, this.enabled ? (this.hovered ? new Color(140, 25, 25, 100).getRGB() : new Color(60, 5, 5, 100).getRGB()) : new Color(225, 90, 90, 50).getRGB());
            mc.getTextureManager().bindTexture(this.ICON);
            // icon size
            final int b = 15;
            GlStateManager.enableBlend();
            // icon color
            GuiUtils.setGlColor(new Color(255, 255, 255, 255).getRGB());
            GuiUtils.drawScaledCustomSizeModalRect(this.xPosition + (float) (this.width - b) / 2, this.yPosition +  (float) (this.height - b) / 2, 0f, 0f, b, b, b, b,  b,  b);
        }
    }
}
