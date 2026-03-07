package fr.eseo.e3a.myminifarm.view.UI;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.controller.GameController;
import fr.eseo.e3a.myminifarm.controller.UIController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * The MenuUI class is responsible for managing the main menu interface of the game.
 * It handles user interactions such as starting the game, quitting the game, and opening settings.
 */
public class MenuUI {
    @FXML private StackPane menu;

    /**
     * This method is called when the MenuUI is loaded.
     * It initializes the menu and sets the focus on it.
     */
    @FXML
    private void onPlayClicked(){
        GameController.getInstance().startGame();
    }

    /**
     * This method is called when the quit button is clicked.
     * It exits the application.
     */
    @FXML
    private void onQuitClicked(){
        System.exit(0);
    }

    /**
     * This method is called when the settings button is clicked.
     * It loads the settings UI and adds it to the menu.
     */
    @FXML
    private void onSettingsClicked(){
        try {
            FXMLLoader loader = new FXMLLoader(MyMiniFarm.class.getResource("UI/settings.fxml"));
            Parent ui = loader.load();
            menu.getChildren().add(ui);
        }catch (IOException e){
            System.out.println("Error loading settings from menu: " + e.getMessage());
        }
    }
}
