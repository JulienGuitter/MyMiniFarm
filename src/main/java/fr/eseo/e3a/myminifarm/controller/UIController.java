package fr.eseo.e3a.myminifarm.controller;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.model.Inventory;
import fr.eseo.e3a.myminifarm.model.Map;
import fr.eseo.e3a.myminifarm.model.Player;
import fr.eseo.e3a.myminifarm.model.farmElement.Container;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import fr.eseo.e3a.myminifarm.model.farmElement.Machine;
import fr.eseo.e3a.myminifarm.model.type.GameState;
import fr.eseo.e3a.myminifarm.utils.AudioManager;
import fr.eseo.e3a.myminifarm.utils.Point2D;
import fr.eseo.e3a.myminifarm.utils.Vector2;
import fr.eseo.e3a.myminifarm.view.*;
import fr.eseo.e3a.myminifarm.view.UI.ContainerInterfaceUI;
import fr.eseo.e3a.myminifarm.view.UI.GameUI;
import fr.eseo.e3a.myminifarm.view.UI.MachineInterfaceUI;
import fr.eseo.e3a.myminifarm.view.utils.DragContext;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The UIController class is responsible for managing the user interface of the game.
 * It handles the loading and displaying of different UI screens, such as the main menu and map view.
 * <br>
 * - It is a singleton class, meaning there can only be one instance of it at a time.
 */
public class UIController {
    public static final int IMAGE_RATIO = 4;
    public static final int IMAGE_SIZE = 16;
    public static final int ANIMATION_SPEED = 200;

    public static final DataFormat CONTAINER_FORMAT = new DataFormat("application/x-javafx-container");
    public static final DataFormat SLOT_FORMAT = new DataFormat("application/x-javafx-slot-id");

    private static UIController instance;
    private GameController gameController;
    private ConfigController configController;
    private Stage stage;
    private Scene scene;
    private AudioManager audioManager;
    private double windowWidth = 848;
    private double windowHeight = 600;

    private Pane debugPane;

    private Pane gamePane;
    private StackPane gameSP;

    private Pane machinePane;
    private Pane containerPane;
    private MachineInterfaceUI machineInterfaceUI;
    private ContainerInterfaceUI containerInterfaceUI;

    private MapView mapView;
    private PlayerView playerView;
    private ItemView itemView;
    private ElementView elementView;

    private GameUI gameUI;

    private boolean hasMachineInterfaceChaneged = false;
    private boolean hasContainerInterfaceChanged = false;

    // DEBUG
    private boolean showCollider = false;

    /**
     * Constructor for the UIController class.
     * Initializes all the necessary components for the UI and sets up event listeners.
     *
     * @param stage The primary stage for the application.
     */
    private UIController(Stage stage) {
        configController = ConfigController.getInstance();
        audioManager = AudioManager.getInstance();

        scene = new Scene(new StackPane(), windowWidth, windowHeight);
        this.stage = stage;

        this.stage.setTitle("My Mini Farm");
        this.stage.getIcons().add(new ImageView(MyMiniFarm.class.getResource("textures/UI/Icon.png").toString()).getImage());

        // Config the fullscreen mode
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        this.stage.setFullScreenExitHint("");

        this.stage.setMinWidth(windowWidth);
        this.stage.setMinHeight(windowHeight);

        this.stage.setScene(scene);
        this.stage.show();

        // Listener for the window resize
        this.stage.widthProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
            windowWidth = newValue.doubleValue();
        });
        this.stage.heightProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
            windowHeight = newValue.doubleValue();
        });

        // Listener for the mouse scroll
        this.stage.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if(gameController.getGameState() != GameState.PLAYING) return;
            if(event.getDeltaY() == 0) return;
            int sens = event.getDeltaY() > 0 ? 1 : -1;
            gameController.onMouseScroll(sens);
        });

        // Listener for the key press
