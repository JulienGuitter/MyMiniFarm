package fr.eseo.e3a.myminifarm.model.farmElement;

import java.util.Arrays;

/**
 * This class contains all the enumerations used in the farm simulation game.
 * It defines various types of items, products, animals, machines, and other
 * game elements with their properties and relationships.
 */
public class FarmEnums {

    /**
     * Enumeration of different item types in the farm system.
     * Categorizes all objects that can exist in the farm.
     */
    public enum ItemType {
        FARM_LAND,  /* Represents farm land */
        CROP,       /* Represents crops */
        ANIMAL,     /* Represents animals */
        MACHINE,    /* Represents machines */
        CONTAINER,  /* Represents storage containers */
        CULTURE,    /* Represents growing cultures */
        PRODUCT,    /* Represents products */
        STRUCTURE,  /* Represents structures */
        TOOL,       /* Represents tools */
    }

    /**
     * Enumeration of different product types in the farm system.
     * Categorizes all products by their source or usage.
     */
    public enum ProductType {
        ANIMAL,   /* Products derived from animals */
        MACHINE,  /* Products produced by machines */
        CULTURE,  /* Products derived from cultures */
        SEED      /* Seeds used for planting */
    }

    /**
     * Enumeration of animal products in the farm system.
     * Each product has a name, cost, and texture ID.
     */
    public enum AnimalProductType {
        MILK("Milk", 20, 35, 13),
        EGG("Egg", 5, 12, 12),
        BACON("Bacon", 30, 50, 15),
        WOOL("Wool", 25, 45, 14);

        /**
         * The display name of the animal product
         */
        private final String name;

        /**
         * The cost/value of the animal product
         */
        private final int cost;
        private final int resalePrice;
        private final int textureId;


