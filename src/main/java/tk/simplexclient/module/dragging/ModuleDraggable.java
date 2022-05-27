package tk.simplexclient.module.dragging;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;

@SuppressWarnings("all")
@Getter public class ModuleDraggable {

    @Setter private int color;

    @Setter private int x, y, width, height, lastX, lastY;

    private boolean dragging;

    public ModuleDraggable(int x, int y, int width, int height, int color) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void draw(int mouseX, int mouseY) {
        draggingFix(mouseX, mouseY);
        Gui.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), this.getColor());
        boolean mouseOverX = (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth());
        boolean mouseOverY = (mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight());
        if (mouseOverX && mouseOverY) {
            if (Mouse.isButtonDown(0)) {
                if (!this.dragging) {
                    this.lastX = x - mouseX;
                    this.lastY = y - mouseY;
                    this.dragging = true;
                }
            }
        }
    }

    private void draggingFix(int mouseX, int mouseY) {
        if (this.dragging) {
            this.x = mouseX + this.lastX;
            this.y = mouseY + this.lastY;
            if (!Mouse.isButtonDown(0)) this.dragging = false;
        }
    }
}
