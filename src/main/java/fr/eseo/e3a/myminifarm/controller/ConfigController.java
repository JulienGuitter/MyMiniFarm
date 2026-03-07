package fr.eseo.e3a.myminifarm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eseo.e3a.myminifarm.model.json.ConfigJson;
import fr.eseo.e3a.myminifarm.model.type.KeyAction;
import fr.eseo.e3a.myminifarm.utils.KeyBindingManager;
import fr.eseo.e3a.myminifarm.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The ConfigController class is a singleton that manages the configuration of the game.
 * It handles loading and saving the configuration file, as well as managing key bindings.
 */
public class ConfigController {
    private static ConfigController instance;
    private ConfigJson config;
    private final KeyBindingManager keyBindingManager;

    /**
     * Private constructor for the ConfigController singleton.
     * Initializes the key binding manager and loads the configuration.
     */
    private ConfigController() {
        keyBindingManager = KeyBindingManager.getInstance();
        loadConfig();
    }

    /**
     * Returns the singleton instance of the ConfigController.
     *
     * @return the instance of ConfigController
     */
    public static ConfigController getInstance() {
        if (instance == null) {
            instance = new ConfigController();
        }
        return instance;
    }

    /**
     * Loads the configuration from the config.json file.
     * If the file does not exist or there is an error loading it, sets the default configuration.
     */
    public void loadConfig(){
        Path configPath = Utils.getAppDataDir().resolve("config.json");

        // Create the app data directory if it doesn't exist
        try {
            Files.createDirectories(Utils.getAppDataDir());
        } catch (Exception e) {
            System.out.println("Error creating app data directory: " + e.getMessage());
        }

        // Create the config file if it doesn't exist
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            config = objectMapper.readValue(configPath.toFile(), ConfigJson.class);
        } catch (Exception e) {
            System.out.println("Error loading config file: " + e.getMessage());
            setDefaultConfig();
        }

        // Load the key bindings
        keyBindingManager.set(config.getKeys());
    }

    /**
     * Set the default configuration for the game.
     * This includes setting the fullscreen mode, key bindings, and audio volume.
     * It also saves the default configuration to the config.json file.
     */
    private void setDefaultConfig(){
        this.config = new ConfigJson();
        // Graphics
        this.config.setFullscreen(false);

        // Keys
        keyBindingManager.bind(KeyAction.MOVE_UP.getAction(), "Z");
        keyBindingManager.bind(KeyAction.MOVE_DOWN.getAction(), "S");
        keyBindingManager.bind(KeyAction.MOVE_LEFT.getAction(), "Q");
        keyBindingManager.bind(KeyAction.MOVE_RIGHT.getAction(), "D");

        keyBindingManager.bind(KeyAction.SHOP.getAction(), "C");

        config.setKeys(keyBindingManager.getActionToKey());

        // Game
        this.config.setFps(60); // Default FPS
        this.config.setUps(200); // Default UPS

        // Audio
        this.config.setVolume(50); // Default volume level

        // Save the config file
        saveConfig();
    }

    /**
     * Save the current configuration to the config.json file.
     */
    public void saveConfig() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(Utils.getAppDataDir().resolve("config.json").toFile(), config);
        } catch (Exception e) {
            System.out.println("Error saving config file: " + e.getMessage());
        }
    }

    /**
     * Get the current configuration.
     *
     * @return the current configuration
     */
    public ConfigJson getConfig() {
        return config;
    }
}
