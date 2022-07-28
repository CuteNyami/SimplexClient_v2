package tk.simplexclient.module;

import tk.simplexclient.module.impl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {

    public final ItemPhysics itemPhysics = new ItemPhysics();

    public final ToggleSprint toggleSprint = new ToggleSprint();

    public final MotionBlur motionBlur = new MotionBlur();

    public final TNTTimer tntTimer = new TNTTimer();

    public final V1_7Visuals v1_7Visual = new V1_7Visuals();

    private final List<ModuleCreator> modules = new ArrayList<>();

    public void registerModules(ModuleCreator... modules) {
        this.modules.addAll(Arrays.asList(modules));
    }

    public List<ModuleCreator> getModules() {
        return this.modules;
    }

    public List<ModuleCreator> getEnabledModules() {
        return this.modules.stream().filter(ModuleCreator::isEnabled).collect(Collectors.toList());
    }
}
