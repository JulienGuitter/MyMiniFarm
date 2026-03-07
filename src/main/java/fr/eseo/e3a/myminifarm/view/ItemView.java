package fr.eseo.e3a.myminifarm.view;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public final class ItemView {
    private static SpriteSheet machineSpriteSheet;
    private static SpriteSheet animalSpriteSheet;
    private static SpriteSheet cultureSpriteSheet;
    private static SpriteSheet toolsSpriteSheet;

    /**
     * Constructs the ItemView and initializes sprite sheets for machines, animals, cultures, and tools.
     */
    public ItemView(){
        machineSpriteSheet = new SpriteSheet(MyMiniFarm.class.getResourceAsStream("textures/items/craftables.png"), 4, 2);
        animalSpriteSheet = new SpriteSheet(MyMiniFarm.class.getResourceAsStream("textures/items/animals.png"), 2, 2);
        cultureSpriteSheet = new SpriteSheet(MyMiniFarm.class.getResourceAsStream("textures/items/cultures.png"), 6, 5);
        toolsSpriteSheet = new SpriteSheet(MyMiniFarm.class.getResourceAsStream("textures/items/tools.png"), 4, 1);
    }

    /**
     * Returns an ImageView for a machine sprite based on its id.
     *
     * @param id the machine id
     * @return ImageView of the machine
     */
    private static ImageView getMachineImage(int id){
        ImageView imageView = new ImageView(machineSpriteSheet.getSprite(id));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        return imageView;
    }

    /**
     * Returns an ImageView for an animal sprite based on its id.
     *
     * @param id the animal id
     * @return ImageView of the animal
     */
    private static ImageView getAnimalImage(int id){
        ImageView imageView = new ImageView(animalSpriteSheet.getSprite(id));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        return imageView;
    }

    /**
     * Returns an ImageView for a culture sprite based on its id.
     *
     * @param id the culture id
     * @return ImageView of the culture
     */
    private static ImageView getCultureImage(int id){
        ImageView imageView = new ImageView(cultureSpriteSheet.getSprite(id));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        return imageView;
    }

    /**
     * Returns an ImageView for a tool sprite based on its id.
     *
     * @param id the tool id
     * @return ImageView of the tool
     */
    private static ImageView getToolImage(int id){
        ImageView imageView = new ImageView(toolsSpriteSheet.getSprite(id));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        return imageView;
    }

    /**
     * Returns an ImageView for an item based on its id and type.
     *
     * @param id the item id
     * @param type the item type
     * @return ImageView of the item
     */
    public static ImageView getItemImage(int id, FarmEnums.ItemType type){
        switch (type) {
            case MACHINE, CONTAINER:
                return getMachineImage(id);
            case CULTURE, PRODUCT:
                return getCultureImage(id);
            case ANIMAL:
                return getAnimalImage(id);
            case TOOL:
                return getToolImage(id);
            default:
                return null;
        }
    }

    /**
     * Crops a region from an image and returns it as a new Image.
     *
     * @param x the x-coordinate of the region
     * @param y the y-coordinate of the region
     * @param width the width of the region
     * @param height the height of the region
     * @param image the source image
     * @return the cropped Image
     */
    private static Image cropImage(int x, int y, int width, int height, Image image) {
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }
}
