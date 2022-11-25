package tk.simplexclient.gui.mod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import tk.simplexclient.gui.mod.theme.Theme;
import tk.simplexclient.gui.mod.theme.ThemeManager;
import tk.simplexclient.gui.mod.theme.Themes;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.io.IOException;

public class TestGui extends GuiScreen {

    private final RoundedShaderRenderer rounded = RoundedShaderRenderer.getInstance();

    @Override
    public void initGui() {
        ThemeManager.setTheme(Themes.MACOS);

        mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/menu_blur.json"));

        if (ThemeManager.getCurrentTheme() == null) return;
        ThemeManager.getCurrentTheme().init();

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Theme.width = width;
        Theme.height = height;

        if (ThemeManager.getCurrentTheme() == null) return;
        ThemeManager.getCurrentTheme().render(mouseX, mouseY, partialTicks);

        // xPos.setEase(Easing.CUBIC_OUT).setMin(2).setMax(100).setSpeed(10).setReversed(false).update();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (ThemeManager.getCurrentTheme() == null) return;
        ThemeManager.getCurrentTheme().actionPerformed(button);

        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        mc.entityRenderer.stopUseShader();
        super.onGuiClosed();
    }
}
