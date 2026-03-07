package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.*;
import fr.eseo.e3a.myminifarm.utils.GameTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for Machine functionality.
 * <p>
 * This class tests all main functionality of the Machine class including
 * suitability checks, processing logic, and item production.
 * </p>
 */
public class MachineTest {

    private Machine milkProcessor;
    private Machine oven;
    private Machine smoker;
    private Machine loom;
    
    @Mock
    private GameLoop gameLoop;
    
    @Mock
    private GameTime gameTime;

    /**
     * Setup method executed before each test.
     * <p>
     * Initializes the machines and mocks necessary for testing.
     * </p>
     */
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        
        when(gameLoop.getGameTime()).thenReturn(gameTime);
        when(gameTime.clone()).thenReturn(gameTime);
        
        GameLoop.setInstance(gameLoop);

        // Create a mock UIController singleton using reflection
        mockUIControllerSingleton();

        milkProcessor = new Machine(Machines.MILKPROCESSOR);
        oven = new Machine(Machines.OVEN);
        smoker = new Machine(Machines.SMOKER);
        loom = new Machine(Machines.LOOM);
    }


    /**
     * Creates a mock UIController and sets it as the singleton instance using reflection.
     */
    private void mockUIControllerSingleton() throws Exception {
        // Create a mock UIController
        UIController mockUIController = mock(UIController.class);

        // Replace the singleton instance field with our mock
        Field instanceField = UIController.class.getDeclaredField("instance");
        instanceField.setAccessible(true);
        instanceField.set(null, mockUIController);

        // Configure mock behavior (No-op for setMachineInterfaceChanged)
        doNothing().when(mockUIController).setMachineInterfaceChanged(anyBoolean());
    }


    /**
     * Tests machine initialization.
     * <p>
     * Verifies that machines are properly initialized with correct properties.
     * </p>
     */
    @Test
    public void testMachineInitialization() {
        assertEquals(ItemType.MACHINE, milkProcessor.getItemType());
        assertEquals(Machines.MILKPROCESSOR.getName(), milkProcessor.getName());
        assertEquals(Machines.MILKPROCESSOR.getCost(), milkProcessor.getCost());
        assertEquals(Machines.MILKPROCESSOR.getTextureId(), milkProcessor.getTextureId());
        assertFalse(milkProcessor.isInUse());
        assertNotNull(milkProcessor.getInventory());
        assertEquals(2, milkProcessor.getInventory().getSize());
        assertEquals(Machines.MILKPROCESSOR, milkProcessor.getTypeMachine());
        assertNull(milkProcessor.getTimeRemaining());
    }

    /**
     * Tests suitability of materials for different machines.
     * <p>
     * Verifies that each machine correctly identifies appropriate input materials.
     * </p>
     */
    @Test
    public void testIsSuitable() {
        assertTrue(milkProcessor.isSuitable(AnimalProductType.MILK));
        assertFalse(milkProcessor.isSuitable(AnimalProductType.EGG));
        assertFalse(milkProcessor.isSuitable(AnimalProductType.BACON));
        assertFalse(milkProcessor.isSuitable(AnimalProductType.WOOL));
        
        assertTrue(oven.isSuitable(AnimalProductType.EGG));
        assertFalse(oven.isSuitable(AnimalProductType.MILK));
        
        assertTrue(smoker.isSuitable(AnimalProductType.BACON));
        assertFalse(smoker.isSuitable(AnimalProductType.EGG));
        
        assertTrue(loom.isSuitable(AnimalProductType.WOOL));
        assertFalse(loom.isSuitable(AnimalProductType.MILK));
    }

    /**
     * Tests if a machine is ready to process.
     * <p>
     * Verifies the readiness logic based on inventory state.
     * </p>
     */
    @Test
    public void testIsReadyToProcess() {
        assertFalse(milkProcessor.isReadyToProcess());
        
        // Add appropriate input item
        Item milk = new Animal(Animals.COW).produce();
        milkProcessor.getInventory().add(milk, 0);
        
        assertTrue(milkProcessor.isReadyToProcess());
    }

    /**
     * Tests the production update cycle.
     * <p>
     * Verifies that production starts correctly and processes items.
     * </p>
     */
    @Test
    public void testUpdateProduction() {
        // Add input item
        Item milk = new Animal(Animals.COW).produce();
        milkProcessor.getInventory().add(milk, 0);
        
        // Start production
        assertTrue(milkProcessor.updateProduction());
        assertTrue(milkProcessor.isInUse());
        
        // Production in progress
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(false);
        assertFalse(milkProcessor.updateProduction());
        
        // Production complete
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(true);
        assertTrue(milkProcessor.updateProduction());
        assertFalse(milkProcessor.isInUse());
        
        // Check that produced item is in the output slot
        Item cheese = milkProcessor.getInventory().getItem(1);
        assertNotNull(cheese);
        assertEquals(MachineProductType.CHEESE.getName(), cheese.getName());
    }

    /**
     * Tests production with multiple quantity input.
     * <p>
     * Verifies that quantity of input materials is properly handled.
     * </p>
     */
    @Test
    public void testProductionWithMultipleQuantity() {
        // Add input item with quantity 2
        Item milk = new Animal(Animals.COW).produce();
        milk.setQuantity(2);
        milkProcessor.getInventory().add(milk, 0);
        
        // Start and complete production
        assertTrue(milkProcessor.updateProduction());
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(true);
        assertTrue(milkProcessor.updateProduction());
        
        // Check that input item quantity decreased but still exists
        Item remainingMilk = milkProcessor.getInventory().getItem(0);
        assertNotNull(remainingMilk);
        assertEquals(1, remainingMilk.getQuantity());
        
        // Check produced item
        Item cheese = milkProcessor.getInventory().getItem(1);
        assertNotNull(cheese);
        assertEquals(MachineProductType.CHEESE.getName(), cheese.getName());
    }

    /**
     * Tests production with recipes that produce multiple outputs.
     * <p>
     * Verifies that recipes producing multiple items work correctly.
     * </p>
     */
    @Test
    public void testMultipleOutputProduction() {
        Machine windmill = new Machine(Machines.WINDMILL);
        
        // Add wheat for cow food production (yields 2 per recipe)
        Item carbage = new Culture(Seed.CABBAGE_SEED).harvest();
        windmill.getInventory().add(carbage, 0);
        
        // Start and complete production
        assertTrue(windmill.updateProduction());
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(true);
        assertTrue(windmill.updateProduction());
        
        // Check produced item quantity
        Item cowFood = windmill.getInventory().getItem(1);
        assertNotNull(cowFood);
        assertEquals(MachineProductType.COW_FOOD.getName(), cowFood.getName());
        assertEquals(2, cowFood.getQuantity()); // Should produce 2 units
    }

    /**
     * Tests stacking of output products.
     * <p>
     * Verifies that multiple production cycles correctly stack output.
     * </p>
     */
    @Test
    public void testOutputStacking() {
        // Add input items
        Item milk = new Animal(Animals.COW).produce();
        milk.setQuantity(2);
        milkProcessor.getInventory().add(milk, 0);
        
        // First production cycle
        assertTrue(milkProcessor.updateProduction());
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(true);
        assertTrue(milkProcessor.updateProduction());
        
        // Second production cycle
        assertTrue(milkProcessor.updateProduction());
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(true);
        assertTrue(milkProcessor.updateProduction());
        
        // Check that output stacked
        Item cheese = milkProcessor.getInventory().getItem(1);
        assertNotNull(cheese);
        assertEquals(2, cheese.getQuantity());
    }
}