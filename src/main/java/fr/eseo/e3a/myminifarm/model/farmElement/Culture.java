package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.Seed;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.CultureType;
import fr.eseo.e3a.myminifarm.utils.GameTime;

/**
 * Class representing a crop culture in the farm.
 * A culture grows over time after being planted and watered, and produces an item
 * when it reaches maturity. The growth of a culture progresses through several stages
 * until it becomes mature and can be harvested.
 */
public class Culture extends Item {

    /**
     * The maximum number of growth stages before reaching maturity
     */
    private final int maxGrowthStage = 5;

    /**
     * The type of culture determining its behavior and product
     */
    private final CultureType cultureType;

    /**
     * The current growth stage of the culture
     */
    private int growthStage;

    /**
     * Flag indicating if the culture has been planted
     */
    private boolean isPlanted;

    /**
     * Flag indicating if the culture has been watered
     */
    private boolean isWatered;

    /**
     * Flag indicating if the culture has reached maturity
     */
    private boolean isMature;

    /**
     * Time in minutes required for the culture to advance one growth stage
     */
    private int timeForOnStage;

    /**
     * Game time when the culture will advance to the next growth stage
     */
    private GameTime nextStateTime;

    /**
     * Constructs a new Culture from a specific seed.
     * The culture inherits properties such as name, cost, and texture from the seed.
     *
     * @param seed The seed from which to create the culture
     */
    public Culture(Seed seed) {
        super(ItemType.CULTURE, seed.getName(), seed.getCost(), seed.getResalePrice(), seed.getTextureId());
        this.cultureType = CultureType.fromSeed(seed);
        this.isPlanted = false;
        this.isWatered = false;
        this.isMature = false;
    }

    /**
     * Marks the culture as planted.
     * A culture can only be planted once.
     *
     * @return {@code true} if planting succeeded, {@code false} otherwise
     */
    public boolean plant() {
        if (!isPlanted) {
            this.isPlanted = true;
            return true;
        }
        return false;
    }

    /**
     * Waters the culture and starts the growth timer.
     * A culture must be planted before it can be watered, and can only be watered once.
     * Watering initiates the growth process by calculating the time between growth stages
     * and setting the time for the next stage.
     *
     * @return {@code true} if watering started the growth, {@code false} otherwise
     */
    public boolean water() {
        if (isPlanted && !isWatered) {
            this.isWatered = true;
            this.timeForOnStage = (int) (this.cultureType.getGrowthTime() / maxGrowthStage);
            nextStateTime = GameLoop.getInstance().getGameTime().clone();
            nextStateTime.addminute(this.timeForOnStage);
            return true;
        }
        return false;
    }

    /**
     * Checks if the culture has been planted.
     *
     * @return {@code true} if the culture is planted, {@code false} otherwise
     */
    public boolean isPlanted() {
        return isPlanted;
    }

    /**
     * Checks if the culture has been watered.
     *
     * @return {@code true} if the culture is watered, {@code false} otherwise
     */
    public boolean isWatered() {
        return isWatered;
    }

    /**
     * Checks if the culture has reached maturity and is ready for harvest.
     *
     * @return {@code true} if the culture is mature, {@code false} otherwise
     */
    public boolean isMature() {
        return isMature;
    }

    /**
     * Updates the growth stage of the culture based on game time.
     * If the culture is planted, watered, and not yet mature, it will advance
     * to the next growth stage when the appropriate game time is reached.
     * When the maximum growth stage is reached, the culture becomes mature.
     */
    public void updateGrowthStage() {
        if (isPlanted && isWatered && !isMature) {
            if (GameLoop.getInstance().getGameTime().isTimePassed(nextStateTime)) {
                this.growthStage++;
                nextStateTime.addminute(this.timeForOnStage);
                GameLoop.getInstance().getMap().setCropCellChange(true);
                if (this.growthStage >= maxGrowthStage) {
                    this.isMature = true;
                }
            }
        }
    }

    /**
     * Attempts to harvest the culture if it is mature.
     * If successful, the culture is reset to its initial state and a product is returned.
     *
     * @return The harvested product if the culture is mature, null otherwise
     */
    public Product attemptHarvest() {
        if (isMature) {
            isPlanted = false;
            isWatered = false;
            isMature = false;
            growthStage = 0;
            GameLoop.getInstance().getMap().setPlantCropChange(true);
            return harvest();
        }
        return null;
    }

    /**
     * Creates and returns the product of this culture.
     * This method is called after a successful harvest.
     *
     * @return A new product of the type corresponding to this culture
     */
    public Product harvest() {
        return new Product(cultureType.getProductType());
    }

    /**
     * Gets the type of this culture.
     *
     * @return The culture type
     */
    public CultureType getCultureType() {
        return cultureType;
    }

    /**
     * Gets the current growth stage of the culture.
     *
     * @return The current growth stage
     */
    public int getGrowthStage() {
        return growthStage;
    }
}