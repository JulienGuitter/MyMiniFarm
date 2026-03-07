package fr.eseo.e3a.myminifarm.view.UI;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.controller.ConfigController;
import fr.eseo.e3a.myminifarm.controller.GameController;
import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.type.GameState;
import fr.eseo.e3a.myminifarm.model.type.KeyAction;
import fr.eseo.e3a.myminifarm.utils.KeyBindingManager;
import fr.eseo.e3a.myminifarm.view.UI.modules.InventoryBarUI;
import fr.eseo.e3a.myminifarm.view.UI.modules.UserInfoUI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * The GameUI class is responsible for managing the interface of the game.
 * It handles user input.
 */
public class GameUI {
    @FXML private BorderPane gameUI;

    @FXML private InventoryBarUI inventoryBarController;

    @FXML private UserInfoUI userInfoController;

    private Set<KeyAction> keysPressed;

    private GameLoop gameLoop;
    private UIController uiController;
    private ConfigController configController;
    private KeyBindingManager keyBindingManager;
    private GameController gameController;

    private Parent shopUI;

    /**
     * When the GameUI is loaded, this method is called to initialize the controllers.
     * It sets the focus on the gameUI and initializes the key bindings.
     */
    @FXML
    private void initialize() {
        keysPressed = EnumSet.noneOf(KeyAction.class);

        gameLoop = GameLoop.getInstance();
        uiController = UIController.getInstance();
        configController = ConfigController.getInstance();
        keyBindingManager = KeyBindingManager.getInstance();
        gameController = GameController.getInstance();

        initKey();

        updateGameUIContent();

        inventoryBarController.setRoot(gameUI);

        Platform.runLater(() -> {
            initShopUI();
        });
    }

    private void initShopUI(){
        try {
            FXMLLoader loader = new FXMLLoader(MyMiniFarm.class.getResource("UI/shopMenu.fxml"));
            shopUI = loader.load();
            shopUI.setId("shopUI");
            ((Pane) gameUI.getParent()).getChildren().add(shopUI);
            shopUI.setVisible(false);
            shopUI.setDisable(true);
        } catch (IOException e) {
            System.out.println("Error loading shop UI: " + e.getMessage());
        }
    }

    /**
     * This method is used to set up the KeyEvent handler for the gameUI.
     * It ensures that the gameUI has the keyboard focus and sets the key event handler.
     */
    public void initKey(){
        Platform.runLater(() -> {
            gameUI.requestFocus();
            uiController.initKey();
            gameUI.setOnMouseClicked(event -> {
                uiController.onMouseClicked(event);
            });
        });
    }

    /**
     * This method is used to delete the KeyEvent handler for the gameUI.
     * It is called when the escape menu is loaded to prevent key events from being processed.
     */
    public void removeKey(){
        uiController.disableKey();
        gameUI.setOnMouseClicked(null);
    }

    /**
     * This method is used to handle key events.
     * If the escape key is pressed, it loads the escape menu.
     * If the F11 key is pressed, it toggles fullscreen mode.
     * Otherwise, it checks if the key is bound to an action and sends the action to the game controller.
     *
     * @param event the KeyEvent that was triggered
     */
    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()){
            case ESCAPE :
                if(!uiController.closeOpenInterface()) {
                    loadEscapeMenu();
                    gameController.setGameState(GameState.PAUSE);
                }
                break;
            case F3:
                uiController.toggleDebug();
                break;
            case F9:
                uiController.getMapView().setUpdateDebug(true);
                uiController.toggleCollider();
                break;
            case F11 :
                configController.getConfig().setFullscreen(!configController.getConfig().isFullscreen());
                uiController.updateFullscreen();
                configController.saveConfig();
            default:
                String action = keyBindingManager.getAction(event.getCode().toString());
                if (action != null) {
                    if(keysPressed.add(KeyAction.fromString(action))) {
                        if(Objects.equals(action, KeyAction.SHOP.getAction())) {
                            if (uiController.isMachineInterfaceOpen()){
                                uiController.closeMachineInterfaceUI();
                            }
                            if (uiController.isContainerInterfaceOpen()){
                                uiController.closeContainerInterfaceUI();
                            }

                            toggleShopUI();
                        }else {
                            gameLoop.onKeyPress(KeyAction.fromString(action));
                        }
                    }
                } else {
//                    System.out.println("No action found for key: " + event.getCode());
                }
                break;
        }
    }

    public void toggleShopUI(){
        if(shopUI != null){
            shopUI.setVisible(!shopUI.isVisible());
            shopUI.setDisable(!shopUI.isDisable());
        }else{
            initShopUI();
        }
    }

    public boolean isShopOpen(){
        return shopUI != null && shopUI.isVisible();
    }

    /**
     * This method is used to handle key release events.
     * It removes the key from the keysPressed set and sends the key release event to the game loop.
     *
     * @param event the KeyEvent that was triggered
     */
    public void onKeyReleased(KeyEvent event) {
        String action = keyBindingManager.getAction(event.getCode().toString());
        if (action != null) {
            if(keysPressed.remove(KeyAction.fromString(action))){
                gameLoop.onKeyRelease(KeyAction.fromString(action));
            }
        }
    }

    /**
     * This method is used to load the escape menu UI.
     * It uses FXMLLoader to load the escape menu FXML file and sets the gameUI as its parent.
     * It also removes the key event handler to prevent key events from being processed while the escape menu is open.
     */
    private void loadEscapeMenu(){
        try {
            FXMLLoader loader = new FXMLLoader(MyMiniFarm.class.getResource("UI/escapeMenu.fxml"));
            Parent ui = loader.load();
            loader.<EscapeMenuUI>getController().setGameUI(this);
            ((Pane) gameUI.getParent()).getChildren().add(ui);
            removeKey();
        }catch (IOException e){
            System.out.println("Error loading escape menu: " + e.getMessage());
        }
    }

    public void updateGameUIContent(){
        userInfoController.setMoneyLabel(gameController.getPlayer().getMoney());
    }


    public Pane getGameUI() {
        return gameUI;
    }
}
