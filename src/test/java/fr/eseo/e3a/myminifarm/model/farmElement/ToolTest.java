package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ToolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Tool class
 * 
 * These tests verify the functionality of the Tool class including:
 * - Proper initialization with different tool types
 * - Getter methods
 * - String representation
 */
public class ToolTest {

    private Tool wateringCan;
    private Tool pickaxe;
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        wateringCan = new Tool(ToolType.WATERING_CAN);
        pickaxe = new Tool(ToolType.PICKAXE);
    }
    
    /**
     * Test initialization of a Tool with specific ToolType
     */
    @Test
    public void testInitialization() {
        // Test wateringCan
        assertEquals(ToolType.WATERING_CAN.getName(), wateringCan.getName());
        assertEquals(ToolType.WATERING_CAN.getCost(), wateringCan.getCost());
        assertEquals(ItemType.TOOL, wateringCan.getItemType());
        assertEquals(ToolType.WATERING_CAN.getTextureId(), wateringCan.getTextureId());
        assertEquals(ToolType.WATERING_CAN, wateringCan.getTypeTools());
        
        // Test PICKAXE
        assertEquals(ToolType.PICKAXE.getName(), pickaxe.getName());
        assertEquals(ToolType.PICKAXE.getCost(), pickaxe.getCost());
        assertEquals(ItemType.TOOL, pickaxe.getItemType());
        assertEquals(ToolType.PICKAXE.getTextureId(), pickaxe.getTextureId());
        assertEquals(ToolType.PICKAXE, pickaxe.getTypeTools());
    }
    
    /**
     * Test initialization with all possible ToolTypes
     */
    @ParameterizedTest
    @EnumSource(ToolType.class)
    public void testInitializationWithAllToolTypes(ToolType toolType) {
        Tool tool = new Tool(toolType);
        
        assertEquals(toolType.getName(), tool.getName());
        assertEquals(toolType.getCost(), tool.getCost());
        assertEquals(ItemType.TOOL, tool.getItemType());
        assertEquals(toolType.getTextureId(), tool.getTextureId());
        assertEquals(toolType, tool.getTypeTools());
    }
    
    /**
     * Test getTypeTools method
     */
    @Test
    public void testGetTypeTools() {
        assertEquals(ToolType.WATERING_CAN, wateringCan.getTypeTools());
        assertEquals(ToolType.PICKAXE, pickaxe.getTypeTools());
    }
    
    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        String expectedWateringCan = "Tool{type=" + ToolType.WATERING_CAN + '}';
        String expectedPICKAXE = "Tool{type=" + ToolType.PICKAXE + '}';
        
        assertEquals(expectedWateringCan, wateringCan.toString());
        assertEquals(expectedPICKAXE, pickaxe.toString());
    }
    
    /**
     * Test that different tools have different properties
     */
    @Test
    public void testDifferentTools() {
        assertNotEquals(wateringCan.getName(), pickaxe.getName());
        assertNotEquals(wateringCan.getTypeTools(), pickaxe.getTypeTools());
        
        // Different instances with same type should have equal properties
        Tool anotherWateringCan = new Tool(ToolType.WATERING_CAN);
        assertEquals(wateringCan.getName(), anotherWateringCan.getName());
        assertEquals(wateringCan.getTypeTools(), anotherWateringCan.getTypeTools());
        assertEquals(wateringCan.getCost(), anotherWateringCan.getCost());
        assertEquals(wateringCan.getTextureId(), anotherWateringCan.getTextureId());
    }
    
    /**
     * Test quantity-related methods inherited from Item
     */
    @Test
    public void testQuantityMethods() {
        assertEquals(1, wateringCan.getQuantity()); // Default quantity should be 1
        
        wateringCan.addQuantity(3);
        assertEquals(4, wateringCan.getQuantity());
        
        wateringCan.setQuantity(10);
        assertEquals(10, wateringCan.getQuantity());
        
        assertFalse(wateringCan.deductQuantity(5)); // Should return false as quantity > 0
        assertEquals(5, wateringCan.getQuantity());
        
        assertFalse(wateringCan.deductQuantity(4)); // Should return false as quantity > 0
        assertEquals(1, wateringCan.getQuantity());
        
        assertTrue(wateringCan.deductQuantity(1)); // Should return true as quantity is now 0
        assertEquals(0, wateringCan.getQuantity());
    }
}