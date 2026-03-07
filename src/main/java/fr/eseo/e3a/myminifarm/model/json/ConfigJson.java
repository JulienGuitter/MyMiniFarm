package fr.eseo.e3a.myminifarm.model.json;

import java.util.HashMap;

/**
 * The ConfigJson class represents the configuration JSON format.
 */
public class ConfigJson {
    private HashMap<String, String> keys = new HashMap<>();
    private boolean fullscreen;
    private int volume;

    private int ups;
    private int fps;

    /**
     * Getter for the keys.
     *
     * @return A HashMap containing the keys and their corresponding actions.
     */
    public HashMap<String, String> getKeys() {
        return keys;
    }

    /**
     * Setter for the keys.
     *
     * @param keys A HashMap containing the keys and their corresponding actions.
     */
    public void setKeys(HashMap<String, String> keys) {
        this.keys = keys;
    }

    /**
     * Getter for the fullscreen property.
     *
     * @return A boolean indicating whether fullscreen mode is enabled or not.
     */
    public boolean isFullscreen() {
        return fullscreen;
    }

    /**
     * Setter for the fullscreen property.
     *
     * @param fullscreen A boolean indicating whether to enable or disable fullscreen mode.
     */
    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    /**
     * Getter for the volume property.
     *
     * @return An integer representing the volume level.
     */
    public int getVolume() {
        return volume;
    }

    /**
     * Setter for the volume property.
     *
     * @param volume An integer representing the volume level.
     */
    public void setVolume(int volume) {
        this.volume = volume;
    }

    /**
     * Getter for the ups property.
     *
     * @return An integer representing the updates per second.
     */
    public int getUps() {
        return ups;
    }

    /**
     * Setter for the ups property.
     *
     * @param ups An integer representing the updates per second.
     */
    public void setUps(int ups) {
        this.ups = ups;
    }

    /**
     * Getter for the fps property.
     *
     * @return An integer representing the frames per second.
     */
    public int getFps() {
        return fps;
    }

    /**
     * Setter for the fps property.
     *
     * @param fps An integer representing the frames per second.
     */
    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * This method is used to convert the configuration data to a string representation.
     *
     * @return A string representation of the configuration data.
     */
    public String toString() {
        return "ConfigJson{" +
                "keys=" + keys +
                ", fullscreen=" + fullscreen +
                '}';
    }
}
