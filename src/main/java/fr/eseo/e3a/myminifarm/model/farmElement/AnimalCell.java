package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an animal enclosure or cell in the farm.
 * <p>
 * An AnimalCell is a structure that can contain a limited number of animals.
 * It provides functionality to add, remove, and manage animals within the cell.
 * Each cell has a maximum capacity defined by MAX_ANIMALS.
 * </p>
 *
 * @see Animal
 * @see Item
 */
public class AnimalCell extends Item {

    /**
     * The maximum number of animals that can be housed in one cell.
     * This limit cannot be exceeded when adding new animals.
     */
    private static final int MAX_ANIMALS = 5;

    /**
     * The list of animals currently housed in this cell.
     * This collection is managed internally to maintain the cell's capacity constraints.
     */
    private List<Animal> animals;

    /**
     * Constructs a new animal cell.
     * <p>
     * Initializes the cell as a structure item with predefined cost and texture ID.
     * Creates an empty list to store animals.
     * </p>
     */
    public AnimalCell() {
        super(ItemType.STRUCTURE, "Animal Cell", 0, 0, 2); // Exemple de coût et textureId
        this.animals = new ArrayList<>();
    }

    /**
     * Checks if the cell is at maximum capacity.
     * <p>
     * A cell is considered occupied when it contains the maximum number of animals.
     * </p>
     *
     * @return {@code true} if the cell contains the maximum number of animals, {@code false} otherwise
     */
    public boolean isOccupied() {
        return animals.size() >= MAX_ANIMALS;
    }

    /**
     * Checks if the cell has space available for more animals.
     * <p>
     * A cell is considered free when it contains fewer than the maximum number of animals.
     * </p>
     *
     * @return {@code true} if the cell can accept more animals, {@code false} if it's at maximum capacity
     */
    public boolean isFree() {
        return animals.size() < MAX_ANIMALS;
    }

    /**
     * Adds a new animal to the cell if space is available.
     * <p>
     * If the cell is already at maximum capacity, the animal will not be added.
     * </p>
     *
     * @param animal The animal to add to the cell
     * @return {@code true} if the animal was successfully added, {@code false} if the cell is full
     */
    public boolean addAnimal(Animal animal) {
        if (isOccupied()) {
            System.out.println("Cette cellule est déjà pleine (5 animaux max).");
            return false;
        }
        animals.add(animal);
        System.out.println(animal.getName() + " a été ajouté à la cellule.");
        return true;
    }

    /**
     * Removes a specific animal from the cell.
     * <p>
     * If the animal is not found in the cell, the operation will fail.
     * </p>
     *
     * @param animal The animal to remove from the cell
     * @return {@code true} if the animal was successfully removed, {@code false} if the animal was not in the cell
     */
    public boolean removeAnimal(Animal animal) {
        if (animals.remove(animal)) {
            System.out.println(animal.getName() + " a été retiré de la cellule.");
            return true;
        } else {
            System.out.println("L'animal n'était pas dans cette cellule.");
            return false;
        }
    }

    /**
     * Removes all animals from the cell.
     * <p>
     * This method completely empties the cell, removing all contained animals.
     * </p>
     */
    public void clear() {
        animals.clear();
        System.out.println("Tous les animaux ont été retirés de la cellule.");
    }

    /**
     * Returns a copy of the list of animals in this cell.
     * <p>
     * A new list is created to prevent external modification of the internal collection.
     * </p>
     *
     * @return A new list containing all animals currently in the cell
     */
    public List<Animal> getAnimals() {
        return new ArrayList<>(animals); // To prevent external modification
    }

    /**
     * Returns the current number of animals in the cell.
     * <p>
     * This count will be between 0 and MAX_ANIMALS.
     * </p>
     *
     * @return The number of animals currently in the cell
     */
    public int getAnimalCount() {
        return animals.size();
    }
}
