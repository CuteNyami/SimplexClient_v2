package tk.simplexclient.shader;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import tk.simplexclient.gl.*;
import tk.simplexclient.utils.shader.*;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/*public class RoundedShaderRenderer {

    static Minecraft mc = Minecraft.getMinecraft();

    static RoundedShaderRenderer INSTANCE;
    static int program;

    public static RoundedShaderRenderer getInstance() {
        if (INSTANCE == null) {
            program = glCreateProgram();
            int fragID, vertexID;
            try {
                fragID = createShader(mc.getResourceManager().getResource(new ResourceLocation("simplex/shader/rounded.frag")).getInputStream(), GL_FRAGMENT_SHADER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                vertexID = createShader(Minecraft.getMinecraft().getResourceManager()
                        .getResource(new ResourceLocation("simplex/shader/vertex.vert")).getInputStream(), GL_VERTEX_SHADER);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            GlStateManager.pushMatrix();
            glAttachShader(program, fragID);
            glAttachShader(program, vertexID);

            GlStateManager.popMatrix();

            glLinkProgram(program); //link program
            int status = glGetProgrami(program, GL_LINK_STATUS);

            if (status == 0) {
                throw new IllegalStateException("Shader failed to link!");
            }
            INSTANCE = new RoundedShaderRenderer();
        }
        return INSTANCE;
    }

    public void load() {
        glUseProgram(program);
    }

    public void unload() {
        glUseProgram(0);
    }

    static int createShader(InputStream inputStream, int shaderType) {
        int shader = glCreateShader(shaderType);
        GlStateManager.pushMatrix();
        glShaderSource(shader, readInputStream(inputStream));
        glCompileShader(shader);

        GlStateManager.popMatrix();

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            throw new IllegalStateException("Shader failed to compile! : " + shaderType);
        }

        return shader;
    }

    static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public void drawRound(ScaledResolution sr, float x, float y, float width, float height, float radius, Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GlStateManager.enableColorMaterial();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        getInstance().load();

        getInstance().setUniformFloat("loc", x * sr.getScaleFactor(),
                (Minecraft.getMinecraft().displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor()));
        getInstance().setUniformFloat("size", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        getInstance().setUniformFloat("radius", radius * sr.getScaleFactor());
        getInstance().setUniformFloat("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x - 1, y - 1);
        glTexCoord2f(0, 1);
        glVertex2f(x - 1, y + height + 1);
        glTexCoord2f(1, 1);
        glVertex2f(x + width + 1, y + height + 1);
        glTexCoord2f(1, 0);
        glVertex2f(x + width + 1, y - 1);
        glEnd();
        getInstance().unload();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.disableColorMaterial();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }


    public void setUniformFloat(String name, float... arguments) {
        int loc = glGetUniformLocation(program, name);
        switch (arguments.length) {
            case 1:
                glUniform1f(loc, arguments[0]);
                break;
            case 2:
                glUniform2f(loc, arguments[0], arguments[1]);
                break;
            case 3:
                glUniform3f(loc, arguments[0], arguments[1], arguments[2]);
                break;
            case 4:
                glUniform4f(loc, arguments[0], arguments[1], arguments[2], arguments[3]);
                break;
        }
    }
}
*/

public class RoundedShaderRenderer {
	private static final RoundedShaderRenderer instance = new RoundedShaderRenderer();
	private Shader shader;

	public static RoundedShaderRenderer getInstance() {
		return instance;
	}

	public void drawRound(ScaledResolution scaledResolution,
						  float x,
						  float y,
						  float width,
						  float height,
						  float radius,
						  Color color) {
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
		float scaledRadius = radius *  scaledResolution.getScaleFactor();
		shader.getUniform("loc").set(scaledX, (Minecraft.getMinecraft().displayHeight - scaledHeight) - scaledY);
		shader.getUniform("size").set(scaledWidth, scaledHeight);
		shader.getUniform("radius").set(scaledRadius);
		shader.getUniform("color").set(color);


		glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x - 1, y - 1);
			glTexCoord2f(0, 1);
			glVertex2f(x - 1, y + height + 1);
			glTexCoord2f(1, 1);
			glVertex2f(x + width + 1, y + height + 1);
			glTexCoord2f(1, 0);
			glVertex2f(x + width + 1, y - 1);
		glEnd();

		shader.unload();
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
		GlStateManager.disableColorMaterial();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}