        /**
         * Constructor for AnimalProductType enum.
         *
         * @param name      The display name of the animal product
         * @param cost      The cost/value of the animal product
         * @param textureId The ID of the texture for the animal product
         */
        AnimalProductType(String name, int cost, int resalePrice, int textureId) {
            this.name = name;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the animal product.
         *
         * @return The product name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the cost/value of the animal product.
         *
         * @return The product cost
         */
        public int getCost() {
            return cost;
        }

        /**
         * Gets the resale price
         * @return the product price resale
         */
        public int getResalePrice() {
            return resalePrice;
        }

        /**
         * Gets the texture ID of the animal product.
         *
         * @return The product texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the animal product.
         *
         * @return The product name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of animals in the farm system.
     * Each animal has properties such as name, cost, food requirements,
     * production type, and production timing.
     */
    public enum Animals {
        COW("Cow", 40, 25, MachineProductType.COW_FOOD, AnimalProductType.MILK, 920, 1),
        CHICKEN("Chicken", 12, 9, MachineProductType.CHICKEN_FOOD, AnimalProductType.EGG, 720, 0),
        PIG("Pig", 20, 15, MachineProductType.PIG_FOOD, AnimalProductType.BACON, 790, 2),
        SHEEP("Sheep", 18, 14, MachineProductType.SHEEP_FOOD, AnimalProductType.WOOL, 750, 3);

        /**
         * The display name of the animal
         */
        private final String name;

        /**
         * The purchase cost of the animal
         */
        private final int cost;

        /**
         * The resale value of the animal
         */
        private final int resalePrice;

        /**
         * The type of food required by the animal
         */
        private final MachineProductType food;

        /**
         * The type of product produced by the animal
         */
        private final AnimalProductType productionType;

        /**
         * The time in milliseconds between product generations
         */
        private final long productionDelay;

        /**
         * The ID of the texture used to represent the animal
         */
        private final int textureId;

        /**
         * Constructor for Animals enum.
         *
         * @param name            The display name of the animal
         * @param cost            The purchase cost of the animal
         * @param resalePrice     The resale value of the animal
         * @param food            The type of food required by the animal
         * @param productionType  The type of product produced by the animal
         * @param productionDelay The time in milliseconds between product generations
         * @param textureId       The ID of the texture for the animal
         */
        Animals(String name, int cost, int resalePrice, MachineProductType food, AnimalProductType productionType, long productionDelay, int textureId) {
            this.name = name;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.food = food;
            this.productionType = productionType;
            this.productionDelay = productionDelay;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the animal.
         *
         * @return The animal name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the purchase cost of the animal.
         *
         * @return The animal cost
         */
        public int getCost() {
            return cost;
        }

        /**
         * Gets the resale value of the animal.
         *
         * @return The animal resale price
         */
        public int getResalePrice() {
            return resalePrice;
        }

        /**
         * Gets the type of food required by the animal.
         *
         * @return The animal food type
         */
        public MachineProductType getFood() {
            return food;
        }

        /**
         * Gets the type of product produced by the animal.
         *
         * @return The animal production type
         */
        public AnimalProductType getProductionType() {
            return productionType;
        }

        /**
         * Gets the time in milliseconds between product generations.
         *
         * @return The animal production delay
         */
        public long getProductionDelay() {
            return productionDelay;
        }

        /**
         * Gets the texture ID of the animal.
         *
         * @return The animal texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the animal.
         *
         * @return The animal name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of machine products in the farm system.
     * Each product has a name, cost, and texture ID.
     */
    public enum MachineProductType {
        BREAD("Bread", 40, 80, 18),
        CHEESE("Cheese", 30, 60, 19),
        FLOUR("Flour", 10, 30, 20),
        COW_FOOD("Cow Food", 8, 20, 24),
        PIG_FOOD("Pig Food", 7, 18, 25),
        CHICKEN_FOOD("Chicken Food", 5, 10, 26),
        SHEEP_FOOD("Sheep Food", 6, 15, 27);

        /**
         * The display name of the machine product
         */
        private final String name;

        /**
         * The cost/value of the machine product
         */
        private final int cost;
        private final int resalePrice;
        private final int textureId;


        /**
         * Constructor for MachineProductType enum.
         *
         * @param name      The display name of the machine product
         * @param cost      The cost/value of the machine product
         * @param textureId The ID of the texture for the machine product
         */
        MachineProductType(String name, int cost, int resalePrice, int textureId) {
            this.name = name;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the machine product.
         *
         * @return The product name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the cost/value of the machine product.
         *
         * @return The product cost
         */
        public int getCost() {
            return cost;
        }

        /**
         * Gets the resale price
         * @return the product price resale
         */
        public int getResalePrice() {
            return resalePrice;
        }

        /**
         * Gets the texture ID of the machine product.
         *
         * @return The product texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the machine product.
         *
         * @return The product name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of machines in the farm system.
     * Each machine has properties such as name, cost, production delay,
     * and texture ID.
     */
    public enum Machines {
        WINDMILL("Windmill", 500, 250, 30, 1),
        OVEN("Oven", 700, 400, 50, 0),
        LOOM("Loom", 450, 250, 45, 5),
        MILKPROCESSOR("Milk Processor", 800, 450, 50, 4),
        SMOKER("Smoker", 500, 300, 70, 6);

        /**
         * The display name of the machine
         */
        private final String name;

        /**
         * The purchase cost of the machine
         */
        private final int cost;

        /**
         * The resale value of the machine
         */
        private final int resalePrice;

        /**
         * The time in minutes required to produce an item
         */
        private final int productionDelay;

        /**
         * The ID of the texture used to represent the machine
         */
        private final int textureId;

        /**
         * Constructor for Machines enum.
         *
         * @param name            The display name of the machine
         * @param cost            The purchase cost of the machine
         * @param resalePrice     The resale value of the machine
         * @param productionDelay The time in minutes required to produce an item
         * @param textureId       The ID of the texture for the machine
         */
        Machines(String name, int cost, int resalePrice, int productionDelay, int textureId) {
            this.name = name;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.productionDelay = productionDelay;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the machine.
         *
         * @return The machine name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the purchase cost of the machine.
         *
         * @return The machine cost
         */
        public int getCost() {
            return cost;
        }

        /**
         * Gets the resale value of the machine.
         *
         * @return The machine resale price
         */
        public int getResalePrice() {
            return resalePrice;
        }

        /**
         * Gets the time in minutes required to produce an item.
         *
         * @return The machine production delay
         */
        public int getProductionDelay() {
            return productionDelay;
        }

        /**
         * Gets the texture ID of the machine.
         *
         * @return The machine texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the machine.
         *
         * @return The machine name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of containers in the farm system.
     * Each container has properties such as name, size, persistence behavior,
     * cost, and texture ID.
     */
    public enum Containers{
        CHEST("Chest", 9, false, true, 100, 60, 3),
        TRASH("Trash", 3, false, false, 50, 20, 2);

        /**
         * The display name of the container
         */
        private final String name;

        /**
         * The purchase cost of the container
         */
        private final int cost;
        private final int resalePrice;

        /**
         * The storage capacity of the container
         */
        private final int size;

        /**
         * Flag indicating if contents are preserved when container is destroyed
         */
        private final boolean keepOnDestroy;

        /**
         * Flag indicating if contents are preserved when container is closed
         */
        private final boolean keepOnClose;

        /**
         * The ID of the texture used to represent the container
         */
        private final int textureId;

        /**
         * Constructor for Containers enum.
         *
         * @param name          The display name of the container
         * @param size          The storage capacity of the container
         * @param keepOnDestroy Flag indicating if contents are preserved when container is destroyed
         * @param keepOnClose   Flag indicating if contents are preserved when container is closed
         * @param cost          The purchase cost of the container
         * @param textureId     The ID of the texture for the container
         */
        Containers(String name, int size, boolean keepOnDestroy, boolean keepOnClose, int cost, int resalePrice, int textureId) {
            this.name = name;
            this.size = size;
            this.keepOnDestroy = keepOnDestroy;
            this.keepOnClose = keepOnClose;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the container.
         *
         * @return The container name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the storage capacity of the container.
         *
         * @return The container size
         */
        public int getSize() {
            return size;
        }

        /**
         * Checks if contents are preserved when container is destroyed.
         *
         * @return true if contents are preserved, false otherwise
         */
        public boolean isKeepOnDestroy() {
            return keepOnDestroy;
        }

        /**
         * Checks if contents are preserved when container is closed.
         *
         * @return true if contents are preserved, false otherwise
         */
        public boolean isKeepOnClose() {
            return keepOnClose;
        }

        /**
         * Gets the purchase cost of the container.
         *
         * @return The container cost
         */
        public int getCost() {
            return cost;
        }
        /**
         * Gets the resale price
         * @return the price resale
         */
        public int getResalePrice() {
            return resalePrice;
        }
        /**
         * Gets the texture ID of the container.
         *
         * @return The container texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the container.
         *
         * @return The container name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of available seeds in the farm system.
     * Represents the different types of seeds that can be planted,
     * with their display name, cost, and texture ID.
     */
    public enum Seed {
        WHEAT_SEED("Wheat_Seed", 5, 2, 0),
        TOMATO_SEED("Tomato_Seed", 10, 5, 3),
        CORN_SEED("Corn_Seed", 12, 7, 2),
        POTATO_SEED("Potato_Seed", 15, 9, 1),
        CABBAGE_SEED("CABBAGE_Seed", 20, 13, 4),
        SUNFLOWER_SEED("Sunflower_Seed", 25, 17, 5);
        
        /**
         * The display name of the seed
         */
        private final String name;

        /**
         * The purchase cost of the seed
         */
        private final int cost;

        private final int resalePrice;

        /**
         * The ID of the texture used to represent the seed
         */
        private final int textureId;

        /**
         * Constructor for Seed enum.
         *
         * @param name      The display name of the seed
         * @param cost      The purchase cost of the seed
         * @param textureId The ID of the texture for the seed
         */
        Seed(String name, int cost, int resalePrice, int textureId) {
            this.name = name;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the seed.
         *
         * @return The seed name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the purchase cost of the seed.
         *
         * @return The seed cost
         */
        public int getCost() {
            return cost;
        }

        /**
         * Gets the resale price of the seed
         *
         * @return the seed resale price
         */
        public int getResalePrice() {
            return resalePrice;
        }

        /**
         * Gets the texture ID of the seed
         *
         * @return The seed texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the seed.
         *
         * @return The seed name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of culture products in the farm system.
     * Each product has a name, cost, and texture ID.
     */
    public enum CultureProductType {
        WHEAT("Wheat", 5, 8, 6),
        POTATO("Potato", 17, 22, 7),
        CORN("Corn", 25, 30, 8),
        TOMATO("Tomato", 35, 41, 9),
        CABBAGE("CABBAGE", 55, 67, 10),
        SUNFLOWER("Sunflower", 99, 142, 11);
        
        private final String name;

        /**
         * The cost/value of the culture product
         */
        private final int cost;

        private final int resalePrice;

        /**
         * The ID of the texture used to represent the culture product
         */
        private final int textureId;

        /**
         * Constructor for CultureProductType enum.
         *
         * @param name      The display name of the culture product
         * @param cost      The cost/value of the culture product
         * @param textureId The ID of the texture for the culture product
         */
        CultureProductType(String name, int cost, int resalePrice, int textureId) {
            this.name = name;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the culture product.
         *
         * @return The product name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the cost/value of the culture product.
         *
         * @return The product cost
         */
        public int getCost() {
            return cost;
        }


        public int getResalePrice() {
            return resalePrice;
        }

        /**
         * Gets the texture ID of the culture product.
         *
         * @return The product texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the culture product.
         *
         * @return The product name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of culture types in the farm system.
     * Each culture type is linked to a specific seed and product type,
     * and has properties such as growth time and texture ID.
     */
    public enum CultureType {
        CULTURE_WHEAT(Seed.WHEAT_SEED, CultureProductType.WHEAT, 10, 0),
        CULTURE_POTATO(Seed.POTATO_SEED, CultureProductType.POTATO, 20, 1),
        CULTURE_CORN(Seed.CORN_SEED, CultureProductType.CORN, 30, 2),
        CULTURE_TOMATO(Seed.TOMATO_SEED, CultureProductType.TOMATO, 45, 3),
        CULTURE_CABBAGE(Seed.CABBAGE_SEED, CultureProductType.CABBAGE, 60, 4),
        CULTURE_SUNFLOWER(Seed.SUNFLOWER_SEED, CultureProductType.SUNFLOWER, 110, 5);
        
        private final Seed seedType;

        /**
         * The product type produced by this culture
         */
        private final CultureProductType productType;

        /**
         * The time in minutes required for the culture to grow to maturity
         */
        private final int growthTime;

        /**
         * The ID of the texture used to represent the culture
         */
        private final int textureId;

        /**
         * Constructor for CultureType enum.
         *
         * @param seedType    The seed type used to plant this culture
         * @param productType The product type produced by this culture
         * @param growthTime  The time in minutes required for the culture to grow to maturity
         * @param textureId   The ID of the texture for the culture
         */
        CultureType(Seed seedType, CultureProductType productType, int growthTime, int textureId) {
            this.seedType = seedType;
            this.productType = productType;
            this.growthTime = growthTime;
            this.textureId = textureId;
        }

        /**
         * Gets the seed type used to plant this culture.
         *
         * @return The seed type
         */
        public Seed getSeedType() {
            return seedType;
        }

        /**
         * Gets the product type produced by this culture.
         *
         * @return The product type
         */
        public CultureProductType getProductType() {
            return productType;
        }

        /**
         * Gets the time in minutes required for the culture to grow to maturity.
         *
         * @return The growth time
         */
        public int getGrowthTime() {
            return growthTime;
        }

        /**
         * Gets the texture ID of the culture.
         *
         * @return The culture texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the CultureType enum corresponding to the given seed type.
         *
         * @param seed The seed type to find the culture type for
         * @return The corresponding culture type, or null if not found
         */
        public static CultureType fromSeed(Seed seed) {
            for (CultureType cultureType : values()) {
                if (cultureType.getSeedType() == seed) {
                    return cultureType;
                }
            }
            return null;
        }
    }

    /**
     * Enumeration of tool types in the farm system.
     * Each tool has a name, cost, and texture ID.
     */
    public enum ToolType{
        PICKAXE("Pickaxe", 15, 7, 1),
        HOE("Hoe", 15, 7, 0),
        AXE("Axe", 15, 7, 2),
        WATERING_CAN("Watering Can", 15, 7, 3);

        /**
         * The display name of the tool
         */
        private final String name;

        /**
         * The purchase cost of the tool
         */
        private final int cost;
        private final int resalePrice;
        private final int textureId;

        /**
         * Constructor for ToolType enum.
         *
         * @param name      The display name of the tool
         * @param cost      The purchase cost of the tool
         * @param textureId The ID of the texture for the tool
         */
        ToolType(String name, int cost, int resalePrice, int textureId) {
            this.name = name;
            this.cost = cost;
            this.resalePrice = resalePrice;
            this.textureId = textureId;
        }

        /**
         * Gets the display name of the tool.
         *
         * @return The tool name
         */
        public String getName() {
            return name;
        }

        /**
         * Gets the purchase cost of the tool.
         *
         * @return The tool cost
         */
        public int getCost() {
            return cost;
        }

        public int getResalePrice() {
            return resalePrice;
        }

        /**
         * Gets the texture ID of the tool.
         *
         * @return The tool texture ID
         */
        public int getTextureId() {
            return textureId;
        }

        /**
         * Returns the string representation of the tool.
         *
         * @return The tool name
         */
        public String toString() {
            return name;
        }
    }

    /**
     * Enumeration of recipe types in the farm system.
     * Each recipe defines an input, output, the machine required for processing,
     * and the quantity of output produced.
     */
    public enum ReceipeType {
        FLOUR(CultureProductType.WHEAT, MachineProductType.FLOUR, Machines.WINDMILL, 1),
        BREAD(MachineProductType.FLOUR, MachineProductType.BREAD, Machines.OVEN, 1),
        CHEESE(AnimalProductType.MILK, MachineProductType.CHEESE, Machines.MILKPROCESSOR, 1),
        COW_FOOD(CultureProductType.CABBAGE, MachineProductType.COW_FOOD, Machines.WINDMILL, 2),
        PIG_FOOD(CultureProductType.POTATO, MachineProductType.PIG_FOOD, Machines.WINDMILL, 2),
        CHICKEN_FOOD(CultureProductType.CORN, MachineProductType.CHICKEN_FOOD, Machines.WINDMILL, 2),
        SHEEP_FOOD(CultureProductType.TOMATO, MachineProductType.SHEEP_FOOD, Machines.WINDMILL, 2);

        /**
         * The input item required for the recipe
         */
        private final Object input;

        /**
         * The output item produced by the recipe
         */
        private final Object output;

        /**
         * The machine required to process the recipe
         */
        private final Machines machine;

        /**
         * The quantity of output items produced
         */
        private final int quantity;

        /**
         * Constructor for ReceipeType enum.
         *
         * @param input    The input item required for the recipe
         * @param output   The output item produced by the recipe
         * @param machine  The machine required to process the recipe
         * @param quantity The quantity of output items produced
         */
        ReceipeType(Object input, Object output, Machines machine, int quantity) {
            this.input = input;
            this.output = output;
            this.machine = machine;
            this.quantity = quantity;
        }

        /**
         * Gets a recipe by its input item name.
         *
         * @param name The name of the input item
         * @return The corresponding recipe, or null if not found
         */
        public static ReceipeType getByName(String name) {
            return Arrays.stream(values())
                    .filter(r -> r.getInput().toString().equals(name))
                    .findFirst()
                    .orElse(null);
        }

        /**
         * Gets the input item required for the recipe.
         *
         * @return The input item
         */
        public Object getInput() {
            return input;
        }

        /**
         * Gets the output item produced by the recipe.
         *
         * @return The output item
         */
        public Object getOutput() {
            return output;
        }

        /**
         * Gets the machine required to process the recipe.
         *
         * @return The required machine
         */
        public Machines getMachine() {
            return machine;
        }

        /**
         * Gets the quantity of output items produced.
         *
         * @return The output quantity
         */
        public int getQuantity() {
            return quantity;
        }
    }
}