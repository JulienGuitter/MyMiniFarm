package fr.eseo.e3a.myminifarm.model;

import fr.eseo.e3a.myminifarm.model.farmElement.Item;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents an inventory system that can store a fixed number of items.
 * <p>
 * The Inventory class provides methods for adding, removing, and managing items.
 * It tracks changes to the inventory contents and supports operations like
 * retrieving items by index, finding items by name, and manipulating item quantities.
 * </p>
 */
public class Inventory {
    /** The maximum number of item slots in this inventory. */
    private int size;

    /** The collection of items stored in this inventory. Null values represent empty slots. */
    private ArrayList<Item> items;

    /** Flag indicating if the inventory contents have changed since last access. */
    private boolean hasChanged;

    /**
     * Creates a new inventory with the specified size.
     * All slots are initially empty (null).
     *
     * @param size The maximum number of item slots in the inventory
     */
    public Inventory(int size) {
        this.size = size;
        items = new ArrayList<>(Collections.nCopies(size, null));
            hasChanged = true;
    }

    /**
     * Adds an item to the inventory.
     * If an item with the same name already exists, its quantity is increased.
     * Otherwise, the item is placed in the first empty slot.
     *
     * @param item The item to add to the inventory
     * @return true if the item was added successfully, false if the inventory is full
     */
    public boolean add(Item item) {
        int index = indexOfByName(item.getName());
        if (index != -1) {
            items.get(index).addQuantity(1);
                hasChanged = true;
            } else {
            int emptyIndex = items.indexOf(null);
            if (emptyIndex != -1) {
                items.set(emptyIndex, item);
        hasChanged = true;
        } else {
                return false; // Inventory is full
        }
    }
        return true;
    }

    /**
     * Adds an item to a specific slot in the inventory.
     * Any existing item in that slot will be replaced.
     *
     * @param item The item to add to the inventory
     * @param index The slot index where the item should be placed
     */
    public void add(Item item, int index) {
        if (index >= 0 && index < size) {
            items.set(index, item);
    }
        hasChanged = true;
    }

    /**
     * Increases the quantity of an item at the specified index.
     *
     * @param index The index of the item to modify
     * @param quantity The amount to add to the item's quantity
     */
    public void addQuantity(int index, int quantity) {
        if (index >= 0 && index < size) {
            items.get(index).addQuantity(quantity);
            }
        hasChanged = true;
        }

    /**
     * Decreases the quantity of an item at the specified index.
     * If the quantity reaches zero or below, the item is removed from the inventory.
     *
     * @param index The index of the item to modify
     * @param quantity The amount to subtract from the item's quantity
     */
    public void deductQuantity(int index, int quantity) {
        if (index >= 0 && index < size) {
            boolean isEmpty = items.get(index).deductQuantity(quantity);
            if (isEmpty) {
                items.set(index, null);
    }
            hasChanged = true;
}
    }

    /**
     * Finds the index of the first item with the specified name.
     *
     * @param name The name of the item to find
     * @return The index of the item if found, -1 otherwise
     */
    public int indexOfByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null && items.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1; // si non trouvé
    }

    /**
     * Removes the specified item from the inventory.
     * If the item exists in the inventory, its quantity is reduced by the quantity of the provided item.
     * If the quantity reaches zero, the item is removed completely.
     *
     * @param item The item to remove from the inventory
     */
    public boolean remove(Item item) {
        if (items.contains(item)) {
            boolean isEmpty = items.get(items.indexOf(item)).deductQuantity(item.getQuantity());
            if (isEmpty) {
                items.set(items.indexOf(item), null);
            }
            hasChanged = true;
            return true;
        }
        return false;
    }

    /**
     * Removes the item at the specified index from the inventory.
     *
     * @param index The index of the item to remove
     */
    public void remove(int index) {
        if (index >= 0 && index < items.size()) {
            items.set(index, null);
            hasChanged = true;
        }
    }

    public void clear(){
        for (int i = 0; i < items.size(); i++) {
            items.set(i, null);
        }
        hasChanged = true;
    }

    /**
     * Gets the item at the specified index.
     *
     * @param index The index of the item to retrieve
     * @return The item at the specified index, or null if the index is invalid or the slot is empty
     */
    public Item getItem(int index){
        if(index >= 0 && index < items.size()){
            return items.get(index);
        } else {
            return null;
        }
    }

    /**
     * Checks if the inventory has changed since the last time this flag was reset.
     *
     * @return true if the inventory has changed, false otherwise
     */
    public boolean hasChanged() {
        return hasChanged;
    }

    /**
     * Sets the changed status of the inventory.
     * This is typically used to mark the inventory as unchanged after handling a change event.
     *
     * @param hasChanged The new changed status
     */
    public void setChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }

    /**
     * Gets the maximum capacity of this inventory.
     *
     * @return The number of item slots in the inventory
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the inventory contains at least one item.
     *
     * @return true if the inventory contains at least one item, false if it is completely empty
     */
    public boolean containItem() {
        for (Item item : items) {
            if (item != null) {
                return true;
            }
        }
        return false;
    }
}
