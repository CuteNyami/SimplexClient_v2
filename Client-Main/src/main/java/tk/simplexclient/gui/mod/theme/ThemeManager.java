package tk.simplexclient.gui.mod.theme;

import java.util.HashMap;

public class ThemeManager {

    private static final HashMap<Themes, Theme> themes = new HashMap<>();

    private static Themes currentTheme;

    public static void registerTheme(Theme theme) {
        themes.put(theme.getTheme(), theme);
    }

    public static void setTheme(Themes theme) {
        currentTheme = theme;
    }

    public static HashMap<Themes, Theme> getThemes() {
        return themes;
    }

    public static Theme getCurrentTheme() {
        return themes.get(currentTheme);
    }
}
