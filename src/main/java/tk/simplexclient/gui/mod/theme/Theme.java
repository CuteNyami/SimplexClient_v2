package tk.simplexclient.gui.mod.theme;

import net.minecraft.client.gui.GuiButton;

import java.io.IOException;

public class Theme {

    private final String name;

    private final Themes theme;

    public static int width, height;

    public Theme(String name, Themes theme) {
        this.name = name;
        this.theme = theme;
    }

    public void init() {}

    public void render(int mouseX, int mouseY, float partialTicks) {}

    public void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {}

    public String getName() {
        return name;
    }

    public Themes getTheme() {
        return theme;
    }
}
