package tk.simplexclient.mixin.impl.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tk.simplexclient.utils.ConnectingUtils;

@Mixin(GuiDisconnected.class)
public class MixinGuiDisconnected extends GuiScreen {

    @Shadow private int field_175353_i;

    @Inject(method = "initGui", at = @At("TAIL"))
    public void addButton(CallbackInfo ci) {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.field_175353_i + this.fontRendererObj.FONT_HEIGHT + 18, "Reconnect"));
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    public void addButtonAction(GuiButton button, CallbackInfo ci) {
        if (button.id == 1) {
            this.mc.displayGuiScreen(ConnectingUtils.connect());
        }
    }

}
