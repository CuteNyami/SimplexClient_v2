package tk.simplexclient.mixin.impl.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import tk.simplexclient.access.AccessGuiMultiplayer;

@Mixin(GuiMultiplayer.class)
public abstract class MixinGuiMultiplayer extends GuiScreen implements AccessGuiMultiplayer {

    @Shadow private String hoveringText;

    @Shadow private ServerSelectionList serverListSelector;

    /**
     * @author CuteNyami
     * @reason idk
     */
    @Overwrite
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.hoveringText = null;
        this.serverListSelector.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.title", new Object[0]), this.width / 2, 20, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (this.hoveringText != null)
        {
            this.drawHoveringText(Lists.newArrayList(Splitter.on("\n").split(this.hoveringText)), mouseX, mouseY);
        }
    }

    @Override
    @Accessor
    public abstract ServerList getSavedServerList();
}
