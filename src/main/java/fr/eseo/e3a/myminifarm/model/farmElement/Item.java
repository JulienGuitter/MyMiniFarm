package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import fr.eseo.e3a.myminifarm.utils.GameTime;

/**
 * Represents a generic item in the game.
 * <p>
 * Items have a name, cost, type, texture ID, and quantity. This class is abstract and meant to be
 * extended by more specific item types, such as animals, crops, products, or structures.
 * It provides basic functionality common to all items in the farm system.
 * </p>
 */
public abstract class Item implements Cloneable {

    /**
     * The name of the item.
     */
    private final String name;

    /** The resale price of the item in euros. */
    private int resalePrice;

    /** The cost of the item in euros. */
    private int cost;

    /**
     * The type of the item from the ItemType enumeration.
     */
    private final ItemType type;

    /**
     * The ID of the texture used to represent this item in the game.
     */
    private final int textureId;

    /**
     * The quantity of this item (for stackable items).
     */
    private int quantity;

    /**
     * Constructs an item with the given type, name, cost, and texture ID.
     * The quantity is initialized to 1.
     *
     * @param type      The type of the item from the ItemType enumeration.
     * @param name      The name of the item.
     * @param cost      The cost of the item in euros.
     * @param textureId The ID of the texture used to represent this item.
     */
    public Item(ItemType type, String name, int cost, int resalePrice, int textureId) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.resalePrice = resalePrice;
        this.textureId = textureId;
        this.quantity = 1;
    }

    /**
     * Returns the name of the item.
     *
     * @return The item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the cost of the item.
     *
     * @return The cost in euros.
     */
    public int getCost() {
        return cost;
    }

    public int getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(int resalePrice) {
        this.resalePrice = resalePrice;
    }

    /**
     * Returns the type of the item.
     *
     * @return The item type from the ItemType enumeration.
     */
    public ItemType getItemType() {
        return type;
    }

    /**
     * Returns the texture ID of the item.
     *
     * @return The ID of the texture used to represent this item.
     */
    public int getTextureId() {
        return textureId;
    }

    /**
     * Returns the current quantity of this item.
     *
     * @return The quantity of this item.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this item to the specified value.
     *
     * @param quantity The new quantity value for this item.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Increases the quantity of this item by the specified amount.
     *
     * @param quantity The amount to add to the current quantity.
     */
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    /**
     * Decreases the quantity of this item by the specified amount.
     *
     * @param quantity The amount to subtract from the current quantity.
     * @return true if the quantity becomes zero or less after deduction, false otherwise.
     */
    public boolean deductQuantity(int quantity) {
        this.quantity -= quantity;
        return this.quantity <= 0;
    }

    /**
     * Returns a string representation of the item, including its name and resale price.
     *
     * @return A string representing the item.
     */
    @Override
    public String toString() {
        return name + " (Resale price: " + cost + "€)";
    }


    @Override
    public Item clone() {
        try {
            return (Item) super.clone(); // shallow copy
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
