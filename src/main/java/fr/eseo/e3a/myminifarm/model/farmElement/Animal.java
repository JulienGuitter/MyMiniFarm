package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.Animals;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.MachineProductType;


/**
 * Class representing a farm animal.
 * <p>
 * An animal can be fed with appropriate food, age over time, and produce an item
 * after a certain delay if it has been fed. Each animal has specific food requirements
 * and produces specific products after being fed and waiting for the production delay.
 * </p>
 *
 * @see Item
 * @see Product
 * @see FarmEnums.Animals
 */
public class Animal extends Item {
    /**
     * Indicates whether the animal has been fed.
     * When true, the animal has been fed and is in the production cycle.
     */
    private boolean isFed;

    /**
     * The timestamp (in milliseconds) when the animal can next produce.
     * This value is set when the animal is fed and is used to determine
     * when production is complete.
     */
    private long nextProductionTime;

    /**
     * The specific type of animal this instance represents.
     * Determines food requirements, production type, and other characteristics.
     */
    private final Animals typeAnimals;

    /**
     * Creates a new animal of the specified type.
     * <p>
     * Initializes the animal with properties based on its type such as name,
     * cost, and texture ID. The animal is created in an unfed state.
     * </p>
     *
     * @param typeAnimals The type of animal to create, from the Animals enum
     */
    public Animal(Animals typeAnimals) {
        super(ItemType.ANIMAL, typeAnimals.getName(), typeAnimals.getCost(), typeAnimals.getResalePrice(), typeAnimals.getTextureId());
        this.typeAnimals = typeAnimals;
        this.isFed = false;
    }

    /**
     * Checks if the given food is suitable for this animal.
     * <p>
     * Each animal type has specific food requirements defined in the Animals enum.
     * </p>
     *
     * @param food The food type to check for suitability
     * @return {@code true} if the food is appropriate for this animal, {@code false} otherwise
     */
    public boolean isSuitable(MachineProductType food) {
        return food == typeAnimals.getFood();
    }

    /**
     * Returns whether the animal has been fed.
     * <p>
     * A fed animal is in the process of producing an item.
     * </p>
     *
     * @return {@code true} if the animal is currently fed, {@code false} otherwise
     */
    public boolean isFed() {
        return isFed;
    }

    /**
     * Sets the fed status of the animal.
     * <p>
     * This method can be used to manually control the fed state of the animal.
     * </p>
     *
     * @param isFed {@code true} to mark the animal as fed, {@code false} to mark as unfed
     */
    public void setIsFed(boolean isFed) {
        this.isFed = isFed;
    }

    /**
     * Returns the scheduled time for the next production.
     * <p>
     * This timestamp represents when the animal will be ready to produce after being fed.
     * </p>
     *
     * @return Timestamp in milliseconds when the animal will be ready to produce
     */
    public long getNextProductionTime() {
        return nextProductionTime;
    }

    /**
     * Sets the next production time.
     * <p>
     * This method can be used to manually set when the animal will be ready to produce.
     * </p>
     *
     * @param nextProductionTime Timestamp in milliseconds for the next production
     */
    public void setNextProductionTime(long nextProductionTime) {
        this.nextProductionTime = nextProductionTime;
    }

    /**
     * Feeds the animal if the food is appropriate and available.
     * <p>
     * If feeding is successful, the animal is marked as fed and the production timer starts.
     * The production timer is set based on the animal type's production delay.
     * </p>
     *
     * @param food The food type used to feed the animal
     * @return {@code true} if the animal was successfully fed, {@code false} if the food was unsuitable
     */
    public boolean feed(MachineProductType food) {
        if (isSuitable(food)) {
            this.isFed = true;
            this.nextProductionTime = System.currentTimeMillis() + this.typeAnimals.getProductionDelay();
            System.out.println(getName() + " has been fed. Production in " + (this.typeAnimals.getProductionDelay() / 1000) + " seconds.");
            return true;
        }
        return false;
    }

    /**
     * Checks if the animal is ready to produce an item.
     * <p>
     * An animal is ready to produce when it has been fed and the production delay has elapsed.
     * </p>
     *
     * @return {@code true} if the animal is fed and the production delay has passed, {@code false} otherwise
     */
    public boolean isReadyToProduce() {
        return isFed && System.currentTimeMillis() >= nextProductionTime;
    }

    /**
     * Attempts to produce an item if the animal is ready.
     * <p>
     * If the animal is ready to produce (fed and delay has passed), it creates the appropriate
     * product and resets its fed status to false. If not ready, returns null.
     * </p>
     *
     * @return The produced {@link Product} if the animal is ready, or {@code null} if not ready
     */
    public Product attemptProduction() {
        if (isReadyToProduce()) {
            isFed = false;
            return produce();
        }
        return null;
    }

    /**
     * Produces an item specific to the type of animal.
     * <p>
     * This method creates a new Product instance based on the animal's production type.
     * It should only be called when the animal is ready to produce.
     * </p>
     *
     * @return The produced {@link Product} based on the animal's type
     */
    public Product produce() {
        return new Product(typeAnimals.getProductionType());
    }
}
