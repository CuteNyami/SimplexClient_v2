package tk.simplexclient.animations.util;

public class ColorMath {

    public static int extractAlpha(int color) {
        return color >> 24 & 255;
    }

    public static int extractRed(int color) {
        return color >> 16 & 255;
    }

    public static int extractGreen(int color) {
        return color >> 8 & 255;
    }

    public static int extractBlue(int color) {
        return color & 255;
    }

}