package tk.simplexclient.shader;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import tk.simplexclient.gl.*;
import tk.simplexclient.utils.shader.*;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class RoundedShaderRenderer {
    private static final RoundedShaderRenderer instance = new RoundedShaderRenderer();
    private Shader shader;

    public static RoundedShaderRenderer getInstance() {
        return instance;
    }

    public void drawRound(ScaledResolution scaledResolution, float x, float y, float width, float height, float radius, Color color) {
        if (shader == null) shader = ShaderLoader.loadShader("rounded", "rounded", "vertex");
        GlStateManager.pushMatrix();
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.enableColorMaterial();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        shader.use();

        float scaledX = x * scaledResolution.getScaleFactor();
        float scaledY = y * scaledResolution.getScaleFactor();
        float scaledWidth = width * scaledResolution.getScaleFactor();
        float scaledHeight = height * scaledResolution.getScaleFactor();
        float scaledRadius = radius * scaledResolution.getScaleFactor();
        shader.getUniform("loc").set(scaledX, (Minecraft.getMinecraft().displayHeight - scaledHeight) - scaledY);
        shader.getUniform("size").set(scaledWidth, scaledHeight);
        shader.getUniform("radius").set(scaledRadius);
        shader.getUniform("color").set(color);


        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(x - 1, y - 1);
            glTexCoord2f(0, 1);
            glVertex2f(x - 1, y + height + 1);
            glTexCoord2f(1, 1);
            glVertex2f(x + width + 1, y + height + 1);
            glTexCoord2f(1, 0);
            glVertex2f(x + width + 1, y - 1);
        }
        glEnd();

        shader.unload();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.disableColorMaterial();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}