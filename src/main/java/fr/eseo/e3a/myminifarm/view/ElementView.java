package fr.eseo.e3a.myminifarm.view;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

/**
 * The ElementView class is responsible for providing the correct images for the different elements
 * in the farm, such as crops and farm land. It uses sprite sheets to retrieve the correct image
 * based on the element's id and other parameters like maturation or offset.
 */
public final class ElementView {
    private static SpriteSheet cultureSpriteSheet;
    private static SpriteSheet farmLandSpriteSheet;

    /**
     * Constructs the ElementView and initializes sprite sheets for cultures and farm land.
     */
    public ElementView(){
        cultureSpriteSheet = new SpriteSheet(MyMiniFarm.class.getResourceAsStream("textures/terrain/crops.png"), 6, 6);
        farmLandSpriteSheet = new SpriteSheet(MyMiniFarm.class.getResourceAsStream("textures/terrain/farm_land.png"), 4, 8);
    }

    /**
     * Returns an ImageView for a culture sprite based on its id and maturation stage.
     *
     * @param id the culture id
     * @param maturation the maturation stage
     * @return ImageView of the culture
     */
    private static ImageView getCultureImage(int id, int maturation){
        ImageView imageView = new ImageView(cultureSpriteSheet.getSprite(id*cultureSpriteSheet.getColumns() + maturation));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        return imageView;
    }

    /**
     * Returns an ImageView for a farm land sprite based on its id and offset.
     *
     * @param id the farm land id
     * @param offset the border offset
     * @return ImageView of the farm land
     */
    private static ImageView getFarmLandImage(int id, int offset){
        ImageView imageView = new ImageView(farmLandSpriteSheet.getSprite((id*farmLandSpriteSheet.getColumns()*(farmLandSpriteSheet.getColumns())) + offset));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(50);
        return imageView;
    }


    /**
     * Returns an ImageView for an item based on its id, maturation, and type.
     *
     * @param id the item id
     * @param maturation the maturation or border value
     * @param type the item type
     * @return ImageView of the item
     */
    public static ImageView getItemImage(int id, int maturation, FarmEnums.ItemType type){
        switch (type) {
            case MACHINE, CONTAINER:
                return ItemView.getItemImage(id, FarmEnums.ItemType.MACHINE);
            case CROP:
                return getCultureImage(id, maturation);
            case FARM_LAND:
                return getFarmLandImage(id, getFarmLandBorderIndex(maturation));
            default:
                return null;
        }
    }

    /**
     * Calculates the border offset for farm land based on the value.
     *
     * @param value the border value
     * @return the offset for the farm land sprite
     */
    private static int getFarmLandBorderIndex(int value){
        int offset = 0;

        // Top : 1, Right : 2, Bottom : 4, Left : 8
        // Somme of the value define witch border is present
        switch (value){
            case 1:
                offset = 12;
                break;
            case 2:
                offset = 13;
                break;
            case 3:
                offset = 9;
                break;
            case 4:
                offset = 4;
                break;
            case 5:
                offset = 8;
                break;
            case 6:
                offset = 1;
                break;
            case 7:
                offset = 5;
                break;
            case 8:
                offset = 15;
                break;
            case 9:
                offset = 11;
                break;
            case 10:
                offset = 14;
                break;
            case 11:
                offset = 10;
                break;
            case 12:
                offset = 3;
                break;
            case 13:
                offset = 7;
                break;
            case 14:
                offset = 2;
                break;
            case 15:
                offset = 6;
                break;
            default:
                offset = 0;
                break;
        }

        return offset;
    }
}
