package tk.simplexclient.module;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.EventManager;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;

import java.awt.*;

@Getter @Setter
public abstract class ModuleCreator {

    private String name, description;

    private int x, y;

    private boolean enabled;

    public final FontRenderer fr = new FontRenderer("smooth", 15.0f);

    public ModuleCreator(String name, String description, int x, int y) {
        this.name = name;
        this.description = description;

        try {
            Module module = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase(), Module.class);
            this.enabled = module.isEnabled();
            this.x = module.getPos().get(0);
            this.y = module.getPos().get(1);
        } catch (NullPointerException e) {
            this.x = x;
            this.y = y;
            this.enabled = false;
        }
    }

    public ModuleCreator(String name, int x, int y) {
        this.name = name;
        this.description = null;

        try {
            Module module = SimplexClient.getInstance().getModuleConfig().get(name.toLowerCase(), Module.class);
            this.enabled = module.isEnabled();
            this.x = module.getPos().get(0);
            this.y = module.getPos().get(1);
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

    public void render() {
    }

    public void renderDummy(int width, int height) {
        render();
    }

    public void onEnable() {
        EventManager.register(this);
    }

    public void onDisable() {
        EventManager.unregister(this);
    }

    public void toggle() {setEnabled(!enabled);}

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

}
