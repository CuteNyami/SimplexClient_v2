package tk.simplexclient.event.impl;

import lombok.AllArgsConstructor;
import tk.simplexclient.event.Event;

@AllArgsConstructor
public class HitOverlayEvent extends Event {

    public float r;
    public float g;
    public float b;
    public  float a;

}
