package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.*;

/**
 * Represents a product item in the farm system.
 * <p>
 * Products are items that can be harvested, produced, or processed in the farm.
 * Each product has a specific type (culture, animal, machine, or seed) and
 * contains detailed information specific to that type. A product can only be
 * of one type at a time.
 * </p>
 */
public class Product extends Item {

    /**
     * The general type of this product (CULTURE, ANIMAL, MACHINE, or SEED).
     */
    private final ProductType productType;

    /**
     * The specific culture product type if this is a culture product.
     */
    private CultureProductType cultureProductType;

    /**
     * The specific animal product type if this is an animal product.
     */
    private AnimalProductType animalProductType;

    /**
     * The specific machine product type if this is a machine product.
     */
    private MachineProductType machineProductType;

    /**
     * The specific seed type if this is a seed product.
     */
    private Seed seedProductType;

    /**
     * Creates a new culture product with the specified culture product type.
     * The product inherits its name, cost, and texture ID from the culture product type.
     *
     * @param cultureProductType The specific type of culture product to create
     */
    public Product(CultureProductType cultureProductType) {
        super(ItemType.PRODUCT, cultureProductType.getName(), cultureProductType.getCost(), cultureProductType.getResalePrice(), cultureProductType.getTextureId());
        this.productType = ProductType.CULTURE;
        this.cultureProductType = cultureProductType;
    }

    /**
     * Creates a new animal product with the specified animal product type.
     * The product inherits its name, cost, and texture ID from the animal product type.
     *
     * @param animalProductType The specific type of animal product to create
     */
    public Product(AnimalProductType animalProductType) {
        super(ItemType.PRODUCT, animalProductType.getName(), animalProductType.getCost(), animalProductType.getResalePrice(), animalProductType.getTextureId());
        this.productType = ProductType.ANIMAL;
        this.animalProductType = animalProductType;
    }

    /**
     * Creates a new machine product with the specified machine product type.
     * The product inherits its name, cost, and texture ID from the machine product type.
     *
     * @param machineProductType The specific type of machine product to create
     */
    public Product(MachineProductType machineProductType) {
        super(ItemType.PRODUCT, machineProductType.getName(), machineProductType.getCost(), machineProductType.getResalePrice(), machineProductType.getTextureId());
        this.productType = ProductType.MACHINE;
        this.machineProductType = machineProductType;
    }

    /**
     * Creates a new seed product with the specified seed type.
     * The product inherits its name, cost, and texture ID from the seed type.
     *
     * @param seedProductType The specific type of seed to create
     */
    public Product(Seed seedProductType) {
        super(ItemType.PRODUCT, seedProductType.getName(), seedProductType.getCost(), seedProductType.getResalePrice(), seedProductType.getTextureId());
        this.productType = ProductType.SEED;
        this.seedProductType = seedProductType;
    }

    /**
     * Gets the general type of this product.
     *
     * @return The product type (CULTURE, ANIMAL, MACHINE, or SEED)
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * Gets the culture product type of this product.
     * This will be non-null only if the product type is CULTURE.
     *
     * @return The culture product type, or null if this is not a culture product
     */
    public CultureProductType getCultureProductType() {
        return cultureProductType;
    }

    /**
     * Gets the animal product type of this product.
     * This will be non-null only if the product type is ANIMAL.
     *
     * @return The animal product type, or null if this is not an animal product
     */
    public AnimalProductType getAnimalProductType() {
        return animalProductType;
    }

    /**
     * Gets the machine product type of this product.
     * This will be non-null only if the product type is MACHINE.
     *
     * @return The machine product type, or null if this is not a machine product
     */
    public MachineProductType getMachineProductType() {
        return machineProductType;
    }

    /**
     * Gets the seed type of this product.
     * This will be non-null only if the product type is SEED.
     *
     * @return The seed type, or null if this is not a seed product
     */
    public Seed getSeed() {
        return seedProductType;
    }
}
