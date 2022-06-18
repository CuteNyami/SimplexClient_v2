package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {

    /**
     * @author CuteNyami
     */

    /*
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        //RoundedShaderRenderer.getInstance().drawRound();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

     */

}
