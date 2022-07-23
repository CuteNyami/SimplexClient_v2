package tk.simplexclient.utils;

import java.io.File;

public enum OSHelper {
    WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".minecraft"),
    MAC("Library" + File.separator + "Application Support" + File.separator + "minecraft"),
    LINUX(".minecraft");

    private final String mc;

    OSHelper(String mc) {
        this.mc = File.separator + mc + File.separator;
    }

    public String getMc() {
        return System.getProperty("user.home") + this.mc;
    }

    public static OSHelper getOS() {
        String currentOS = System.getProperty("os.name").toLowerCase();
        if (currentOS.startsWith("windows")) {
            return WINDOWS;
        }
        if (currentOS.startsWith("mac")) {
            return MAC;
        }
        return LINUX;
    }
}
