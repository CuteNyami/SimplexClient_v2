package tk.simplexclient.render;

import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

/**
 * The main ui renderer
 * <p>
 * Used to create UIs with the SimplexClient Render Engine
 */
public class UIRenderer {

    @Getter
    private int width, height;
    @Getter
    private final List<UIComponent> components = new ArrayList<>();

    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        this.width = width;
        this.height = height;
        this.components.clear();
        this.init();
    }

    /**
     * This is the init function of the Renderer
     * <p>
     * This is the place where the components are initialized
     */
    public void init() {
    }

    /**
     * Main render function
     * <p>
     * This is the place where the components are rendered
     *
     * @param mouseX The x position of the mouse
     * @param mouseY The y position of the mouse
     */
    public void render(int mouseX, int mouseY) {
        for (UIComponent component : components) {
            component.render(mouseX, mouseY);
        }
    }

    /**
     * Get the System clipboard text as String
     *
     * @return The System clipboard message
     */
    @SneakyThrows
    public static String getClipBoard() {
        return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
    }

    /**
     * Set the System clipboard text
     *
     * @param copy The paste text
     */
    public static void setClipBoard(String copy) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(copy), null);
    }
}