//        initKey();

        itemView = new ItemView();
        elementView = new ElementView();

        // Load the main menu
        showMainMenu();

        // Center the window on the screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);

        // Set the default volume
        audioManager.setVolume(configController.getConfig().getVolume());
    }

    private final EventHandler<KeyEvent> keyPressedFilter = event -> {
        if (gameController.getGameState() != GameState.PLAYING) return;
        gameUI.onKeyPressed(event);
    };

    private final EventHandler<KeyEvent> keyReleasedFilter = event -> {
        if (gameController.getGameState() != GameState.PLAYING) return;
        gameUI.onKeyReleased(event);
    };

    public void initKey() {
        this.stage.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter);
        this.stage.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedFilter);
    }

    public void disableKey() {
        this.stage.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedFilter);
        this.stage.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedFilter);
    }

    public void onMouseClicked(MouseEvent event) {
        if(gameController.getGameState() != GameState.PLAYING) return;
        Point2D clickedPos = new Point2D((int)(Math.abs(mapView.getMapPos().x()) + event.getX()) / (UIController.IMAGE_SIZE * UIController.IMAGE_RATIO),
                (int)(Math.abs(mapView.getMapPos().y()) + event.getY())  / (UIController.IMAGE_SIZE * UIController.IMAGE_RATIO));

        gameController.onMouseClick(clickedPos, event.getButton() == javafx.scene.input.MouseButton.PRIMARY);
    }

    /**
     * Returns the singleton instance of the UIController, creating it if necessary.
     *
     * @param stage The primary stage for the application.
     * @return The singleton instance of the UIController.
     */
    public static UIController getInstance(Stage stage) {
        if (instance == null) {
            instance = new UIController(stage);
        }
        return instance;
    }

    /**
     * Returns the singleton instance of the UIController.
     *
     * @return The singleton instance of the UIController.
     */
    public static UIController getInstance() {
        return instance;
    }

    /**
     * Sets the GameController instance for the UIController.
     * Used to link the UIController with the GameController.
     *
     * @param gameController The GameController instance to set.
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Updates the fullscreen mode of the stage based on the user's configuration.
     */
    public void updateFullscreen() {
        this.stage.setFullScreen(configController.getConfig().isFullscreen());
    }

    /**
     * Initializes the map view with the given map.
     *
     * @param map The map to initialize the view with.
     */
    public void initMap(Map map) {
        // Load the sprite sheet for the map
        SpriteSheet spriteSheet = new SpriteSheet(MyMiniFarm.class.getResourceAsStream("textures/terrain/outdoor.png"), 20, 20);

        // Create the map view
        mapView = new MapView(map, spriteSheet);
    }

    /**
     * Initializes the player view with the player's sprites.
     *
     * @param player instance of the player model
     * @param spritePath the path to the player sprite sheet
     */
    public void initPlayer(Player player, String spritePath){
        playerView = new PlayerView(player, spritePath);
    }

    /**
     * Shows the main game content, including the map and player views.
     */
    public void showGame(){
        gameSP = new StackPane();
        gamePane = new Pane();

        gameSP.getStyleClass().add("game_background");
        scene.getStylesheets().add(Objects.requireNonNull(MyMiniFarm.class.getResource("UI/css/style.css")).toExternalForm());

        StackPane mapSP = mapView.getStackPane();
        StackPane playerSP = playerView.getStackPane();

        gamePane.getChildren().add(mapSP);
        gamePane.getChildren().add(playerSP);
        try{
            FXMLLoader ui = new FXMLLoader(MyMiniFarm.class.getResource("UI/game.fxml"));
            Parent content = ui.load();
            gameUI = ui.getController();
            gameSP.getChildren().add(gamePane);
            gameSP.getChildren().add(content);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        initMachinePane();
        initContainerPane();

        scene.setRoot(gameSP);
    }

    /**
     * This method is used to show the loading screen on the stage.
     * It sets the root of the scene to the loading screen and updates the stage.
     */
    public void showLoading() {
        FXMLLoader fxmlLoader = new FXMLLoader(MyMiniFarm.class.getResource("UI/loading.fxml"));

        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to show the main menu on the stage.
     * It sets the root of the scene to the main menu and updates the stage.
     * It also plays the home music.
     */
    public void showMainMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(MyMiniFarm.class.getResource("UI/main_menu.fxml"));

        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        updateFullscreen();

        // Play the home music
        audioManager.playHomeMusic();
    }

    /**
     * Toggles the debug mode of the game.
     */
    public void toggleDebug(){
        gameController.toggleDebug();
    }

    /**
     * Toggles the display of colliders for debugging purposes.
     */
    public void toggleCollider(){
        showCollider = !showCollider;
    }

    /**
     * Updates the debug UI, showing or hiding the debug pane depending on debug mode.
     */
    public void updateDebugUI() {
        if(gameController.isDebugMode()){
            if(debugPane == null){
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(MyMiniFarm.class.getResource("UI/debug.fxml"));
                    debugPane = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            gameSP.getChildren().add(debugPane);
        }else{
            if(debugPane != null){
                gameSP.getChildren().remove(debugPane);
            }
        }
    }

    /**
     * Updates the debug content with the current FPS and UPS values.
     *
     * @param fps The current frames per second
     * @param ups The current updates per second
     */
    public void updateDebugContent(int fps, int ups){
        if(debugPane != null){
            try {
                Label fpsLabel = (Label) debugPane.lookup("#fpsLabel");
                fpsLabel.setText(String.valueOf(fps));
                Label upsLabel = (Label) debugPane.lookup("#upsLabel");
                upsLabel.setText(String.valueOf(ups));
                Label xyLabel = (Label) debugPane.lookup("#xyLabel");
                xyLabel.setText(String.format("%.3f / 1.00000 / %.3f", gameController.getPlayer().getPos().x(), gameController.getPlayer().getPos().y()));
            }catch (NullPointerException e){
                System.out.println("Debug content not found");
            }
        }
    }

    /**
     * Updates the map and player views.
     */
    public void update(){
        mapView.update();
        playerView.update();
    }

    private void initMachinePane(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyMiniFarm.class.getResource("UI/machineInterface.fxml"));
            Parent content = fxmlLoader.load();
            machineInterfaceUI = fxmlLoader.getController();
            machineInterfaceUI.setGameUI(gameUI.getGameUI());
            machinePane = (Pane) content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Opens the machine interface UI for the specified machine.
     *
     * @param machine The machine to display in the interface
     */
    public void openMachineInterfaceUI(Machine machine) {
        if(machinePane != null){
            machineInterfaceUI.setMachine(machine);
            gameSP.getChildren().add(machinePane);
        }
    }

    /**
     * Returns true if the machine interface is currently open.
     *
     * @return true if open, false otherwise
     */
    public boolean isMachineInterfaceOpen() {
        return gameSP.getChildren().contains(machinePane);
    }

    /**
     * Closes the machine interface UI if it is open.
     */
    public void closeMachineInterfaceUI() {
        if(machinePane != null){
            gameSP.getChildren().remove(machinePane);
        }
    }

    private void initContainerPane(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyMiniFarm.class.getResource("UI/containerInterface.fxml"));
            Parent content = fxmlLoader.load();
            containerInterfaceUI = fxmlLoader.getController();
            containerInterfaceUI.setGameUI(gameUI.getGameUI());
            containerPane = (Pane) content;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens the container interface UI for the specified container.
     *
     * @param container The container to display in the interface
     */
    public void openContainerInterfaceUI(Container container){
        if(containerPane != null){
            containerInterfaceUI.setContainer(container);
            gameSP.getChildren().add(containerPane);
        }
    }

    /**
     * Returns true if the container interface is currently open.
     *
     * @return true if open, false otherwise
     */
    public boolean isContainerInterfaceOpen() {
        return gameSP.getChildren().contains(containerPane);
    }

    /**
     * Closes the container interface UI if it is open.
     */
    public void closeContainerInterfaceUI() {
        if(containerPane != null){
            containerInterfaceUI.getContainer().close();
            gameSP.getChildren().remove(containerPane);
        }
    }


    /**
     * Closes any open interface (machine, container, or shop) and returns true if one was closed.
     *
     * @return true if an interface was closed, false otherwise
     */
    public boolean closeOpenInterface(){
        if(isMachineInterfaceOpen()){
            closeMachineInterfaceUI();
            return true;
        }
        if(isContainerInterfaceOpen()){
            closeContainerInterfaceUI();
            return true;
        }
        if(gameUI.isShopOpen()){
            gameUI.toggleShopUI();
            return true;
        }
        return false;
    }




    /**
     * Handles the start of a drag-and-drop operation for inventory items.
     *
     * @param root The root pane
     * @param inventoryContent The inventory content pane
     * @param cell The cell being dragged
     * @param container The container stack pane
     * @param inventory The inventory involved
     * @param containerType The type of container
     * @param event The mouse event
     */
    public void onDragStart(Pane root, Pane inventoryContent, Pane cell, StackPane container, Inventory inventory, FarmEnums.ItemType containerType, MouseEvent event) {
        Dragboard db = root.startDragAndDrop(TransferMode.MOVE);

        int startIndex = inventoryContent.getChildren().indexOf(cell);

        ClipboardContent content = new ClipboardContent();
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(new Color(0, 0, 0, 0.3));
        WritableImage image = container.snapshot(params, null);

        content.putImage(image);
        DragContext.set(inventory, containerType, startIndex);
        content.putString("item");

        db.setContent(content);

        event.consume();
    }

    /**
     * Handles drag-over events for inventory cells.
     *
     * @param cell The cell being dragged over
     * @param event The drag event
     */
    public void onDragOver(Pane cell, DragEvent event) {
        if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    /**
     * Handles the end of a drag-and-drop operation for inventory items.
     *
     * @param inventoryContent The inventory content pane
     * @param cell The cell being dropped on
     * @param inventory The inventory involved
     * @param containerType The type of container
     * @param machine The machine involved (if any)
     * @param event The drag event
     */
    public void onDragEnd(Pane inventoryContent, StackPane cell, Inventory inventory, FarmEnums.ItemType containerType, FarmEnums.Machines machine, DragEvent event){
//        Dragboard db = event.getDragboard();
        boolean success = false;

        if (DragContext.sourceContainer != null) {
            int startIndex = DragContext.sourceIndex;
            int dropIndex = inventoryContent.getChildren().indexOf(cell);

            String userData = (String) cell.getUserData();
            if(userData != null && userData.equals("noDrop")){
                DragContext.clear();
                return;
            }

            Inventory sourceInventory = (Inventory) DragContext.sourceContainer;
            Inventory targetInventory = inventory;

            Item it = sourceInventory.getItem(startIndex);
            Item it2 = targetInventory.getItem(dropIndex);


            FarmEnums.ReceipeType receipeType = FarmEnums.ReceipeType.getByName(it.getName());
            if(machine != null) {
                if(receipeType == null || receipeType.getMachine() != machine){
                    DragContext.clear();
                    return;
                }
            }

            if(targetInventory.getItem(dropIndex) == null) {
                targetInventory.add(it, dropIndex);
                sourceInventory.remove(startIndex);
            }else if(Objects.equals(it.getName(), it2.getName())){
                targetInventory.addQuantity(dropIndex, it.getQuantity());
                sourceInventory.remove(startIndex);
            } else {
                sourceInventory.remove(startIndex);
                targetInventory.remove(dropIndex);
                sourceInventory.add(it2, startIndex);
                targetInventory.add(it, dropIndex);
            }

            // Call update
            if(DragContext.containerType == FarmEnums.ItemType.MACHINE || containerType == FarmEnums.ItemType.MACHINE){
                setMachineInterfaceChanged(true);
            }
            if(DragContext.containerType == FarmEnums.ItemType.CONTAINER || containerType == FarmEnums.ItemType.CONTAINER){
                setContainerInterfaceChanged(true);
            }

            success = true;
        }

        DragContext.clear();

        event.setDropCompleted(success);
        event.consume();
    }



    /**
     * Returns the current window width.
     *
     * @return the window width
     */
    public double getWindowWidth() {
        return windowWidth;
    }

    /**
     * Returns the current window height.
     *
     * @return the window height
     */
    public double getWindowHeight() {
        return windowHeight;
    }

    /**
     * Returns the map width.
     *
     * @return the map width
     */
    public double getMapWidth() {
        return mapView.getMapWidth();
    }

    /**
     * Returns the map height.
     *
     * @return the map height
     */
    public double getMapHeight() {
        return mapView.getMapHeight();
    }

    /**
     * Returns the GameUI instance.
     *
     * @return the GameUI instance
     */
    public GameUI getGameUI() {
        return gameUI;
    }

    /**
     * Returns the ItemView instance.
     *
     * @return the ItemView instance
     */
    public ItemView getItemView() {
        return itemView;
    }

    /**
     * Returns the ElementView instance.
     *
     * @return the ElementView instance
     */
    public ElementView getElementView() {
        return elementView;
    }

    /**
     * Returns the MapView instance.
     *
     * @return the MapView instance
     */
    public MapView getMapView() {
        return mapView;
    }

    /**
     * Returns whether the collider display is enabled for debugging.
     *
     * @return true if collider display is enabled, false otherwise
     */
    public boolean isShowCollider() {
        return showCollider;
    }

    /**
     * Sets whether the machine interface has changed and needs updating.
     *
     * @param hasMachineInterfaceChaneged true if changed, false otherwise
     */
    public void setMachineInterfaceChanged(boolean hasMachineInterfaceChaneged){
        this.hasMachineInterfaceChaneged = hasMachineInterfaceChaneged;
    }

    /**
     * Returns whether the machine interface has changed and needs updating.
     *
     * @return true if changed, false otherwise
     */
    public boolean hasMachineInterfaceChanged() {
        return hasMachineInterfaceChaneged;
    }

    /**
     * Sets whether the container interface has changed and needs updating.
     *
     * @param hasContainerInterfaceChanged true if changed, false otherwise
     */
    public void setContainerInterfaceChanged(boolean hasContainerInterfaceChanged){
        this.hasContainerInterfaceChanged = hasContainerInterfaceChanged;
    }

    /**
     * Returns whether the container interface has changed and needs updating.
     *
     * @return true if changed, false otherwise
     */
    public boolean hasContainerInterfaceChanged() {
        return hasContainerInterfaceChanged;
    }

    /**
     * Returns the MachineInterfaceUI instance.
     *
     * @return the MachineInterfaceUI instance
     */
    public MachineInterfaceUI getMachineInterfaceUI() {
        return machineInterfaceUI;
    }

    /**
     * Returns the ContainerInterfaceUI instance.
     *
     * @return the ContainerInterfaceUI instance
     */
    public ContainerInterfaceUI getContainerInterfaceUI() {
        return containerInterfaceUI;
    }
}
