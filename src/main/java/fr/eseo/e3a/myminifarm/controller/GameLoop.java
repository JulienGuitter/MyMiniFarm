package fr.eseo.e3a.myminifarm.controller;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.model.Map;
import fr.eseo.e3a.myminifarm.model.Player;
import fr.eseo.e3a.myminifarm.model.farmElement.*;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import fr.eseo.e3a.myminifarm.model.farmElement.Machine;
import fr.eseo.e3a.myminifarm.model.farmElement.Tool;
import fr.eseo.e3a.myminifarm.model.type.GameState;
import fr.eseo.e3a.myminifarm.model.type.KeyAction;
import fr.eseo.e3a.myminifarm.utils.GameTime;
import fr.eseo.e3a.myminifarm.utils.Point2D;
import fr.eseo.e3a.myminifarm.utils.Vector2;
import fr.eseo.e3a.myminifarm.view.UI.modules.InventoryBarUI;
import fr.eseo.e3a.myminifarm.view.UI.modules.TimeInfoUI;
import fr.eseo.e3a.myminifarm.view.UI.modules.UserInfoUI;
import javafx.application.Platform;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class GameLoop {
    public static final int TIME_RATIO = 200; //60; // TODO: Change to 60 for real time

    private static GameLoop instance;
    private GameController gameController;
    private UIController uiController;
    private ConfigController configController;

    private InventoryBarUI inventoryBarUI;
    private UserInfoUI userInfoUI;
    private TimeInfoUI timeInfoUI;

    private int actualFPS, actualUPS;

    private Map map;
    private Player player;


    private Set<KeyAction> keysPressed;

    private boolean running;

    private GameTime gameTime;
    private double lastDayTimeUpdate;

    private boolean hasChange;

    /**
     * Private constructor for the GameLoop singleton.
     * Initializes the game loop and its dependencies.
     */
    private GameLoop(){
        // Initialize the game loop
        keysPressed = EnumSet.noneOf(KeyAction.class);

        this.gameController = GameController.getInstance();
        this.uiController = UIController.getInstance();
        this.configController = ConfigController.getInstance();

        // Initialize the game state
        this.gameTime = new GameTime();
        this.lastDayTimeUpdate = System.currentTimeMillis()-(60000/TIME_RATIO);

        running = true;
    }

    /**
     * Returns the singleton instance of the GameLoop.
     *
     * @return the instance of GameLoop
     */
    public static GameLoop getInstance() {
        if (instance == null) {
            instance = new GameLoop();
        }
        return instance;
    }

    /**
     * Sets the singleton instance of the GameLoop.
     *
     * @param gameLoop the GameLoop instance to set
     */
    public static void setInstance(GameLoop gameLoop) {
        instance = gameLoop;
    }

    /**
     * Initializes the game state, player, and map, and returns the initial game state.
     *
     * @return the initial game state
     */
    public GameState init() {
        GameState gameState = GameState.START_GAME;

        System.out.println("Loading the game ...");
        double startTime = System.currentTimeMillis();

        // Create the player instance
        player = new Player();
        uiController.initPlayer(player, "textures/entity/clint.png");

        // Load the map from the json file
        map = new Map(Objects.requireNonNull(MyMiniFarm.class.getResource("map/Farm.json")).getPath());

        // Initialize the map
        uiController.initMap(map);
        player.setPos(new Vector2(map.getMapJson().getWidth() / 2, map.getMapJson().getHeight() / 2));
        gameState = GameState.PLAYING;

//        timeInfoUI.setDayLabel(nbDay, dayHour, dayMinute);

        double endTime = System.currentTimeMillis();
        System.out.println("Game loaded in " + (endTime - startTime) + "ms");

        return gameState;
    }

    /**
     * Starts the main game loop, handling updates for UI and logic.
     */
    public void run(){
        double timePerFrame = 1000000000.0 / configController.getConfig().getFps();
        double timePerUpdate = 1000000000.0 / configController.getConfig().getUps();

        long previousTime = System.nanoTime();

        int fpsCount = 0;
        int upsCount = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaF = 0;
        double deltaU = 0;

        while(running){
            long now = System.nanoTime();

            // Calculate the delta time
            deltaF += (now - previousTime) / timePerFrame;
            deltaU += (now - previousTime) / timePerUpdate;
            previousTime = now;

            if(deltaF >= 1){
                // Update the game UI
                updateUI();
                fpsCount++;
                deltaF--;
            }


            if(deltaU >= 1){
                // Update the game logic
                updateLogic();
                upsCount++;
                deltaU--;
            }


            // Used to count the real FPS and UPS
            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                actualFPS = fpsCount;
                actualUPS = upsCount;
                fpsCount = 0;
                upsCount = 0;
            }
        }
    }


    private void updateAllMachines(){
        for (var entry : map.getMachines().entrySet()) {
            Machine machine = entry.getValue();
            if (machine.updateProduction())
                uiController.setMachineInterfaceChanged(true);
        }
    }

    private void updateUI(){
        Platform.runLater(() -> {
            // Update the UI with the new player position
            if(gameController.isDebugMode()){
                uiController.updateDebugContent(actualFPS, actualUPS);
            }
            uiController.update();
            if(player.isInteracting()){
                inventoryBarUI.selectCell(player.getInvBarIndex());
                player.setInteracting(false);
            }
            if(player.getInventory().hasChanged()){
                inventoryBarUI.updateInventoryBar();
                player.getInventory().setChanged(false);
            }
            if(player.hasChange()){
                userInfoUI.setMoneyLabel(player.getMoney());
                player.setChanged(false);
            }
            if(hasChange){
                timeInfoUI.setDayLabel(gameTime);
                hasChange = false;
            }
            if(map.hasCropCellChange()){
                uiController.getMapView().updateCropCell();
                map.setCropCellChange(false);
            }
            if(map.hasPlantCropChange()){
                uiController.getMapView().updatePlantCropCell();
                map.setPlantCropChange(false);
            }
            if(map.hasMachineChange()){
                uiController.getMapView().updateMachine();
                map.setMachineChange(false);
            }
            if(map.hasContainerChange()){
                uiController.getMapView().updateContainer();
                map.setContainerChange(false);
            }
            if(uiController.hasMachineInterfaceChanged()){
                uiController.getMachineInterfaceUI().update();
                uiController.setMachineInterfaceChanged(false);
            }
            if(uiController.hasContainerInterfaceChanged()){
                uiController.getContainerInterfaceUI().update();
                uiController.setContainerInterfaceChanged(false);
            }
            if(map.hasDropItemChange()){
                uiController.getMapView().updateDropItem();
                map.setDropItemChange(false);
            }
        });
    }

    private void updateLogic(){
        // Update the player speed based on the keys pressed
        int x = 0;
        int y = 0;
        for (KeyAction key : keysPressed) {
            x += getXDirection(key);
            y += getYDirection(key);
        }
        Vector2 playerPos = player.getPos();
        player.updateSpeed(x, y);
        Vector2 nextPos = player.getNextPos();

        // Check if the next position is valid

        if(!map.isValidPosX(new Vector2(nextPos.x(), playerPos.y()), x)){
            player.resetSpeedX();
        }
        if(!map.isValidPosY(new Vector2(playerPos.x(), nextPos.y()), y)){
            player.resetSpeedY();
        }


        updateTime();
        updateAllMachines();

        player.update();
        map.getDropItem(player.getPos());
        map.setPos(player.getPos());

    }



    private void updateTime(){
        double currentTime = System.currentTimeMillis();
        if(currentTime - lastDayTimeUpdate >= (60000/TIME_RATIO)){
            gameTime.addminute(1);
            lastDayTimeUpdate = currentTime;
            hasChange = true;

            updateEachGameMinute();
        }
    }

    private void updateEachGameMinute(){
        map.setPlantCropChange(true);
    }

    /**
     * Handle key press events.
     *
     * @param key the key that was pressed
     */
    public void onKeyPress(KeyAction key) {
        if (keysPressed.contains(key)) {
            return;
        }
        keysPressed.add(key);
    }

    /**
     * Handle key release events.
     *
     * @param key the key that was released
     */
    public void onKeyRelease(KeyAction key) {
        keysPressed.remove(key);
    }

    /**
     * Handle left mouse button click events.
     *
     * @param pos the position of the mouse click
     */
    public void onLeftClick(Point2D pos) {
        if (gameController.getGameState() == GameState.PLAYING) {
            Item itemInHand = player.getCurrentItem();

            if (itemInHand != null) {
                switch (itemInHand.getItemType() ){
                    case TOOL -> {
                        switch (((Tool) itemInHand).getTypeTools()){
                            case PICKAXE -> {
                                Item i = (Item) map.removeElement(pos);
                                if(i != null){
                                    i.setQuantity(1);
                                    player.tryAddItemInventory(i, new Vector2(pos.x(), pos.y()));
                                }
                            }
                        }
                    }
                    case MACHINE -> {
                        boolean isPlaced = map.addMachine(pos, (Machine) itemInHand);
                        if(isPlaced) {
                            player.getInventory().deductQuantity(player.getInvBarIndex(), 1);
                        }
                    }
                    case CONTAINER -> {
                        boolean isPlaced = map.addContainer(pos, (Container) itemInHand);
                        if(isPlaced) {
                            player.getInventory().deductQuantity(player.getInvBarIndex(), 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Handle right mouse button click events.
     *
     * @param pos the position of the mouse click
     */
    public void onRigthClick(Point2D pos) {
        if (gameController.getGameState() == GameState.PLAYING) {

            Point2D[] targets = { pos, new Point2D(pos.x(), pos.y() + 1) };

            for (Point2D target : targets) {
                if(map.isCellOccupiedByCropCell(target)){
                    Culture culture = map.getCropCell(target).getCulture();
                    if(culture != null && culture.isMature()){
                        Product produit = culture.attemptHarvest();
                        if(produit != null){
                            player.tryAddItemInventory(produit, new Vector2(target.x(), target.y()));
                            map.getCropCell(target).clear();
                            map.setCropCellChange(true);
                            return;
                        }
                    }
                }

                if(map.isCellOccupiedByMachine(target)){
                    uiController.setMachineInterfaceChanged(true);
                    uiController.openMachineInterfaceUI(map.getMachine(target));
                    return;
                }

                if(map.isCellOccupiedByContainer(target)){
                    uiController.setContainerInterfaceChanged(true);
                    uiController.openContainerInterfaceUI(map.getContainer(target));
                    return;
                }
            }

            Item itemInHand = player.getCurrentItem();
            if (itemInHand != null) {
                switch (itemInHand.getItemType()){
                    case TOOL -> {
                        switch (((Tool) itemInHand).getTypeTools()){
                            case HOE -> {
                                map.addCropCell(pos);
                            }
                            case WATERING_CAN -> {
                                CropCell cp = map.getCropCell(pos);
                                if(cp != null && cp.isOccupied()){
                                    cp.water();
                                }
                            }
                        }
                    }
                    case PRODUCT -> {
                        if(itemInHand instanceof Product product && product.getProductType() == FarmEnums.ProductType.SEED){
                            CropCell cp = map.getCropCell(pos);
                            if(cp != null && !cp.isOccupied()){
                                cp.plant(product);
                                player.getInventory().deductQuantity(player.getInvBarIndex(), 1);
                            }
                        }
                    }
                }
            }
        }
    }

    private static int getXDirection(KeyAction action) {
        return switch (action) {
            case MOVE_LEFT -> -1;
            case MOVE_RIGHT -> 1;
            default -> 0;
        };
    }

    private static int getYDirection(KeyAction action) {
        return switch (action) {
            case MOVE_UP -> -1;
            case MOVE_DOWN -> 1;
            default -> 0;
        };
    }

    public void setInventoryBarUI(InventoryBarUI inventoryBarUI) {
        this.inventoryBarUI = inventoryBarUI;
    }

    public void setUserInfoUI(UserInfoUI userInfoUI) {
        this.userInfoUI = userInfoUI;
    }

    public void setTimeInfoUI(TimeInfoUI timeInfoUI) {
        this.timeInfoUI = timeInfoUI;
    }


    /**
     * Returns the current GameTime instance.
     *
     * @return the GameTime instance
     */
    public GameTime getGameTime() {
        return gameTime;
    }

    /**
     * Returns whether the game state has changed and needs updating.
     *
     * @return true if changed, false otherwise
     */
    public boolean hasChange() {
        return hasChange;
    }

    /**
     * Sets whether the game state has changed and needs updating.
     *
     * @param hasChange true if changed, false otherwise
     */
    public void setHasChange(boolean hasChange) {
        this.hasChange = hasChange;
    }


    /**
     * Returns the player object.
     *
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the map object.
     *
     * @return the map object
     */
    public Map getMap() {
        return map;
    }
}
