package tk.simplexclient.module;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Module {
    private final boolean enabled;
    private final ArrayList<Integer> pos;
}
