package fr.eseo.e3a.myminifarm.view.UI;

import fr.eseo.e3a.myminifarm.controller.ConfigController;
import fr.eseo.e3a.myminifarm.model.type.KeyAction;
import fr.eseo.e3a.myminifarm.utils.AudioManager;
import fr.eseo.e3a.myminifarm.utils.KeyBindingManager;
import fr.eseo.e3a.myminifarm.controller.UIController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Objects;

/**
 * The SettingsUI class is responsible for managing the settings interface of the game.
 * It handles user input for changing graphics, audio, and key bindings.
 */
public class SettingsUI {

    @FXML private ScrollPane graphicsContainer;
    @FXML private ScrollPane keysScrol;
    @FXML private ScrollPane audioContainer;
    @FXML private GridPane keysContainer;
    @FXML private StackPane settings;

    // Settings
    @FXML private CheckBox fullscreenCheckBox;

    // Audio
    @FXML private Slider volumeSlider;

    private ConfigController configController = ConfigController.getInstance();
    private KeyBindingManager keyBindingManager = KeyBindingManager.getInstance();

    private Button lastClickedButton = null;
    private int row = 0;

    /**
     * This method is called when the SettingsUI is loaded.
     * It initializes the UI components and binds the scroll pane size to the graphics container size.
     * It also loads the keys from the config file and sets up the audio settings.
     */
    @FXML
    private void initialize() {


        addKeyLabelLigne("Movement :");
        addKeyLine(KeyAction.MOVE_UP);
        addKeyLine(KeyAction.MOVE_DOWN);
        addKeyLine(KeyAction.MOVE_LEFT);
        addKeyLine(KeyAction.MOVE_RIGHT);

        addKeyLabelLigne("Inventory :");
        addKeyLine(KeyAction.SHOP);


        // Graphics
        fullscreenCheckBox.setSelected(configController.getConfig().isFullscreen());

        //Audio
        volumeSlider.setValue(configController.getConfig().getVolume());

        settings.setOnMouseClicked(this::onMouseClicked);

        onKeyPressed();
    }

    private void addKeyLine(KeyAction keyAction){
        String action = keyAction.getAction();
        String key = configController.getConfig().getKeys().get(action);

        if(Objects.equals(action, "   ")){
            return;
        }

        Label label = new Label(action);
        label.getStyleClass().addAll("pixelLabel", "fixLabelCenter");

        Button button = new Button(key);
        button.setId(action.replace(" ", "_"));
        button.getStyleClass().addAll("button", "btn_wood");
        button.setUserData(action);
        button.setFocusTraversable(false);
        button.setOnAction(this::onKeyClicked);

        HBox hBox = new HBox(label, button);
        hBox.setSpacing(20);
        hBox.setAlignment(javafx.geometry.Pos.CENTER);

        keysContainer.add(label, 0, row);  // colonne 0
        keysContainer.add(button, 1, row); // colonne 1
        row++;
    }

    private void addKeyLabelLigne(String text){
        Label empty = new Label(" ");
        Label empty2 = new Label(" ");
        Label label = new Label(text);
        label.getStyleClass().addAll("pixelLabel", "fixLabelCenter");

        if(row != 0) {
            keysContainer.add(empty, 0, row);
            row++;
            keysContainer.add(empty2, 0, row);
            row++;
        }
        keysContainer.add(label, 0, row);
        row++;
    }

    private void onMouseClicked(MouseEvent event){
        if(lastClickedButton != null){
            String action = lastClickedButton.getUserData().toString();
            String oldKey = keyBindingManager.getKey(action);
            Button oldButton = (Button) keysContainer.lookup("#" + action.replace(" ", "_"));
            if(oldKey != null && !"   ".equals(oldKey)){
                oldButton.setText(oldKey);
            }else{
                oldButton.setText("   ");
            }
        }
        settings.requestFocus();
    }

    private void onKeyPressed(){
        settings.setFocusTraversable(true);
        Platform.runLater(() -> settings.requestFocus());
        settings.setOnKeyPressed(e -> {
            if(lastClickedButton == null){
                return;
            }

            String action = lastClickedButton.getUserData().toString();
            String usedAction = keyBindingManager.getAction(e.getCode().toString());

            if(usedAction != null){
                Button usedButton = (Button) keysContainer.lookup("#" + usedAction.replace(" ", "_"));
                if(usedButton != null){
                    usedButton.setText("   ");
                }
            }

            keyBindingManager.bind(action, e.getCode().toString());

            lastClickedButton.setText(e.getCode().toString());
            lastClickedButton = null;
        });
    }

    /**
     * This method is called when the fullscreen checkbox is changed.
     * It updates the config and UI accordingly.
     *
     * @param event the Event that triggered the change
     */
    @FXML
    private void onFullscreenChanged(Event event) {
        configController.getConfig().setFullscreen(fullscreenCheckBox.isSelected());
        UIController.getInstance().updateFullscreen();
    }

    /**
     * This method is called when the graphics button is clicked.
     * It shows the graphics settings and hides the keys and audio settings.
     */
    @FXML
    private void onGraphicsClicked() {
        graphicsContainer.setVisible(true);
        keysScrol.setVisible(false);
        audioContainer.setVisible(false);
    }

    /**
     * This method is called when the keys button is clicked.
     * It shows the keys settings and hides the graphics and audio settings.
     */
    @FXML
    private void onKeysClicked() {
        keysScrol.setVisible(true);
        graphicsContainer.setVisible(false);
        audioContainer.setVisible(false);
    }

    /**
     * This method is called when the audio button is clicked.
     * It shows the audio settings and hides the graphics and keys settings.
     */
    @FXML
    private void onAudioClicked(){
        audioContainer.setVisible(true);
        keysScrol.setVisible(false);
        graphicsContainer.setVisible(false);
    }

    /**
     * This method is called when the back button is clicked.
     * It updates the config and saves it, then removes the settings UI from the parent.
     */
    @FXML
    private void onBackClicked() {
        // Update all the field to the current config
        configController.getConfig().setFullscreen(fullscreenCheckBox.isSelected());

        configController.getConfig().setKeys(keyBindingManager.getActionToKey());
        configController.saveConfig();


        if (settings.getParent() instanceof Pane parent) {
            parent.getChildren().remove(settings);
        }
    }

    /**
     * This method is called when a key button is clicked.
     * It sets up the key binding for the clicked button and updates the UI accordingly.
     *
     * @param event the ActionEvent that triggered the click
     */
    @FXML
    private void onKeyClicked(ActionEvent event){
        // Remove the old key binding
        if(lastClickedButton != null){
            String action = lastClickedButton.getUserData().toString();
            String oldKey = keyBindingManager.getKey(action);
            if(oldKey != null && !"   ".equals(oldKey)){
                Button oldButton = (Button) keysContainer.lookup("#" + action.replace(" ", "_"));
                oldButton.setText(oldKey);
                oldButton.setOnKeyPressed(null);
            }
        }

        lastClickedButton = (Button) event.getSource();
        lastClickedButton.setText("?");
    }

    /**
     * This method is called when the main volume slider is changed.
     * It updates the config and audio manager accordingly.
     */
    @FXML
    private void onMainVolumeChanged(){
        int volume = (int) volumeSlider.getValue();
        configController.getConfig().setVolume(volume);
        AudioManager.getInstance().setVolume(volume);
    }
}
