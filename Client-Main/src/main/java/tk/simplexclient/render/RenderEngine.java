package tk.simplexclient.render;

import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import tk.simplexclient.gl.GlStateManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.io.File;

import static org.lwjgl.opengl.GL11.*;

public class RenderEngine {
    @Getter
    private final Minecraft minecraft;
    @Getter
    private final int width, height;

    @Getter
    private static int mouseX = 0, mouseY = 0;

    public RenderEngine(Minecraft minecraft, int width, int height) {
        this.minecraft = minecraft;
        this.width = width;
        this.height = height;
    }

    public static void init(int mouseX, int mouseY) {
        RenderEngine.mouseX = mouseX;
        RenderEngine.mouseY = mouseY;
    }

    @SneakyThrows
    public static boolean texture(File file, int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        readAndBindImage(ImageIO.read(file));
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
        return Mouse.isButtonDown(0) && mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static boolean texture(ResourceLocation resourceLocation, int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
        return Mouse.isButtonDown(0) && mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    @SneakyThrows
    private static int readAndBindImage(BufferedImage img) {
        int[] pixels = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = pixels[y * img.getWidth() + x];
                byteBuffer.put((byte) ((pixel >> 16) & 0xFF));
                byteBuffer.put((byte) ((pixel >> 8) & 0xFF));
                byteBuffer.put((byte) (pixel & 0xFF));
                byteBuffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        byteBuffer.flip();
        int glTextureId = glGenTextures();
        GlStateManager.bindTexture(glTextureId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
        return glTextureId;
    }
}
