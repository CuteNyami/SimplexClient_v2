package tk.simplexclient.module.impl;

import lombok.SneakyThrows;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.XPOrbOverlayEvent;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;

import java.awt.*;

public class XPOrbColor extends ModuleCreator {

    @Option(text = "Color", colorPickerAlphaSlider = false)
    private Color color = new Color(63, 255, 12, 128);

    public float[] colorFloat;

    public XPOrbColor() {
        super("xporb-color", 0,0);
    }

    @SneakyThrows
    @EventTarget
    public void onXPOrbOverlay(XPOrbOverlayEvent event) {
        if (isEnabled()) {
            event.r = getValColor("color").getRed();
            event.g = getValColor("color").getGreen();
            event.b = getValColor("color").getBlue();
            event.a = getValColor("color").getAlpha();
        }
    }
}
