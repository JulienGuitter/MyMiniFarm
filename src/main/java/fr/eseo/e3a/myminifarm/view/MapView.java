package fr.eseo.e3a.myminifarm.view;

import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.Map;
import fr.eseo.e3a.myminifarm.model.farmElement.CropCell;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import fr.eseo.e3a.myminifarm.utils.Point2D;
import fr.eseo.e3a.myminifarm.utils.Vector2;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * The MapView class is responsible for displaying the map in the game.
 * It uses JavaFX to create a graphical representation of the map.
 */
public class MapView {
    private UIController uiController;
    private Map map;

    private SpriteSheet spriteSheet;
    private StackPane mapStackPane;
    private GridPane dropItemStackPane;
    private GridPane cropCellStackPane;
    private GridPane plantedCropStackPane;
    private GridPane machineStackPane;
    private GridPane containerStackPane;
    private GridPane colliderDebugStackPane;

    private double mapWidth;
    private double mapHeight;

    private boolean updateDebug = true;

    /**
     * Constructor for the MapView class.
     * Initializes the map and spriteSheet, and sets up the graphical layers.
     *
     * @param map        The map to be displayed.
     * @param spriteSheet The sprite sheet used for rendering the map.
     */
    public MapView(Map map, SpriteSheet spriteSheet){
        uiController = UIController.getInstance();

        this.map = map;
        this.spriteSheet = spriteSheet;
        this.mapStackPane = new StackPane();

        List<GridPane> gridPanes = getGridPane();

        mapStackPane = new StackPane();

        for (GridPane pane : gridPanes) {
            mapStackPane.getChildren().add(pane);
        }

        mapWidth = this.map.getMapJson().getWidth() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO;
        mapHeight = this.map.getMapJson().getHeight() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO;


        this.dropItemStackPane = new GridPane();
        this.cropCellStackPane = new GridPane();
        this.plantedCropStackPane = new GridPane();
        this.machineStackPane = new GridPane();
        this.containerStackPane = new GridPane();
        mapStackPane.getChildren().add(cropCellStackPane);
        mapStackPane.getChildren().add(dropItemStackPane);
        mapStackPane.getChildren().add(plantedCropStackPane);
        mapStackPane.getChildren().add(machineStackPane);
        mapStackPane.getChildren().add(containerStackPane);

        this.colliderDebugStackPane = new GridPane();
        mapStackPane.getChildren().add(colliderDebugStackPane);
    }

