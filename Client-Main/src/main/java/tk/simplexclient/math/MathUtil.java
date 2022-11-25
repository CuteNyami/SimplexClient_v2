package tk.simplexclient.math;

import net.minecraft.util.*;

import java.awt.*;

public class MathUtil
{
    private static final double[] a;
    private static final double[] b;

    public static double getAngle(int nameInt) {
        nameInt %= 360;
        return MathUtil.b[nameInt];
    }

    public static double getRightAngle(int nameInt) {
        nameInt += 90;
        nameInt %= 360;
        return MathUtil.b[nameInt];
    }

    private static float snapToStep(float value, final float valueStep) {
        if (valueStep > 0.0f) {
            value = valueStep * Math.round(value / valueStep);
        }
        return value;
    }

    public static float normalizeValue(final float value, final float valueMin, final float valueMax, final float valueStep) {
        return MathHelper.clamp_float((snapToStepClamp(value, valueMin, valueMax, valueStep) - valueMin) / (valueMax - valueMin), 0.0f, 1.0f);
    }

    private static float snapToStepClamp(float value, final float valueMin, final float valueMax, final float valueStep) {
        value = snapToStep(value, valueStep);
        return MathHelper.clamp_float(value, valueMin, valueMax);
    }

    public static float denormalizeValue(final float value, final float valueMin, final float valueMax, final float valueStep) {
        return snapToStepClamp(valueMin + (valueMax - valueMin) * MathHelper.clamp_float(value, 0.0f, 1.0f), valueMin, valueMax, valueStep);
    }

    public static int lerp(int a, int b, float f) {
        return (int) (a + (b - a) * f);
    }

    public static Color lerp(Color a, Color b, float f) {
        return new Color(lerp(a.getRed(), b.getRed(), f), lerp(a.getGreen(), b.getGreen(), f), lerp(a.getBlue(), b.getBlue(), f), lerp(a.getAlpha(), b.getAlpha(), f));
    }

    static {
        a = new double[65536];
        b = new double[360];
        for (int i = 0; i < 65536; ++i) {
            MathUtil.a[i] = Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
        for (int i = 0; i < 360; ++i) {
            MathUtil.b[i] = Math.sin(Math.toRadians(i));
        }
    }
}
