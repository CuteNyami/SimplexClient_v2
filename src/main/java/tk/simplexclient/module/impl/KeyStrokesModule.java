package tk.simplexclient.module.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.opengl.GL11;
import tk.simplexclient.gl.GLRectUtils;
import tk.simplexclient.module.ModuleCreator;

import java.awt.*;

public class KeyStrokesModule extends ModuleCreator {

    private KeystrokesMode mode;

    public KeyStrokesModule() {
        super(2, "keystrokes", 200, 200);
    }

    @Override
    public void render() {
        GL11.glPushMatrix();

        mode = KeystrokesMode.WASD_JUMP_MOUSE;

        for (Key key : mode.getKeys()) {

            int textWidth = (int) fr.getWidth(key.getName());

            Gui.drawRect(getX() + key.getX(), getY() + key.getY(), getX() + key.getX() + key.getWidth(), getY() + key.getY() + key.getHeight(), key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 120).getRGB());

            if (!key.getName().contains("-")) {
                fr.drawString(key.getName(), getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 0.45F, getY() + key.getY() + key.getHeight() / 2 - 5, key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
            } else {
                Minecraft.getMinecraft().fontRendererObj.drawString(key.getName(), getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 5, getY() + key.getY() + key.getHeight() / 2 - 4, key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
            }
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderDummy(int mouseX, int mouseY) {
        GL11.glPushMatrix();

        mode = KeystrokesMode.WASD_JUMP_MOUSE;

        for (Key key : mode.getKeys()) {

            int textWidth = (int) fr.getWidth(key.getName());

            Gui.drawRect(getX() + key.getX(), getY() + key.getY(), getX() + key.getX() + key.getWidth(), getY() + key.getY() + key.getHeight(), key.isDown() ? new Color(255, 255, 255, 102).getRGB() : new Color(0, 0, 0, 120).getRGB());

            if (!key.getName().contains("-")) {
                fr.drawString(key.getName(), getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 0.45F, getY() + key.getY() + key.getHeight() / 2 - 5, key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
            } else {
                Minecraft.getMinecraft().fontRendererObj.drawString(key.getName(), getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 5, getY() + key.getY() + key.getHeight() / 2 - 4, key.isDown() ? new Color(0, 0, 0, 255).getRGB() : -1);
            }
        }

        GL11.glPopMatrix();

        GLRectUtils.drawRectOutline(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0.25f, new Color(0, 0, 0, 160).getRGB());
        GLRectUtils.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), new Color(255, 255, 255, 70).getRGB());

        super.renderDummy(mouseX, mouseY);
    }

    public static class Key {
        public static Minecraft mc = Minecraft.getMinecraft();

        private static final Key W = new Key("W", mc.gameSettings.keyBindForward, 21, 1, 18, 18);
        private static final Key A = new Key("A", mc.gameSettings.keyBindLeft, 1, 21, 18, 18);
        private static final Key S = new Key("S", mc.gameSettings.keyBindBack, 21, 21, 18, 18);
        private static final Key D = new Key("D", mc.gameSettings.keyBindRight, 41, 21, 18, 18);

        private static final Key LMB = new Key("LMB", mc.gameSettings.keyBindAttack, 1, 41, 28, 18);
        private static final Key RMB = new Key("RMB", mc.gameSettings.keyBindUseItem, 31, 41, 28, 18);

        private static final Key Jump1 = new Key("§m---", mc.gameSettings.keyBindJump, 1, 41, 58, 10);
        private static final Key Jump2 = new Key("§m---", mc.gameSettings.keyBindJump, 1, 61, 58, 10);

        private final String name;
        private final KeyBinding keyBind;
        private final int x, y, w, h;

        public Key(String name, KeyBinding keyBind, int x, int y, int w, int h) {
            this.name = name;
            this.keyBind = keyBind;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public boolean isDown() {
            return keyBind.isKeyDown();
        }

        public int getHeight() {
            return h;
        }

        public int getWidth() {
            return w;
        }

        public String getName() {
            return name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    @Override
    public int getWidth() {
        return 60;
    }

    @Override
    public int getHeight() {
        return 72;
    }

    public static Color outlineColor;

    public enum KeystrokesMode {
        WASD(Key.W, Key.A, Key.S, Key.D),
        WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
        WASD_JUMP(Key.W, Key.A, Key.S, Key.D, Key.Jump1),
        WASD_JUMP_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, Key.Jump2);

        private final Key[] keys;
        private int width, height;

        KeystrokesMode(Key... keysIn) {
            this.keys = keysIn;

            for (Key key : keys) {
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());
            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public Key[] getKeys() {
            return keys;
        }
    }
}
