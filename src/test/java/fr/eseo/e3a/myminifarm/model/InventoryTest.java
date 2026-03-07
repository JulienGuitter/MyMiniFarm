package fr.eseo.e3a.myminifarm.model;

import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Inventory
 *
 * This class tests all functionality of the Inventory class including:
 * - Adding items
 * - Removing items
 * - Managing quantities
 * - Checking inventory state
 */
public class InventoryTest {

    private Inventory inventory;
    private TestItem testItem1;
    private TestItem testItem2;
    
    /**
     * Concrete implementation of the abstract Item class for testing
     */
    private static class TestItem extends Item {
        public TestItem(String name, int quantity) {
            super(ItemType.TOOL, name, 10, 10, 1);
            setQuantity(quantity);
        }
    }
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        inventory = new Inventory(5);
        testItem1 = new TestItem("TestItem1", 1);
        testItem2 = new TestItem("TestItem2", 1);
    }
    
    /**
     * Test adding an item to the inventory
     */
    @Test
    public void testAddItem() {
        assertTrue(inventory.add(testItem1));
        assertEquals(testItem1, inventory.getItem(0));
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test adding an item that already exists in the inventory
     */
    @Test
    public void testAddExistingItem() {
        inventory.add(testItem1);
        assertTrue(inventory.add(new TestItem("TestItem1", 1)));
        assertEquals(2, inventory.getItem(0).getQuantity());
    }

    /**
     * Test adding an item to a full inventory
     */
    @Test
    public void testAddToFullInventory() {
        // Fill the inventory
        for (int i = 0; i < 5; i++) {
            inventory.add(new TestItem("Item" + i, 1));
        }
        
        // Try to add one more item
        assertFalse(inventory.add(new TestItem("ExtraItem", 1)));
    }

    /**
     * Test adding an item at a specific index
     */
    @Test
    public void testAddAtIndex() {
        inventory.add(testItem1, 2);
        assertEquals(testItem1, inventory.getItem(2));
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test adding quantity to an item
     */
    @Test
    public void testAddQuantity() {
        inventory.add(testItem1, 0);
        inventory.addQuantity(0, 5);
        assertEquals(6, inventory.getItem(0).getQuantity());
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test deducting quantity from an item
     */
    @Test
    public void testDeductQuantity() {
        testItem1 = new TestItem("TestItem1", 5);
        inventory.add(testItem1, 0);
        
        inventory.deductQuantity(0, 3);
        assertEquals(2, inventory.getItem(0).getQuantity());
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test deducting all quantity from an item
     */
    @Test
    public void testDeductAllQuantity() {
        testItem1 = new TestItem("TestItem1", 5);
        inventory.add(testItem1, 0);
        
        inventory.deductQuantity(0, 5);
        assertNull(inventory.getItem(0));
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test finding an item by name
     */
    @Test
    public void testIndexOfByName() {
        inventory.add(testItem1, 2);
        assertEquals(2, inventory.indexOfByName("TestItem1"));
        assertEquals(-1, inventory.indexOfByName("NonExistentItem"));
    }

    /**
     * Test removing an item by reference
     */
    @Test
    public void testRemoveItem() {
        inventory.add(testItem1, 0);
        inventory.remove(testItem1);
        assertNull(inventory.getItem(0));
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test removing an item by index
     */
    @Test
    public void testRemoveByIndex() {
        inventory.add(testItem1, 0);
        inventory.remove(0);
        assertNull(inventory.getItem(0));
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test retrieving an item by index
     */
    @Test
    public void testGetItem() {
        inventory.add(testItem1, 0);
        assertEquals(testItem1, inventory.getItem(0));
        assertNull(inventory.getItem(10)); // Out of bounds
    }

    /**
     * Test changing the state of inventory
     */
    @Test
    public void testChangeState() {
        inventory.setChanged(false);
        assertFalse(inventory.hasChanged());
        
        inventory.add(testItem1);
        assertTrue(inventory.hasChanged());
    }

    /**
     * Test checking if inventory contains any items
     */
    @Test
    public void testContainItem() {
        assertFalse(inventory.containItem());
        
        inventory.add(testItem1);
        assertTrue(inventory.containItem());
        
        inventory.remove(0);
        assertFalse(inventory.containItem());
    }
    
    /**
     * Test getting the size of the inventory
     */
    @Test
    public void testGetSize() {
        assertEquals(5, inventory.getSize());
    }
    
    /**
     * Test passing a null item to add method
     */
    @Test
    public void testAddNullItem() {
        // This would typically throw an exception but your implementation might handle it
        // differently - adjust assertion as needed for your implementation
        assertThrows(NullPointerException.class, () -> inventory.add(null));
    }
    
    /**
     * Test add and remove operations with edge case quantities
     */
    @Test
    public void testQuantityEdgeCases() {
        TestItem largeQuantityItem = new TestItem("BulkItem", 100);
        inventory.add(largeQuantityItem, 0);
        assertEquals(100, inventory.getItem(0).getQuantity());
        
        inventory.deductQuantity(0, 50);
        assertEquals(50, inventory.getItem(0).getQuantity());
        
        // Add more than we have and check if it updates correctly
        inventory.addQuantity(0, 150);
        assertEquals(200, inventory.getItem(0).getQuantity());
    }
}