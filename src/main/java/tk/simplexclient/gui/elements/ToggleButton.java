package tk.simplexclient.gui.elements;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.animations.Animate;
import tk.simplexclient.animations.Easing;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.awt.*;

import static tk.simplexclient.gui.utils.GuiUtils.drawHollowRect;

public class ToggleButton extends GuiButton {

    @Getter
    boolean allowDraw;

    @Getter
    boolean toggled;

    Color off = new Color (0xFFbdbdbd);
    Color on = new Color (0xFF00FF00);
    Color c = new Color (0xFFFFFFFF);

    Animate anim = new Animate();

    public ToggleButton(int x, int y, int size, String buttonText, boolean toggled) {
        super(555, x, y, size, size, buttonText);
        this.toggled = toggled;
    }


    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        hovered = enabled && visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;


        //drawRoundedRect(this.xPosition, this.yPosition, 10, 10, 3, new Color(49, 49, 49));

        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("simplex/icons/checkbox.png"));
        Gui.drawModalRectWithCustomSizedTexture(xPosition, yPosition, width - 4, height - 3, width - 4, height - 3, width - 4, height - 3);

        SimplexClient.getInstance().getSmoothFont().drawString(displayString, xPosition + 30, yPosition, -1);
    }

    public void onClick() {
        if (hovered) {
            toggled = !toggled;
        }
    }

    private void drawRoundedRect(int x, int y, int width, int height, int cornerRadius, Color color) {
        drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
        drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
        drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());
        drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
        drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
        drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
        drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
    }

    private void drawArc(int x, int y, int radius, int startAngle, int endAngle, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);

        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

        worldRenderer.begin(6, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();

        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }

        Tessellator.getInstance().draw();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    private void drawCircle(int x, int y, int width, int height, Color color) {
        this.drawArc(x + width / 2, y + height / 2, width / 2, 0, 360, color);
    }
}
