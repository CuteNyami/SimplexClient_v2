package tk.simplexclient.module;

import tk.simplexclient.module.impl.ItemPhysics;
import tk.simplexclient.module.impl.MotionBlur;
import tk.simplexclient.module.impl.ToggleSprint;
import tk.simplexclient.module.impl.V1_7Visuals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    public final ItemPhysics itemPhysics = new ItemPhysics();

    public final ToggleSprint toggleSprint = new ToggleSprint();

    public final MotionBlur motionBlur = new MotionBlur();

    public final V1_7Visuals v1_7Visual = new V1_7Visuals();

    private final List<ModuleCreator> modules = new ArrayList<>();

    public void registerModules(ModuleCreator... modules) {
        Collections.addAll(this.modules, modules);
    }

    public List<ModuleCreator> getModules() {
        return this.modules;
    }

    public List<ModuleCreator> getEnabledModules() {
        return this.modules.stream().filter(ModuleCreator::isEnabled).collect(Collectors.toList());
    }
}
