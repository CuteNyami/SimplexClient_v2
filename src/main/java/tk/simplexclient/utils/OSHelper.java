package tk.simplexclient.utils;

import java.nio.file.*;

public enum OSHelper {
    WINDOWS(Paths.get("AppData", "Roaming", ".minecraft")),
    MAC(Paths.get("Library", "Application Support", "minecraft")),
    LINUX(Paths.get(".minecraft"));

    private final Path mcPath;

    OSHelper(Path path) {
        this.mcPath = path;
    }

    public String getPath() {
        return Paths.get(System.getProperty("user.home")).resolve(mcPath).toAbsolutePath().toString();
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