    /**
     * This method is used to get the GridPane representation of the map.
     * It creates a GridPane for each layer of the map and adds it to a list.
     * Each GridPane is populated with the corresponding tiles from the map data.
     *
     * @return A list of GridPanes representing the map layers.
     */
    private List<GridPane> getGridPane(){
        List<GridPane> gridPanes = new ArrayList<>();

        int nbLayers = this.map.getMapJson().getLayers().size();
        int idOffset = this.map.getMapJson().getTilesets().get(0).getFirstgid();

        for(int i = 0; i < nbLayers; i++){
            GridPane gridPane = new GridPane();
            gridPane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            gridPane.setMinSize(this.map.getMapJson().getWidth(), this.map.getMapJson().getHeight());
            List<Integer> gridData = this.map.getMapJson().getLayers().get(i).getData();
            boolean visible = this.map.getMapJson().getLayers().get(i).isVisible();
            if(!visible){
                continue;
            }

            for (int j = 0; j < gridData.size(); j++) {
                int x = j % this.map.getMapJson().getWidth();
                int y = j / this.map.getMapJson().getWidth();

                ColumnConstraints col = new ColumnConstraints(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                gridPane.getColumnConstraints().add(col);

                RowConstraints row = new RowConstraints(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                gridPane.getRowConstraints().add(row);

                int id = gridData.get(j) - idOffset;
                if(id >= 0){
                    ImageView image = new ImageView(spriteSheet.getSprite(id));
                    image.setFitWidth(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                    image.setFitHeight(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                    gridPane.add(image, x, y);
                }
            }
            gridPanes.add(gridPane);
        }

        return gridPanes;
    }

    /**
     * Returns the StackPane containing all map layers.
     *
     * @return the StackPane for the map
     */
    public StackPane getStackPane(){
        return mapStackPane;
    }


    /**
     * Updates the map's position and debug overlays based on the current state.
     */
    public void update(){

        double posX = this.map.getPos().x() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO;
        double posY = this.map.getPos().y() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO;

        double minX = uiController.getWindowWidth()/2;
        double minY = uiController.getWindowHeight()/2;
        double maxX = mapWidth - uiController.getWindowWidth()/2;
        double maxY = mapHeight - uiController.getWindowHeight()/2;


        if(posX < minX){
            this.mapStackPane.setTranslateX(0);
        }else if (posX > maxX){
            this.mapStackPane.setTranslateX(-mapWidth + uiController.getWindowWidth());
        }else{
            this.mapStackPane.setTranslateX(-posX + minX);
        }

        if(posY < minY){
            this.mapStackPane.setTranslateY(0);
        }else if(posY > maxY){
            this.mapStackPane.setTranslateY(-mapHeight + uiController.getWindowHeight());
        }else{
            this.mapStackPane.setTranslateY(-posY + minY);
        }

        if(map.hasCropCellChange() || map.hasMachineChange()){
            updateDebug = true;
        }

        // DEBUG
        if(uiController.isShowCollider() && updateDebug){
            colliderDebugStackPane.getChildren().clear();
            int i = 0;
            for (var entry : map.getCollisionLayer().getData()) {
                Point2D pos = new Point2D(i % map.getMapJson().getWidth(), i / map.getMapJson().getWidth());
                if(map.isCollision(pos)){
                    Rectangle rect = new Rectangle(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO, UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                    rect.setTranslateX(pos.x() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                    rect.setTranslateY(pos.y() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                    rect.setOpacity(0.5);
                    rect.setStyle("-fx-fill: red;");
                    colliderDebugStackPane.getChildren().add(rect);
                }
                i++;
            }
            updateDebug = false;
        }else if(!uiController.isShowCollider()){
            colliderDebugStackPane.getChildren().clear();
        }
    }

    /**
     * Updates the machine layer with the current machines on the map.
     */
    public void updateMachine(){
        machineStackPane.getChildren().clear();
        for (var entry : map.getMachines().entrySet()) {
            Point2D pos = entry.getKey();
            ImageView image = new ImageView(ElementView.getItemImage(entry.getValue().getTextureId(), 0, entry.getValue().getItemType()).getImage());
            image.setFitWidth(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            image.setFitHeight(UIController.IMAGE_SIZE*2 * UIController.IMAGE_RATIO);
            machineStackPane.getChildren().add(image);
            image.setTranslateX(pos.x() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            image.setTranslateY((pos.y()-1) * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
        }
    }

    /**
     * Updates the container layer with the current containers on the map.
     */
    public void updateContainer(){
        containerStackPane.getChildren().clear();
        for (var entry : map.getContainers().entrySet()) {
            Point2D pos = entry.getKey();
            ImageView image = new ImageView(ElementView.getItemImage(entry.getValue().getTextureId(), 0, entry.getValue().getItemType()).getImage());
            image.setFitWidth(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            image.setFitHeight(UIController.IMAGE_SIZE*2 * UIController.IMAGE_RATIO);
            containerStackPane.getChildren().add(image);
            image.setTranslateX(pos.x() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            image.setTranslateY((pos.y()-1) * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
        }
    }

    /**
     * Updates the crop cell layer with the current crop cells on the map.
     */
    public void updateCropCell(){
        cropCellStackPane.getChildren().clear();
        for (var entry : map.getCropCells().entrySet()) {
            Point2D pos = entry.getKey();
            CropCell cropCell = entry.getValue();
            int border = (map.getCropCell(new Point2D(pos.x(), pos.y()-1)) != null ? 1 : 0) +
                         (map.getCropCell(new Point2D(pos.x()+1, pos.y())) != null ? 2 : 0) +
                         (map.getCropCell(new Point2D(pos.x(), pos.y()+1)) != null ? 4 : 0) +
                         (map.getCropCell(new Point2D(pos.x()-1, pos.y())) != null ? 8 : 0);

            int watered = cropCell.isWatered() ? 1 : 0;
            ImageView image = ElementView.getItemImage(watered, border, FarmEnums.ItemType.FARM_LAND);
            image.setFitWidth(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            image.setFitHeight(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            cropCellStackPane.getChildren().add(image);
            image.setTranslateX(pos.x() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            image.setTranslateY(pos.y() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
        }
    }

    /**
     * Updates the planted crop cell layer with the current planted crops on the map.
     */
    public void updatePlantCropCell(){
        plantedCropStackPane.getChildren().clear();
        for (var entry : map.getCropCells().entrySet()) {
            Point2D pos = entry.getKey();
            CropCell cropCell = entry.getValue();
            if(cropCell.isOccupied() && cropCell.getCulture() != null){
                cropCell.getCulture().updateGrowthStage();

                ImageView image = ElementView.getItemImage(cropCell.getCulture().getTextureId(), cropCell.getCulture().getGrowthStage(), FarmEnums.ItemType.CROP);

                int centerOffset = (int) ((UIController.IMAGE_SIZE * UIController.IMAGE_RATIO)*1.2);

                image.setFitWidth(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                image.setFitHeight(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO*2);
                plantedCropStackPane.getChildren().add(image);
                image.setTranslateX(pos.x() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
                image.setTranslateY(pos.y() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO - centerOffset);

            }
        }
    }

    /**
     * Updates the dropped item layer with the current dropped items on the map.
     */
    public void updateDropItem(){
        dropItemStackPane.getChildren().clear();
        for (var entry : map.getDropedItem().entrySet()) {
            Vector2 pos = entry.getKey();
            ImageView image = ItemView.getItemImage(entry.getValue().getTextureId(), entry.getValue().getItemType());
            image.setFitWidth((double) (UIController.IMAGE_SIZE * UIController.IMAGE_RATIO) /2);
            image.setFitHeight((double) (UIController.IMAGE_SIZE * UIController.IMAGE_RATIO) /2);
            dropItemStackPane.getChildren().add(image);
            image.setTranslateX(pos.x() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
            image.setTranslateY(pos.y() * UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
        }
    }

    /**
     * Returns the width of the map in pixels.
     *
     * @return the map width
     */
    public double getMapWidth(){
        return mapWidth;
    }

    /**
     * Returns the height of the map in pixels.
     *
     * @return the map height
     */
    public double getMapHeight(){
        return mapHeight;
    }

    /**
     * Returns the current position of the map as a Vector2.
     *
     * @return the map position
     */
    public Vector2 getMapPos(){
        return new Vector2(mapStackPane.getTranslateX(), mapStackPane.getTranslateY());
    }

    /**
     * Sets whether the debug overlay should be updated.
     *
     * @param activate true to activate debug update, false otherwise
     */
    public void setUpdateDebug(boolean activate){
        updateDebug = activate;
    }
}
