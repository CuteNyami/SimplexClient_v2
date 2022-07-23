package tk.simplexclient.module;

import tk.simplexclient.SimplexClient;
import tk.simplexclient.json.JsonFile;

import java.util.ArrayList;

public class ModuleConfig extends JsonFile {
    public ModuleConfig() {
        super(SimplexClient.GSON, "simplex/modules/config.json");
        if (!exists()) {
            create();
            append("config-version", "0");
            save();
        }
        load();
        if (!get("config-version", String.class).equals("0")) {
            getFile().delete();
            create();
            append("config-version", "0");
            save();
        }
    }

    public void saveModuleConfig() {
        for (ModuleCreator m : SimplexClient.getInstance().getModuleManager().getModules()) {
            //append(m.getName().toLowerCase() + " x", m.getX());
            //append(m.getName().toLowerCase() + " y", m.getY());
            //append(m.getName().toLowerCase() + " enabled", m.isEnabled());
            ArrayList<Integer> pos = new ArrayList<>();
            pos.add(m.getX());
            pos.add(m.getY());
            append(m.getName().toLowerCase(), new Module(m.isEnabled(), pos));
            save();
        }
    }

    public void saveItemSize(double size) {
        append("item_size", size);
        save();
    }

    public double getItemSize() {
        return get("item_size", Double.class) == null ? 0.4 : get("item_size", Double.class);
    }
}
