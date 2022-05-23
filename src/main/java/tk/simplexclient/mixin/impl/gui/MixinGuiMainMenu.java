package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.ui.buttons.main.ExitButton;
import tk.simplexclient.ui.buttons.round.ImageButton;
import tk.simplexclient.ui.buttons.round.RoundedButton;

import java.awt.*;
import java.io.IOException;

@Mixin(GuiMainMenu.class)
public class MixinGuiMainMenu extends GuiScreen {

    /**
     * @author CuteNyami
     */
    @Overwrite
    public void initGui() {
        this.buttonList.add(new RoundedButton(1, width / 2 - 35, height / 2 - 20, 100, 13, "Singleplayer"));
        this.buttonList.add(new RoundedButton(2, width / 2 - 35, height / 2 - 5, 100, 13, "Multiplayer"));
        this.buttonList.add(new ImageButton(3, width / 2 + 70, height / 2 - 20, 13, 13, "options"));
        this.buttonList.add(new ImageButton(4, width / 2 + 70, height / 2 - 5, 13, 13, "discord"));
        this.buttonList.add(new ExitButton(5, width - 28, 5, 22, 22));
    }

    /**
     * @author CuteNyami
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("simplex/menu/main/bg.png"));
        Gui.drawModalRectWithCustomSizedTexture(-21 + Mouse.getX() / 90, Mouse.getY() * -1 / 90, 0.0f, 0.0f, this.width + 20, this.height + 20, (float)(this.width + 21), (float)(this.height + 20));

        String s1 = "Copyright Mojang AB";
        SimplexClient.getInstance().getSmoothFont().drawString(s1, this.width - this.fontRendererObj.getStringWidth(s1) + 25, this.height - 11, -1);

        int rectX = width / 2 - 105;
        int rectY = height / 2 - 40;
        GLRectUtils.drawRoundedOutline(rectX, rectY, rectX + 220, rectY + 70, 3.0f, 5, new Color(0, 178, 255, 200).getRGB());
        GLRectUtils.drawRoundedRect(rectX, rectY, rectX + 220, rectY + 70, 3.0f, new Color(0, 0, 0, 158).getRGB());

        drawScaledLogo((float) width / 2 - 70, (float) height / 2 - 6, 50, 50);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1: {
                this.mc.displayGuiScreen(new GuiSelectWorld(this));
                break;
            }
            case 2: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 4: {
                String url_open = "https://discord.gg/h5xEt7NWcP";
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case 5: {
                this.mc.shutdown();
                break;
            }
        }
        super.actionPerformed(button);
    }

    public void drawScaledLogo(float x, float y, int size, int size2) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glPopMatrix();
        drawTexture(new ResourceLocation("simplex/menu/main/logo.png"), x - (float) size / 2, y - (float) size / 2, size, size2);
    }

    public void bindTexture(ResourceLocation resourceLocation) {
        ITextureObject texture = Minecraft.getMinecraft().getTextureManager().getTexture(resourceLocation);
        if (texture == null) {
            texture = new SimpleTexture(resourceLocation);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, texture);
        }
        GL11.glBindTexture(3553, texture.getGlTextureId());
    }

    public void drawTexture(ResourceLocation resourceLocation, float x, float y, float width, float height) {
        GL11.glPushMatrix();
        final float size = width / 2.0f;
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2848);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindTexture(resourceLocation);
        GL11.glBegin(7);
        GL11.glTexCoord2d(0.0f / size, 0.0f / size);
        GL11.glVertex2d(x, y);
        GL11.glTexCoord2d(0.0f / size, (0.0f + size) / size);
        GL11.glVertex2d(x, y + height);
        GL11.glTexCoord2d((0.0f + size) / size, (0.0f + size) / size);
        GL11.glVertex2d(x + width, y + height);
        GL11.glTexCoord2d((0.0f + size) / size, 0.0f / size);
        GL11.glVertex2d((x + width), y);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
