package fr.eseo.e3a.myminifarm.model.type;

/**
 * The KeyAction enum represents the different actions that can be performed in the game using keyboard input.
 * Each action corresponds to a specific key binding.
 */
public enum KeyAction {
    MOVE_UP("Move Up"),
    MOVE_DOWN("Move Down"),
    MOVE_LEFT("Move Left"),
    MOVE_RIGHT("Move Right"),

    SHOP("Shop Menu");

    private final String action;

    /**
     * Constructor for the KeyAction enum.
     *
     * @param action The string representation of the action.
     */
    KeyAction(String action) {
        this.action = action;
    }

    /**
     * Get the string representation of the action.
     *
     * @return The string representation of the action.
     */
    public String getAction() {
        return action;
    }

    /**
     * Get the KeyAction enum constant from a string representation of the action.
     *
     * @param action The string representation of the action.
     * @return The corresponding KeyAction enum constant.
     * @throws IllegalArgumentException if no constant with the specified action is found.
     */
    public static KeyAction fromString(String action) {
        for (KeyAction keyAction : KeyAction.values()) {
            if (keyAction.getAction().equalsIgnoreCase(action)) {
                return keyAction;
            }
        }
        throw new IllegalArgumentException("No constant with action " + action + " found");
    }
}
