package fr.eseo.e3a.myminifarm.model.type;

/**
 * The GameState enum represents the different states of the game.
 * It is used to manage in a state machine the different phases of the game.
 */
public enum GameState {
    MAIN_MENU,
    START_GAME,
    PLAYING,
    MACHINE_MENU,
    PAUSE,
    SETTINGS,
    QUIT
}
