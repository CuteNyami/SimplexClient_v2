package tk.simplexclient.gui.mod;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import tk.simplexclient.animations.Animate;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.mod.theme.Theme;
import tk.simplexclient.gui.mod.theme.ThemeManager;
import tk.simplexclient.gui.mod.theme.Themes;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.awt.*;
import java.io.IOException;

public class TestGui extends GuiScreen {

    private final RoundedShaderRenderer rounded = RoundedShaderRenderer.getInstance();

    @Override
    public void initGui() {
        ThemeManager.setTheme(Themes.MACOS);

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
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        if (ThemeManager.getCurrentTheme() == null) return;
        ThemeManager.getCurrentTheme().actionPerformed(p_actionPerformed_1_);

        super.actionPerformed(p_actionPerformed_1_);
    }
}
