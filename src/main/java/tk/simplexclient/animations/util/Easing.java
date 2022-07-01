package tk.simplexclient.animations.util;

@FunctionalInterface
public interface Easing {
    /**
     * @param value
     * @return animation formula
     */
    double ease(double value);
}