package fr.eseo.e3a.myminifarm.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The Utils class provides utility methods for the application.
 * It contains methods to get the application data directory based on the operating system.
 */
public final class Utils {

    /**
     * Get the application data directory based on the operating system.
     * <br>
     * - Windows: %APPDATA%/MyMiniFarm
     * <br>
     * - macOS: ~/Library/Application Support/MyMiniFarm
     * <br>
     * - Linux: ~/.config/MyMiniFarm
     *
     * @return the path to the application data directory
     */
    public static Path getAppDataDir() {
        String appFolderName = "MyMiniFarm";
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");

        if (os.contains("win")) {
            // Windows → %APPDATA%
            String appData = System.getenv("APPDATA");
            return Paths.get(appData, appFolderName);
        } else if (os.contains("mac")) {
            // macOS → ~/Library/Application Support
            return Paths.get(userHome, "Library", "Application Support", appFolderName);
        } else {
            // Linux → ~/.config/
            return Paths.get(userHome, ".config", appFolderName);
        }
    }
}
