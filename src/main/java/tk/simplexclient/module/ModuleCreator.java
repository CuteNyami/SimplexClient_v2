package tk.simplexclient.module;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;

import java.awt.*;

@Getter @Setter
public abstract class ModuleCreator {

    private String name, description;

    private int x, y;

    private final int id;

    private boolean enabled;


    public final FontRenderer fr = new FontRenderer("smooth", 15.0f);

    public ModuleCreator(int id, String name, String description, int x, int y) {
        this.name = name;
        this.description = description;
        this.id = id;

        try {
            this.x = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " x", Integer.class);
            this.y = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " y", Integer.class);
            this.setEnabled(SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " enabled", Boolean.class));
        } catch (NullPointerException e) {
            this.x = x;
            this.y = y;
            this.enabled = false;
        }
    }

    public ModuleCreator(int id, String name, int x, int y) {
        this.name = name;
        this.description = null;
        this.id = id;

        try {
            this.x = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " x", Integer.class);
            this.y = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " y", Integer.class);
            this.setEnabled(SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase() + " enabled", Boolean.class));
        } catch (NullPointerException e) {
            this.x = x;
            this.y = y;
            this.enabled = false;
        }
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

    public void renderDummy() {
        render();
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

}
