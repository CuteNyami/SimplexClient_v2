package tk.simplexclient.module;

import tk.simplexclient.SimplexClient;
import tk.simplexclient.json.JsonFile;

public class ModuleConfig extends JsonFile {
    public ModuleConfig() {
        super(SimplexClient.GSON, "simplex/modules/config.json");
        if (!exists()) {
            create();
            append("config-version", "1");
            save();
        }
        load();
    }

    public void saveModuleConfig() {
        for (ModuleCreator m : SimplexClient.getInstance().getModuleManager().getModules()) {
            append(m.getName().toLowerCase() + " x", m.getX());
            append(m.getName().toLowerCase() + " y", m.getY());
            append(m.getName().toLowerCase() + " enabled", m.isEnabled());
            save();
        }
    }
}
