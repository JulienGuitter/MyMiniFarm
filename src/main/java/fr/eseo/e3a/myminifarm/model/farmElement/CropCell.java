package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.model.Player;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;

/**
 * The CropCell class represents a farming plot where crops can be planted, watered, and harvested.
 * It extends the Item class and manages a Culture object that represents the planted crop.
 * Each crop cell can be in either occupied or free state, depending on whether a culture is planted.
 */
public class CropCell extends Item {
    /**
     * Flag indicating whether the crop cell is occupied by a culture
     */
    private boolean isOccupied;

    /**
     * The culture currently planted in this crop cell
     */
    private Culture culture;

    /**
     * Constructs a new empty CropCell.
     * Initializes the crop cell with default values and no planted culture.
     */
    public CropCell() {
        super(ItemType.STRUCTURE, "Crop Cell", 0, 0, 1); // Exemple de coût et textureId
        this.isOccupied = false;
        this.culture = null;
    }

    /**
     * Checks if the crop cell is currently occupied by a culture.
     *
     * @return true if the crop cell is occupied, false otherwise
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    /**
     * Checks if the crop cell is free and available for planting.
     *
     * @return true if the crop cell is free, false if it's occupied
     */
    public boolean isFree() {
        return !isOccupied;
    }

    /**
     * Attempts to plant a seed in this crop cell.
     * The planting will only succeed if the cell is free and a valid seed is provided.
     *
     * @param seedProduct The seed product to plant
     * @return true if planting was successful, false otherwise
     */
    public boolean plant(Product seedProduct) {
        if (isOccupied) {
            return false;
        }
        if (seedProduct == null || seedProduct.getProductType() != FarmEnums.ProductType.SEED) {
            return false;
        }

        Culture newCulture = new Culture(seedProduct.getSeed());
        if (newCulture.plant()) {
            this.culture = newCulture;
            this.isOccupied = true;
            GameLoop.getInstance().getMap().setPlantCropChange(true);
            return true;
        }

        System.out.println("Planting failed.");
        return false;
    }

    /**
     * Waters the culture in this crop cell if one exists.
     * Watering is necessary for crop growth.
     *
     * @return true if watering was successful, false if there's no culture or it has already been watered
     */
    public boolean water() {
        if (!isOccupied || culture == null) {
            return false;
        }

        boolean watered = culture.water();
        if (watered) {
            GameLoop.getInstance().getMap().setCropCellChange(true);
        }
        return watered;
    }

    /**
     * Checks if the culture in this crop cell has been watered.
     *
     * @return true if the crop cell has a culture that has been watered, false otherwise
     */
    public boolean isWatered() {
        if (!isOccupied || culture == null) {
            return false;
        }
        return culture.isWatered();
    }

    /**
     * Checks if the culture is mature and harvests it if possible.
     * If harvesting is successful, the harvested product is added to the player's inventory
     * and the crop cell is cleared.
     *
     * @param player The player who will receive the harvested product
     * @return true if a product was harvested, false otherwise
     */
    public boolean checkGrowth(Player player) {
        if (!isOccupied || culture == null) {
            return false;
        }

        if (culture.isMature()) {
            Product product = culture.attemptHarvest();
            if (product != null) {
                player.getInventory().add(product);
                this.culture = null;
                this.isOccupied = false;
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the culture currently planted in this crop cell.
     *
     * @return The culture object, or null if the crop cell is empty
     */
    public Culture getCulture() {
        return culture;
    }

    /**
     * Clears the crop cell, removing any planted culture.
     * This resets the crop cell to an empty state.
     */
    public void clear() {
        this.culture = null;
        this.isOccupied = false;
    }
}