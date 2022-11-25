package tk.simplexclient.module.impl;

import lombok.SneakyThrows;
import tk.simplexclient.event.EventTarget;
import tk.simplexclient.event.impl.HitOverlayEvent;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.module.settings.Option;

import java.awt.*;

public class HitColor extends ModuleCreator {

    @Option(text = "Color")
    private Color color = new Color(255, 0, 0, 73);

    public float[] colorFloat;

    public HitColor() {
        super("hit-color", 0,0);
    }

    @SneakyThrows
    @EventTarget
    public void onHitOverlay(HitOverlayEvent event) {
        if (isEnabled()) {
            event.r = getValColor("color").getRed() / 255.0F;
            event.g = getValColor("color").getGreen() / 255.0F;
            event.b = getValColor("color").getBlue() / 255.0F;
            event.a = getValColor("color").getAlpha() / 255.0F;
        }
    }
}
