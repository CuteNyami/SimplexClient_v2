package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {
    /*
    @Overwrite
    public void initGui() {
        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));
        this.buttonList.add(new PauseScreenButton(4, this.width / 2 - 80, this.height / 2 - 40, 160, 15, I18n.format("menu.returnToGame")));
        this.buttonList.add(new PauseScreenButton(5, this.width / 2 - 30, this.height / 2 - 30, 70, 15, I18n.format("gui.achievements")));
    }

    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RoundedShaderRenderer.getInstance().drawRound(width / 2 - 100, height / 2 - 50, 200, 100, 4F, new Color(0,0,0,120));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
    }
     */
}
