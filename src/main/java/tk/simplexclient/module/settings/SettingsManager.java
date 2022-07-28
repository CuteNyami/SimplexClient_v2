package tk.simplexclient.module.settings;

import lombok.Getter;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gui.elements.GuiSlider;
import tk.simplexclient.gui.elements.ToggleBoxButton;
import tk.simplexclient.module.ModuleCreator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingsManager {

    @Getter
    private final HashMap<Integer, Field> options = new HashMap<>();

    @Getter
    private final HashMap<Field, GuiSlider> sliderMap = new HashMap<>();

    @Getter
    private final ArrayList<GuiSlider> sliderList = new ArrayList<>();

    @Getter
    private final HashMap<Field, ToggleBoxButton> toggleMap = new HashMap<>();

    @Getter
    private final ArrayList<ToggleBoxButton> toggleList = new ArrayList<>();

    @Getter
    private final ArrayList<ModuleCreator> modsWithSettings = new ArrayList<>();

    public void setup(ModuleCreator module) {
        Field[] fields = module.getClass().getDeclaredFields();
        for (Field field : fields) {
            Option optionAnnotation = field.getAnnotation(Option.class);

            if (optionAnnotation != null) {
                options.put(optionAnnotation.priority(), field);
                modsWithSettings.add(module);
            }

        }
    }

    public double getSliderValue(ModuleCreator module, Field field) {
        double val = 0.0;
        Option optionAnnotation = field.getAnnotation(Option.class);
        if (optionAnnotation != null && (field.getType() == int.class || field.getType() == float.class || field.getType() == double.class)) {
            try {
                field.setAccessible(true);
                val = SimplexClient.getInstance().getModuleConfig().get(field.toString(), Double.class) == null ? field.getDouble(module) : SimplexClient.getInstance().getModuleConfig().get(field.toString(), Double.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return val;
    }

    public boolean getToggleValue(ModuleCreator module, Field field) {
        boolean val = false;
        Option optionAnnotation = field.getAnnotation(Option.class);
        if (optionAnnotation != null && field.getType() == boolean.class) {
            try {
                field.setAccessible(true);
                val = SimplexClient.getInstance().getModuleConfig().get(field.toString(), Boolean.class) == null ? field.getBoolean(module) : SimplexClient.getInstance().getModuleConfig().get(field.toString(), Boolean.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return val;
    }

    public Field sliderField(ModuleCreator module) {
        Field[] fields = module.getClass().getDeclaredFields();
        Field sliderField = null;
        for (Field field : fields) {
            Option optionAnnotation = field.getAnnotation(Option.class);
            if (optionAnnotation != null && options.containsValue(field)) {
                System.out.println(field);
                sliderField = field;
            }
        }
        return sliderField;
    }

    public Option getOption(Field field) {
        return field.getAnnotation(Option.class);
    }
}
