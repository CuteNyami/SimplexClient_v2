package tk.simplexclient.render;

import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Shader {

    private final int program;

    private int fragment, vertex;

    private final Map<String, Integer> uniforms = new HashMap<>();

    public Shader(String... attributes) {
        program = GL20.glCreateProgram();
        for (int i = 0; i < attributes.length; i++) {
            GL20.glBindAttribLocation(program, i, attributes[i]);
        }
    }

    public void fragment(String file) {
        fragment = createShader(file, GL20.GL_FRAGMENT_SHADER);
    }

    public void vertex(String file) {
        vertex = createShader(file, GL20.GL_VERTEX_SHADER);
    }

    public void link() {
        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == 0) {
            throw new RuntimeException("Error linking Shader code: " + GL20.glGetProgramInfoLog(program, 1024));
        }
        if (fragment != 0) GL20.glDetachShader(program, fragment);
        if (vertex != 0) GL20.glDetachShader(program, vertex);

        GL20.glValidateProgram(program);

        if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(program, 1024));
        }
    }

    public void bind() {
        GL20.glUseProgram(program);
    }

    public void unbind() {
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (program != 0) GL20.glDeleteProgram(program);
    }

    public int createShader(String file, int shader) {
        try {
            int id = GL20.glCreateShader(shader);
            if (id == 0) throw new RuntimeException("Error creating Shader type: " + shader);

            InputStream input = Shader.class.getResourceAsStream(file);
            if (input == null) return -1;
            StringBuilder builder = new StringBuilder();

            int read;
            while ((read = input.read()) != -1) builder.append(((char) read));
            input.close();

            GL20.glShaderSource(id, builder);
            GL20.glCompileShader(id);

            if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == 0) {
                throw new RuntimeException("Error compiling Shader code: " + GL20.glGetShaderInfoLog(id, 1024));
            }

            GL20.glAttachShader(program, id);

            return id;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUniform(String name) {
        int location = GL20.glGetUniformLocation(program, name);
        if (location < 0) throw new RuntimeException("Could not find uniform: " + name);
        uniforms.put(name, location);
    }

    public void setVector(String name, Vector2f vector) {
        GL20.glUniform2f(uniforms.get(name), vector.x, vector.y);
    }

    public void setVector(String name, Vector3f vector) {
        GL20.glUniform3f(uniforms.get(name), vector.x, vector.y, vector.z);
    }

}
