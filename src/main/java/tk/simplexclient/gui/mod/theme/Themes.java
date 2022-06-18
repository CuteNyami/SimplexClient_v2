package tk.simplexclient.gui.mod.theme;

public enum Themes {

    LIGHT,
    DARK,
    WINDOWS,
    MACOS,
    LINUX; //best theme ever lol

    private static Themes currentTheme;

    public static void setTheme(Themes theme) {
        currentTheme = theme;
    }

    public static Themes getCurrentTheme() {
        return currentTheme;
    }
}
