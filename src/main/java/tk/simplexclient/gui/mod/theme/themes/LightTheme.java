package tk.simplexclient.gui.mod.theme.themes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import tk.simplexclient.gui.mod.theme.Theme;
import tk.simplexclient.gui.mod.theme.Themes;
import tk.simplexclient.shader.RoundedShaderRenderer;

import java.awt.*;

public class LightTheme extends Theme {

    private final RoundedShaderRenderer rounded = RoundedShaderRenderer.getInstance();

    private ScaledResolution sr;

    public LightTheme() {
        super("macOS", Themes.MACOS);
    }

    @Override
    public void init() {
        sr = new ScaledResolution(Minecraft.getMinecraft());
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        // Background
        rounded.drawRound(sr, (float) width / 2 - 125, (float) height / 2 - 75, 50, 150, 2F, new Color(209, 209, 209, 179));
        rounded.drawRound(sr, (float) width / 2 - 75, (float) height / 2 - 75, 220, 150, 2F, new Color(255, 255, 255));
        super.render(mouseX, mouseY, partialTicks);
    }
}
