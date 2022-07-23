package tk.simplexclient.gui.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import tk.simplexclient.font.FontRenderer;

public class SplashProgress {

    private static final int MAX = 7;
    private static int progress = 0;
    private static String current;
    private static ResourceLocation background;
    private static FontRenderer fr;

    public static void update() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) return;
        draw(Minecraft.getMinecraft().getTextureManager());
    }

    public static void setProgress(int givenProgress, String text) {
        progress = givenProgress;
    }

    public static void draw(TextureManager textureManager) {

    }

    public static void drawProgress() {

    }

    private static void resetTextureState() {

    }

}
