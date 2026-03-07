package fr.eseo.e3a.myminifarm.controller;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.model.Map;
import fr.eseo.e3a.myminifarm.model.Player;
import fr.eseo.e3a.myminifarm.model.type.GameState;
import fr.eseo.e3a.myminifarm.model.type.KeyAction;
import fr.eseo.e3a.myminifarm.utils.Point2D;
import javafx.concurrent.Task;

import java.util.Objects;

/**
 * The GameController class is a singleton that manages the game state and interactions.
 * It handles the loading of the game map, starting and stopping the game, and processing user actions.
 * It also manages all the game logic and interactions with the UI.
 */
public final class GameController {
    public static final int INVBAR_SIZE = 9;

    private static GameController instance;
    private ConfigController configController;
    private UIController uiController;
    private GameState gameState;
    private GameLoop gameLoop;
    private Thread gameThread;

    private boolean debugMode = false;

    /**
     * Private constructor for the GameController singleton.
     * Initializes the game state and loads the configuration.
     */
    private GameController() {
        this.gameState = GameState.MAIN_MENU;
        this.configController = ConfigController.getInstance();

        // Load the config file
        configController.loadConfig();
    }

    /**
     * Returns the singleton instance of the GameController.
     *
     * @return the instance of GameController
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Sets the UI controller for the game.
     *
     * @param uiController the UI controller to set
     */
    public void setUiController(UIController uiController) {
        this.uiController = uiController;
    }

    /**
     * Starts the game by loading the map and initializing the map UI.
     * This method also starts the game loop in a new thread.
     */
    public void startGame() {
        this.gameState = GameState.START_GAME;

        uiController.showLoading();

        Task<Void> loadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Initialize the game loop
                gameLoop = GameLoop.getInstance();
                gameState = gameLoop.init();

                return null;
            }

            @Override
            protected  void succeeded(){
                gameThread = new Thread(() -> {
                    // Start the game loop
                    gameLoop.run();
                });
                gameThread.setDaemon(true);
                gameThread.start();
                // Show the map
                uiController.showGame();
            }
        };

        Thread t = new Thread(loadTask);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Stops the game and returns to the main menu.
     */
    public void stopGame() {
        this.gameState = GameState.MAIN_MENU;
        System.out.println("Stopping the game...");
        uiController.showMainMenu();
    }


    /**
     * Returns whether debug mode is enabled.
     *
     * @return true if debug mode is enabled, false otherwise
     */
    public boolean isDebugMode() {
        return debugMode;
    }

    /**
     * Toggles the debug mode on or off.
     */
    public void toggleDebug() {
        this.debugMode = !this.debugMode;
        uiController.updateDebugUI();
    }

    /**
     * Handles mouse scroll events to change the inventory bar index.
     *
     * @param deltaY the amount of scroll (positive or negative)
     */
    public void onMouseScroll(int deltaY) {
        if (gameState == GameState.PLAYING) {
            gameLoop.getPlayer().changeInvBarIndex(-deltaY);
        }
    }

    /**
     * Handles mouse button click events.
     *
     * @param pos Point2D of the clicked cell
     * @param isLeftClick true if the left mouse button was clicked, false otherwise
     */
    public void onMouseClick(Point2D pos, boolean isLeftClick) {
        if(isLeftClick) {
            gameLoop.onLeftClick(pos);
        } else {
            gameLoop.onRigthClick(pos);
        }
    }

    /**
     * Returns the current game state.
     *
     * @return the current game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the game state.
     *
     * @param gameState the game state to set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the player object.
     *
     * @return the player object
     */
    public Player getPlayer() {
        return gameLoop.getPlayer();
    }
}
