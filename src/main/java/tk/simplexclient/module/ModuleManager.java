package tk.simplexclient.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    private final List<ModuleCreator> modules = new ArrayList<>();

    public void registerModules(ModuleCreator... modules) {
        Collections.addAll(this.modules, modules);
    }

    public List<ModuleCreator> getModule() {
        return this.modules;
    }

    public List<ModuleCreator> getEnabledModules() {
        return this.modules.stream().filter(ModuleCreator::isEnabled).collect(Collectors.toList());
    }
}
