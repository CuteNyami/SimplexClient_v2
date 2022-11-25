package tk.simplexclient.render;

import lombok.Getter;

import java.awt.*;

public abstract class UIComponent {

    @Getter
    private final int x, y, width, height;

    @Getter
    private final Color color;

    @Getter
    private final ComponentPosition position;

    public UIComponent(int x, int y, int width, int height, Color color, ComponentPosition position) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.position = position;
    }

    public abstract void render(int mouseX, int mouseY);
}
