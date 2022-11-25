package tk.simplexclient.mixin.impl.gui;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import tk.simplexclient.access.AccessGui;

@Mixin(Gui.class)
public abstract class MixinGui implements AccessGui {
    @Override
    @Invoker("drawGradientRect")
    public abstract void drawGradient(int left, int top, int right, int bottom, int startColor, int endColor);
}
