package tk.simplexclient.utils.shader;

import com.google.common.collect.*;
import lombok.*;
import org.jetbrains.annotations.*;

import java.awt.*;
import java.util.*;

import static org.lwjgl.opengl.GL20.*;

@RequiredArgsConstructor @Data public class Shader {
	private final int program;
	private HashMap<String, Uniform> uniforms = Maps.newHashMap();

	public void use() {
		glUseProgram(program);
	}

	public Uniform getUniform(String name) {
		if(uniforms.containsKey(name))
			return uniforms.get(name);
		uniforms.put(name, new Uniform(this, glGetUniformLocation(program, name)));
		return uniforms.get(name);
	}

	public void unload() {
		glUseProgram(0);
	}

	@RequiredArgsConstructor @Data public static class Uniform {

		@NotNull private final Shader parent;
		private final int loc;
		private float[] value = new float[0];

		public void set(float @NotNull ... fl) {
			this.value = new float[fl.length];
			switch (fl.length) {
				case 1:
					glUniform1f(loc, fl[0]);
					break;
				case 2:
					glUniform2f(loc, fl[0], fl[1]);
					break;
				case 3:
					glUniform3f(loc, fl[0], fl[1], fl[2]);
					break;
				case 4:
					glUniform4f(loc, fl[0], fl[1], fl[2], fl[3]);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + fl.length);
			}
		}

		public void set(@NotNull Color color) {
			set(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
		}
	}
}
