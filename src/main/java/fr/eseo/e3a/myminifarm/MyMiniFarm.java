package fr.eseo.e3a.myminifarm;

import fr.eseo.e3a.myminifarm.controller.GameController;
import fr.eseo.e3a.myminifarm.controller.UIController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class MyMiniFarm extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameController gameController = GameController.getInstance();
        UIController uiController = UIController.getInstance(stage);

        uiController.setGameController(gameController);
        gameController.setUiController(uiController);
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
