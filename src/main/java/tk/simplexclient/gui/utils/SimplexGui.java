package tk.simplexclient.gui.utils;

import net.minecraft.client.gui.Gui;

public class SimplexGui extends Gui {

    @Override
    public void drawHorizontalLine(int startX, int endX, int y, int color) {
        super.drawHorizontalLine(startX, endX, y, color);
    }

    @Override
    public void drawVerticalLine(int x, int startY, int endY, int color) {
        super.drawVerticalLine(x, startY, endY, color);
    }
}
