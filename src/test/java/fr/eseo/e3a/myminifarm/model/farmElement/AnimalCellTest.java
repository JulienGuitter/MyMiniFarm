package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.Animals;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AnimalCell class
 * 
 * These tests verify the functionality of the AnimalCell including:
 * - Initialization
 * - Adding animals
 * - Removing animals
 * - Capacity management
 * - State checking
 */
public class AnimalCellTest {
    
    private AnimalCell cell;
    private Animal chicken1;
    private Animal chicken2;
    private Animal cow1;
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        cell = new AnimalCell();
        chicken1 = new Animal(Animals.CHICKEN);
        chicken2 = new Animal(Animals.CHICKEN);
        cow1 = new Animal(Animals.COW);
    }
    
    /**
     * Test proper initialization of AnimalCell
     */
    @Test
    public void testInitialization() {
        assertEquals("Animal Cell", cell.getName());
        assertEquals(0, cell.getCost());
        assertEquals(ItemType.STRUCTURE, cell.getItemType());
        assertEquals(2, cell.getTextureId());
        assertEquals(0, cell.getAnimalCount());
        assertTrue(cell.getAnimals().isEmpty());
        assertFalse(cell.isOccupied());
        assertTrue(cell.isFree());
    }
    
    /**
     * Test adding animals to the cell
     */
    @Test
    public void testAddAnimal() {
        assertTrue(cell.addAnimal(chicken1));
        assertEquals(1, cell.getAnimalCount());
        
        assertTrue(cell.addAnimal(cow1));
        assertEquals(2, cell.getAnimalCount());
        
        List<Animal> animals = cell.getAnimals();
        assertEquals(2, animals.size());
        assertTrue(animals.contains(chicken1));
        assertTrue(animals.contains(cow1));
    }
    
    /**
     * Test removing animals from the cell
     */
    @Test
    public void testRemoveAnimal() {
        cell.addAnimal(chicken1);
        cell.addAnimal(cow1);
        
        assertTrue(cell.removeAnimal(chicken1));
        assertEquals(1, cell.getAnimalCount());
        assertFalse(cell.getAnimals().contains(chicken1));
        assertTrue(cell.getAnimals().contains(cow1));
        
        // Try to remove an animal that's not in the cell
        assertFalse(cell.removeAnimal(chicken2));
        assertEquals(1, cell.getAnimalCount());
    }
    
    /**
     * Test clearing all animals from the cell
     */
    @Test
    public void testClear() {
        cell.addAnimal(chicken1);
        cell.addAnimal(cow1);
        
        cell.clear();
        assertEquals(0, cell.getAnimalCount());
        assertTrue(cell.getAnimals().isEmpty());
    }
    
    /**
     * Test cell capacity limits
     */
    @Test
    public void testCapacityLimit() {
        // Add maximum number of animals
        for (int i = 0; i < 5; i++) {
            assertTrue(cell.addAnimal(new Animal(Animals.CHICKEN)));
            assertEquals(i + 1, cell.getAnimalCount());
        }
        
        // Cell should now be full
        assertTrue(cell.isOccupied());
        assertFalse(cell.isFree());
        
        // Try to add one more animal
        assertFalse(cell.addAnimal(cow1));
        assertEquals(5, cell.getAnimalCount());
    }
    
    /**
     * Test that the returned animals list is a defensive copy
     */
    @Test
    public void testGetAnimalsReturnsDefensiveCopy() {
        cell.addAnimal(chicken1);
        
        List<Animal> animals = cell.getAnimals();
        assertEquals(1, animals.size());
        
        // Try to modify the returned list
        animals.add(cow1);
        
        // The original list in the cell should be unchanged
        assertEquals(1, cell.getAnimalCount());
        assertFalse(cell.getAnimals().contains(cow1));
    }
    
    /**
     * Test state transitions when adding and removing animals
     */
    @Test
    public void testStateTransitions() {
        // Initially free
        assertTrue(cell.isFree());
        assertFalse(cell.isOccupied());
        
        // Add 5 animals to fill it
        for (int i = 0; i < 5; i++) {
            cell.addAnimal(new Animal(Animals.CHICKEN));
        }
        
        // Should now be occupied
        assertTrue(cell.isOccupied());
        assertFalse(cell.isFree());
        
        // Remove one animal
        cell.removeAnimal(cell.getAnimals().get(0));
        
        // Should now be free again
        assertFalse(cell.isOccupied());
        assertTrue(cell.isFree());
    }
}