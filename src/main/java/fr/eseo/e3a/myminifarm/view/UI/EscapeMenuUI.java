package fr.eseo.e3a.myminifarm.view.UI;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.controller.GameController;
import fr.eseo.e3a.myminifarm.model.type.GameState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

/**
 * The EscapeMenuUI class is responsible for managing the escape menu interface in the game.
 * It handles user input and provides options to resume the game, access settings, or quit the game.
 */
public class EscapeMenuUI {
    @FXML
    StackPane escapeMenu;

    private GameUI gameUI;

    /**
     * This method is called when the EscapeMenuUI is loaded.
     * It sets the focus on the escape menu and initializes the key event handler.
     */
    @FXML
    private void initialize(){
        Platform.runLater(() -> {
            escapeMenu.requestFocus();
        });
    }

    /**
     * This method is used to set the GameUI instance for the escape menu.
     * It allows the escape menu to interact with the game UI.
     *
     * @param gameUI The GameUI instance to set.
     */
    public void setGameUI(GameUI gameUI) {
        this.gameUI = gameUI;
    }

    /**
     * This method is called when the resume button is clicked.
     * It removes the escape menu from the parent pane and deactivates the key event handler.
     */
    @FXML
    public void onResumeClicked(){
        StackPane settingsUI = (StackPane) escapeMenu.getParent().lookup("#settings");
        if (escapeMenu.getParent() instanceof Pane parent) {

            // remove all UI
            parent.getChildren().remove(escapeMenu);
            if(settingsUI != null){
                parent.getChildren().remove(settingsUI);
            }

            gameUI.initKey();

            GameController.getInstance().setGameState(GameState.PLAYING);
        }
    }

    /**
     * This method is called when the settings button is clicked.
     * It loads the settings UI and adds it to the parent pane of the escape menu.
     */
    @FXML
    private void onSettingsClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(MyMiniFarm.class.getResource("UI/settings.fxml"));
            Parent ui = loader.load();
            ((Pane) escapeMenu.getParent()).getChildren().add(ui);
        }catch (IOException e){
            System.out.println("Error loading settings from game: " + e.getMessage());
        }
    }

    /**
     * This method is called when the quit button is clicked.
     * It triggers the GameController to go back to the main menu.
     */
    @FXML
    private void onQuitClicked(){
        GameController.getInstance().stopGame();
    }
}
