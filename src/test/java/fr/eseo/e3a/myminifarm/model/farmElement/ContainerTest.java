package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.Inventory;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.Containers;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ToolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Container class
 * 
 * These tests verify the functionality of the Container class including:
 * - Proper initialization with different container types
 * - Inventory creation
 * - Getter methods
 */
public class ContainerTest {

    private Container smallChest;
    private Container largeChest;
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        smallChest = new Container(Containers.CHEST);
        largeChest = new Container(Containers.TRASH);
    }
    
    /**
     * Test initialization of a Container with specific type
     */
    @Test
    public void testInitialization() {
        // Test small chest
        assertEquals(Containers.CHEST.getName(), smallChest.getName());
        assertEquals(Containers.CHEST.getCost(), smallChest.getCost());
        assertEquals(ItemType.CONTAINER, smallChest.getItemType());
        assertEquals(Containers.CHEST.getTextureId(), smallChest.getTextureId());
        assertEquals(Containers.CHEST, smallChest.getTypeContainer());
        
        // Test large chest
        assertEquals(Containers.TRASH.getName(), largeChest.getName());
        assertEquals(Containers.TRASH.getCost(), largeChest.getCost());
        assertEquals(ItemType.CONTAINER, largeChest.getItemType());
        assertEquals(Containers.TRASH.getTextureId(), largeChest.getTextureId());
        assertEquals(Containers.TRASH, largeChest.getTypeContainer());
    }
    
    /**
     * Test initialization with all possible Container types
     */
    @ParameterizedTest
    @EnumSource(Containers.class)
    public void testInitializationWithAllContainerTypes(Containers containerType) {
        Container container = new Container(containerType);
        
        assertEquals(containerType.getName(), container.getName());
        assertEquals(containerType.getCost(), container.getCost());
        assertEquals(ItemType.CONTAINER, container.getItemType());
        assertEquals(containerType.getTextureId(), container.getTextureId());
        assertEquals(containerType, container.getTypeContainer());
    }
    
    /**
     * Test that inventory is properly created with correct size
     */
    @Test
    public void testInventoryCreation() {
        // Check inventory is not null
        assertNotNull(smallChest.getInventory());
        assertNotNull(largeChest.getInventory());
        
        // Check inventory has correct size
        assertEquals(Containers.CHEST.getSize(), smallChest.getInventory().getSize());
        assertEquals(Containers.TRASH.getSize(), largeChest.getInventory().getSize());
    }
    
    /**
     * Test getTypeContainer method
     */
    @Test
    public void testGetTypeContainer() {
        assertEquals(Containers.CHEST, smallChest.getTypeContainer());
        assertEquals(Containers.TRASH, largeChest.getTypeContainer());
    }
    
    /**
     * Test getInventory method
     */
    @Test
    public void testGetInventory() {
        Inventory smallChestInventory = smallChest.getInventory();
        Inventory largeChestInventory = largeChest.getInventory();
        
        assertNotNull(smallChestInventory);
        assertNotNull(largeChestInventory);
        
        // Verify these are different inventory instances
        assertNotSame(smallChestInventory, largeChestInventory);
    }
    
    /**
     * Test that different containers have different properties
     */
    @Test
    public void testDifferentContainers() {
        // Different types should have different properties
        if (!Containers.CHEST.equals(Containers.TRASH)) { // Guard in case enum values change
            assertNotEquals(smallChest.getName(), largeChest.getName());
            assertNotEquals(smallChest.getTypeContainer(), largeChest.getTypeContainer());
            assertNotEquals(smallChest.getInventory().getSize(), largeChest.getInventory().getSize());
        }
        
        // Different instances with same type should have equal properties but different inventories
        Container anotherSmallChest = new Container(Containers.CHEST);
        assertEquals(smallChest.getName(), anotherSmallChest.getName());
        assertEquals(smallChest.getTypeContainer(), anotherSmallChest.getTypeContainer());
        assertEquals(smallChest.getInventory().getSize(), anotherSmallChest.getInventory().getSize());
        assertNotSame(smallChest.getInventory(), anotherSmallChest.getInventory());
    }
    
    /**
     * Test inventory functionality by adding and removing items
     */
    @Test
    public void testInventoryFunctionality() {
        // Create a test item
        Item testItem = new Tool(ToolType.PICKAXE);
        
        // Add to inventory
        assertTrue(smallChest.getInventory().add(testItem));
        assertEquals(1, smallChest.getInventory().getItem(0).getQuantity());
        
        // Add another of the same item
        assertTrue(smallChest.getInventory().add(testItem));
        assertEquals(2, smallChest.getInventory().getItem(0).getQuantity());
        
        // Remove from inventory
        smallChest.getInventory().remove(0);
        assertNull(smallChest.getInventory().getItem(0));
        
        // Test that the inventory has the expected size
        int expectedSize = Containers.CHEST.getSize();
        assertEquals(expectedSize, smallChest.getInventory().getSize());
    }
    
    /**
     * Test quantity-related methods inherited from Item
     */
    @Test
    public void testQuantityMethods() {
        assertEquals(1, smallChest.getQuantity()); // Default quantity should be 1
        
        smallChest.addQuantity(2);
        assertEquals(3, smallChest.getQuantity());
        
        smallChest.setQuantity(5);
        assertEquals(5, smallChest.getQuantity());
        
        assertFalse(smallChest.deductQuantity(3)); // Should return false as quantity > 0
        assertEquals(2, smallChest.getQuantity());
        
        assertFalse(smallChest.deductQuantity(1)); // Should return false as quantity > 0
        assertEquals(1, smallChest.getQuantity());
        
        assertTrue(smallChest.deductQuantity(1)); // Should return true as quantity is now 0
        assertEquals(0, smallChest.getQuantity());
    }
}