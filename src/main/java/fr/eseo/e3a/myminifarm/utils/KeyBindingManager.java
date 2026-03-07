package fr.eseo.e3a.myminifarm.utils;


import java.util.HashMap;
import java.util.Set;

/**
 * The KeyBindingManager class is responsible for managing key bindings in the game.
 * It allows you to bind actions to keys, and retrieve one from the other.
 * <br> <br>
 * This class is implemented as a singleton to ensure that there is only one instance
 */
public class KeyBindingManager {
    private static KeyBindingManager instance;

    private final HashMap<String, String> actionToKey = new HashMap<>();
    private final HashMap<String, String> keyToAction = new HashMap<>();

    /**
     * Private constructor for the KeyBindingManager singleton.
     * It initializes the default key bindings.
     */
    private KeyBindingManager() {
    }

    /**
     * Get the singleton instance of the KeyBindingManager.
     *
     * @return the instance of KeyBindingManager
     */
    public static KeyBindingManager getInstance() {
        if (instance == null) {
            instance = new KeyBindingManager();
        }
        return instance;
    }

    /**
     * Bind an action to a key.
     * If the action is already bound to a key, the old key will be unbound.
     * If the key is already bound to an action, the old action will be unbound.
     *
     * @param action The action to bind.
     * @param key The key to bind the action to.
     */
    public void bind(String action, String key) {
        if (actionToKey.containsKey(action)) {
            String oldKey = actionToKey.get(action);
            keyToAction.put(oldKey, "   ");
        }
        if (keyToAction.containsKey(key)) {
            String oldAction = keyToAction.get(key);
            actionToKey.put(oldAction, "   ");
        }
        actionToKey.put(action, key);
        keyToAction.put(key, action);
    }

    /**
     * Used to get the key bound to an action.
     *
     * @param action The action of the wanted key.
     */
    public String getKey(String action) {
        return actionToKey.get(action);
    }

    /**
     * Used to get the action bound to a key.
     *
     * @param key The key of the wanted action.
     */
    public String getAction(String key) {
        return keyToAction.get(key);
    }

    /**
     * Used to check if a key is bound to an action.
     *
     * @param key The key to check.
     * @return true if the key is bound to an action, false otherwise.
     */
    public boolean isKeyBound(String key) {
        return keyToAction.containsKey(key);
    }

    /**
     * This method is used to get all the actions that are bound to keys.
     *
     * @return A set of all actions that are bound to keys.
     */
    public Set<String> getAllActions() {
        return actionToKey.keySet();
    }

    /**
     * This method is used to get all the keys that are bound to actions.
     *
     * @return A set of all keys that are bound to actions.
     */
    public HashMap<String, String> getActionToKey() {
        return actionToKey;
    }

    /**
     * This method is used to load the key bindings from a HashMap.
     *
     * @param data The HashMap containing the key bindings.
     */
    public void set(HashMap<String, String> data) {
        this.actionToKey.clear();
        this.actionToKey.putAll(data);
        keyToAction.clear();
        for (String action : data.keySet()) {
            keyToAction.put(data.get(action), action);
        }
    }
}
