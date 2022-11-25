package tk.simplexclient.module.settings;

import lombok.Getter;
import tk.simplexclient.SimplexClient;
import tk.simplexclient.gui.elements.ColorPicker;
import tk.simplexclient.gui.elements.GuiSlider;
import tk.simplexclient.gui.elements.ToggleBoxButton;
import tk.simplexclient.module.ModuleCreator;
import tk.simplexclient.ui.elements.InputField;

import java.awt.*;
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
    public final HashMap<Field, ColorPicker> colorPickerMap = new HashMap<>();

    @Getter
    private final HashMap<ColorPicker, Float[]> colorMap = new HashMap<>();

    @Getter
    private final ArrayList<ToggleBoxButton> toggleList = new ArrayList<>();

    @Getter
    private final ArrayList<ColorPicker> colorList = new ArrayList<>();

    @Getter
    private final ArrayList<InputField> inputList = new ArrayList<>();

    @Getter
    private final HashMap<Field, InputField> inputMap = new HashMap<>();

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

    public String getInputValue(ModuleCreator module, Field field) {
        String val = "";
        Option optionAnnotation = field.getAnnotation(Option.class);
        if (optionAnnotation != null && field.getType() == String.class) {
            try {
                field.setAccessible(true);
                val = SimplexClient.getInstance().getModuleConfig().get(field.toString(), String.class) == null ? (String) field.get(module) : SimplexClient.getInstance().getModuleConfig().get(field.toString(), String.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return val;
    }

    public Color getColorValue(ModuleCreator module, Field field) {
        Color val = new Color(255, 255, 255);
        Option optionAnnotation = field.getAnnotation(Option.class);
        if (optionAnnotation != null && field.getType() == Color.class) {
            try {
                field.setAccessible(true);
                val = SimplexClient.getInstance().getModuleConfig().get(field + " color", Color.class) == null ? (Color) field.get(module) : SimplexClient.getInstance().getModuleConfig().get(field + " color", Color.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return val;
    }

    public int getColorValueInt(ModuleCreator module, Field field) {
        int val = new Color(255, 255, 255).getRGB();
        Option optionAnnotation = field.getAnnotation(Option.class);
        if (optionAnnotation != null && field.getType() == Color.class) {
            try {
                field.setAccessible(true);
                Color color = (Color) field.get(module);
                val = SimplexClient.getInstance().getModuleConfig().get(field + " color", Color.class) == null ? color.getRGB() : SimplexClient.getInstance().getModuleConfig().get(field + " color", Color.class).getRGB();
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
                sliderField = field;
            }
        }
        return sliderField;
    }

    public Option getOption(Field field) {
        return field.getAnnotation(Option.class);
    }
}
