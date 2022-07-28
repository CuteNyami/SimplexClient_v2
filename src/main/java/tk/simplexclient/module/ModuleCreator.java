package tk.simplexclient.module;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.event.EventManager;
import tk.simplexclient.font.FontRenderer;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.gui.mod.GuiModSettings;
import tk.simplexclient.module.settings.Option;

import java.awt.*;
import java.lang.reflect.Field;

@Setter
public abstract class ModuleCreator {

    @Getter private String name, description;

    public int x, y;

    @Getter private boolean enabled;

    @Getter public final FontRenderer fr = new FontRenderer("smooth", 15.0f);

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

    @SneakyThrows
    public double getValDouble(String field) {
        Field field1 = this.getClass().getDeclaredField(field);
        if (!(Minecraft.getMinecraft().currentScreen instanceof GuiModSettings)) {
            return SimplexClient.getInstance().getSettingsBuilder().getSliderValue(this, field1);
        } else {
            double val = 0.0;
            Option optionAnnotation = field1.getAnnotation(Option.class);
            if (optionAnnotation != null && SimplexClient.getInstance().getSettingsBuilder().getSliderMap().containsKey(field1)) {
                field1.setAccessible(true);
                val = SimplexClient.getInstance().getSettingsBuilder().getSliderMap().get(field1).getValue();
            }
            return val;
        }
    }

    @SneakyThrows
    public boolean getValBoolean(String field) {
        Field field1 = this.getClass().getDeclaredField(field);
        if (!(Minecraft.getMinecraft().currentScreen instanceof GuiModSettings)) {
            return SimplexClient.getInstance().getSettingsBuilder().getToggleValue(this, field1);
        } else {
            boolean val = false;
            Option optionAnnotation = field1.getAnnotation(Option.class);
            if (optionAnnotation != null && field1.getType() == boolean.class && SimplexClient.getInstance().getSettingsBuilder().getToggleMap().containsKey(field1)) {
                field1.setAccessible(true);
                val = SimplexClient.getInstance().getSettingsBuilder().getToggleMap().get(field1).isToggled();
            }
            return val;
        }
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }


    public int getX() {
        //ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        return x;
    }

    public int getY() {
       // ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        return y;
    }

    public void setX(int x) {
       // ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        this.x = x;
    }

    public void setY(int y) {
        //ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        this.y = y;
    }
}
