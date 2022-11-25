package tk.simplexclient.event.impl;

import lombok.AllArgsConstructor;
import tk.simplexclient.event.Event;

@AllArgsConstructor
public class ServerJoinEvent extends Event {

    public final String ip;
    public final int port;

}
