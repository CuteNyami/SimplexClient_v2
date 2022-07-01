package tk.simplexclient.animations.type;

import tk.simplexclient.animations.Animation;
import tk.simplexclient.animations.util.ColorMath;
import tk.simplexclient.animations.util.Easing;
import tk.simplexclient.animations.util.Easings;

import java.awt.*;

/**
 * Use if u need to animation the color value
 */
public class ColorAnimation {

    private final Animation red = new Animation();
    private final Animation green = new Animation();
    private final Animation blue = new Animation();
    private final Animation alpha = new Animation();

    /**
     * Main method, use to animate color
     */
    public void animate(int colorTo, double duration) {
        animate(colorTo, duration, false);
    }

    /**
     * Main method, use to animate color
     */
    public void animate(int colorTo, double duration, boolean safe) {
        animate(colorTo, duration, Easings.NONE, safe);
    }

    /**
     * Main method, use to animate color
     */
    public void animate(int colorTo, double duration, Easing easing, boolean safe) {
        getRed().animate(ColorMath.extractRed(colorTo), duration, easing, safe);
        getGreen().animate(ColorMath.extractGreen(colorTo), duration, easing, safe);
        getBlue().animate(ColorMath.extractBlue(colorTo), duration, easing, safe);
        getAlpha().animate(ColorMath.extractAlpha(colorTo), duration, easing, safe);
    }

    /**
     * Main method, use to animate color
     */
    public void animate(Color colorTo, double duration, Easing easing, boolean safe) {
        animate(colorTo.getRGB(), duration, easing, safe);
    }

    /**
     * Main method, use to animate color
     */
    public void animate(Color colorTo, double duration, boolean safe) {
        animate(colorTo.getRGB(), duration, Easings.NONE, safe);
    }

    /**
     * Main method, use to animate color
     */
    public void animate(Color colorTo, double duration) {
        animate(colorTo.getRGB(), duration, false);
    }

    /**
     * Updates all colors
     *
     * @return all colors alive
     */
    public boolean update() {
        return ((getRed().update() &&
                getGreen().update() &&
                getBlue().update()) &&
                getAlpha().update())
                ||
                (getRed().update() &&
                        getGreen().update() &&
                        getBlue().update())
                ;
    }

    /**
     * @return all colors alive
     */
    public boolean isAlive() {
        return ((getRed().isAlive() && getGreen().isAlive() && getBlue().isAlive()) && getAlpha().isAlive()) || (getRed().isAlive() && getGreen().isAlive() && getBlue().isAlive());
    }

    /**
     * @return all colors done
     */
    public boolean isDone() {
        return !isAlive();
    }

    /**
     * Build java.awt.Color
     *
     * @return java.awt.Color
     */
    public Color getColor() {
        return new Color((int) getRed().getValue(), (int) getGreen().getValue(), (int) getBlue().getValue(), (int) getAlpha().getValue());
    }

    /**
     * return RGB color code
     *
     * @return java.awt.Color#getRGB()
     */
    public int getHex() {
        return getColor().getRGB();
    }

    public Animation getRed() {
        return red;
    }

    public Animation getGreen() {
        return green;
    }

    public Animation getBlue() {
        return blue;
    }

    public Animation getAlpha() {
        return alpha;
    }
}