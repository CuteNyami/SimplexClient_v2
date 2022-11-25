package tk.simplexclient.utils.shader;

import com.google.common.collect.*;
import lombok.*;
import lombok.experimental.*;
import org.apache.commons.io.*;
import org.jetbrains.annotations.*;
import tk.simplexclient.*;
import tk.simplexclient.gl.*;

import java.util.*;

import static org.lwjgl.opengl.GL20.*;

@UtilityClass public class ShaderLoader {

	private static final HashMap<String, Shader> shaders = Maps.newHashMap();

	@SneakyThrows
	public static @NotNull Shader loadShader(@NotNull String name, @NotNull String fragName, @NotNull String vertName) {

		int program = glCreateProgram();

		GlStateManager.pushMatrix();
		int vert = glCreateShader(GL_VERTEX_SHADER);
		int frag = glCreateShader(GL_FRAGMENT_SHADER);

		String vertPath = String.format("/assets/minecraft/simplex/shader/%s.vert", vertName);
		String fragPath = String.format("/assets/minecraft/simplex/shader/%s.frag", fragName);


		glShaderSource(vert,
					   IOUtils.toString(Objects.requireNonNull(SimplexClient.class.getResourceAsStream(vertPath)))
		);
		glShaderSource(frag,
					   IOUtils.toString(Objects.requireNonNull(SimplexClient.class.getResourceAsStream(fragPath)))
		);

		glCompileShader(vert);
		glCompileShader(frag);


		if(glGetShaderi(vert, GL_COMPILE_STATUS) == 0)
			throw new IllegalStateException("Failed to compile vertex shader");

		if(glGetShaderi(frag, GL_COMPILE_STATUS) == 0)
			throw new IllegalStateException("Failed to compile fragment shader");


		glAttachShader(program, vert);
		glAttachShader(program, frag);

		glLinkProgram(program);
		int status = glGetProgrami(program, GL_LINK_STATUS);

		if (status == 0)
			throw new IllegalStateException("Failed to link shader.");


		shaders.put(name, new Shader(program));
		GlStateManager.popMatrix();
		return shaders.get(name);
	}

	public static @NotNull Shader loadShader(@NotNull String name) {
		return loadShader(name, name, name);
	}




}
