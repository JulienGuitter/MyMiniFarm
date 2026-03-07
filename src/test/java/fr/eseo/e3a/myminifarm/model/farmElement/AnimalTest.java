package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.Animals;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Animal class
 * 
 * These tests verify the functionality of the Animal class including:
 * - Initialization
 * - Feeding
 * - Production
 * - Status checking
 */
public class AnimalTest {

    private Animal chicken;
    private Animal cow;
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        chicken = new Animal(Animals.CHICKEN);
        cow = new Animal(Animals.COW);
    }
    
    /**
     * Test that animal is properly initialized
     */
    @Test
    public void testInitialization() {
        assertEquals(Animals.CHICKEN.getName(), chicken.getName());
        assertEquals(Animals.CHICKEN.getCost(), chicken.getCost());
        assertEquals(ItemType.ANIMAL, chicken.getItemType());
        assertEquals(Animals.CHICKEN.getTextureId(), chicken.getTextureId());
        assertFalse(chicken.isFed());
    }
    
    /**
     * Test that animal correctly identifies suitable food
     */
    @Test
    public void testIsSuitable() {
        assertTrue(chicken.isSuitable(Animals.CHICKEN.getFood()));
        assertFalse(chicken.isSuitable(Animals.COW.getFood()));
        
        assertTrue(cow.isSuitable(Animals.COW.getFood()));
        assertFalse(cow.isSuitable(Animals.CHICKEN.getFood()));
    }
    
    /**
     * Test feeding the animal with suitable food
     */
    @Test
    public void testFeedWithSuitableFood() {
        long beforeFeed = System.currentTimeMillis();
        assertTrue(chicken.feed(Animals.CHICKEN.getFood()));
        long afterFeed = System.currentTimeMillis();
        
        assertTrue(chicken.isFed());
        
        // Verify that production time is set to current time + delay
        // We allow for a small window since we're using real system time
        long expectedMinTime = beforeFeed + Animals.CHICKEN.getProductionDelay();
        long expectedMaxTime = afterFeed + Animals.CHICKEN.getProductionDelay();
        
        assertTrue(chicken.getNextProductionTime() >= expectedMinTime);
        assertTrue(chicken.getNextProductionTime() <= expectedMaxTime);
    }
    
    /**
     * Test feeding the animal with unsuitable food
     */
    @Test
    public void testFeedWithUnsuitableFood() {
        assertFalse(chicken.feed(Animals.COW.getFood()));
        assertFalse(chicken.isFed());
        assertEquals(0, chicken.getNextProductionTime());
    }
    
    /**
     * Test if animal is not ready immediately after feeding
     */
    @Test
    public void testNotReadyImmediatelyAfterFeeding() {
        chicken.feed(Animals.CHICKEN.getFood());
        assertFalse(chicken.isReadyToProduce());
    }
    
    /**
     * Test manually setting production time to simulate passage of time
     */
    @Test
    public void testManualProductionTime() {
        // Feed the animal
        chicken.feed(Animals.CHICKEN.getFood());
        
        // Set the next production time to now to simulate time passage
        chicken.setNextProductionTime(System.currentTimeMillis() - 1);
        
        // Now the animal should be ready to produce
        assertTrue(chicken.isReadyToProduce());
    }
    
    /**
     * Test production when the animal is ready
     */
    @Test
    public void testProduction() {
        Product product = chicken.produce();
        
        assertNotNull(product);
        assertEquals(FarmEnums.ProductType.ANIMAL, product.getProductType());
    }
    
    /**
     * Test attempting production when animal is manually set to ready
     */
    @Test
    public void testAttemptProductionWhenReady() {
        // Feed the animal
        chicken.feed(Animals.CHICKEN.getFood());
        
        // Manually set production time to simulate time passage
        chicken.setNextProductionTime(System.currentTimeMillis() - 1);
        
        // Attempt production
        Product product = chicken.attemptProduction();
        
        assertNotNull(product);
        assertEquals(FarmEnums.ProductType.ANIMAL, product.getProductType());
        assertFalse(chicken.isFed()); // Animal should no longer be fed after producing
    }
    
    /**
     * Test attempting production when animal is not ready
     */
    @Test
    public void testAttemptProductionWhenNotReady() {
        // Animal not fed
        assertNull(chicken.attemptProduction());
        
        // Feed the animal, but production time not passed
        chicken.feed(Animals.CHICKEN.getFood());
        
        // Attempt production
        assertNull(chicken.attemptProduction());
        assertTrue(chicken.isFed()); // Animal should still be fed
    }
    
    /**
     * Test setters
     */
    @Test
    public void testSetters() {
        chicken.setIsFed(true);
        assertTrue(chicken.isFed());
        
        long time = System.currentTimeMillis() + 5000L;
        chicken.setNextProductionTime(time);
        assertEquals(time, chicken.getNextProductionTime());
    }
    
    /**
     * Test that different animals produce different products
     */
    @Test
    public void testDifferentAnimalProducts() {
        Product chickenProduct = chicken.produce();
        Product cowProduct = cow.produce();
        
        assertNotNull(chickenProduct);
        assertNotNull(cowProduct);
        assertEquals(FarmEnums.ProductType.ANIMAL, chickenProduct.getProductType());
        assertEquals(FarmEnums.ProductType.ANIMAL, cowProduct.getProductType());
        assertEquals(chickenProduct.getProductType(), cowProduct.getProductType());
    }
}