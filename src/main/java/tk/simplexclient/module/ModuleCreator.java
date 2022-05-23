package tk.simplexclient.module;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.dragging.ModuleDraggable;

import java.awt.*;

public abstract class ModuleCreator {

    @Getter @Setter private String name, description;

    private int x, y;

    @Setter @Getter private boolean enabled;

    @Getter ModuleDraggable drag;

    public final FontRenderer fr = new FontRenderer("smooth", 15.0f);

    public ModuleCreator(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;

        try {
            this.x = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " x", Integer.class);
            this.y = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " y", Integer.class);
            this.setEnabled(SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " enabled", Boolean.class));
        } catch (NullPointerException e) {
            this.x = x;
            this.y = y;
            this.enabled = false;
        }

        drag = new ModuleDraggable(this.x, this.y, getWidth(), getHeight(), new Color(0, 0, 0, 0).getRGB());
    }

    public ModuleCreator(String name, int x, int y) {
        this.name = name;
        this.description = null;

        try {
            this.x = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " x", Integer.class);
            this.y = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " y", Integer.class);
            this.setEnabled(SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " enabled", Boolean.class));
        } catch (NullPointerException e) {
            this.x = x;
            this.y = y;
            this.enabled = true;
        }

        drag = new ModuleDraggable(this.x, this.y, getWidth(), getHeight(), new Color(0, 0, 0, 0).getRGB());
    }

    public void drawDummyBackground(int x, int y, int width, int height) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);

        GLRectUtils.drawRectOutline(x, y, x + width, y + height, 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(x, y, x + width, y + height, new Color(255, 255, 255, 70).getRGB());

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public abstract void render();

    public void renderDummy(int mouseX, int mouseY) {
        drag.draw(mouseX, mouseY);
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public int getX() {
        return drag.getX();
    }

    public int getY() {
        return drag.getY();
    }
}
