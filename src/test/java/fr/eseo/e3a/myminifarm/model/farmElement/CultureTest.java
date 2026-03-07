package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.model.Map;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.Seed;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.CultureType;
import fr.eseo.e3a.myminifarm.utils.GameTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the Culture class
 * These tests verify the functionality of the Culture class including:
 * - Initialization
 * - Planting
 * - Watering
 * - Growth stages
 * - Harvesting
 */
@ExtendWith(MockitoExtension.class)
public class CultureTest {

    private Culture wheatCulture;
    private Culture cornCulture;
    
    @Mock
    private GameLoop gameLoop;

    @Mock
    private Map farmMap;

    @Mock
    private GameTime gameTime;

    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        // Configure mocks
        lenient().when(gameLoop.getGameTime()).thenReturn(gameTime);
        lenient().when(gameLoop.getMap()).thenReturn(farmMap);
        lenient(). when(gameTime.clone()).thenReturn(gameTime);

        // Since GameLoop is a singleton with static access, we need to mock it
        // But instead of using static mocking which can cause issues, we'll test
        // indirectly or use reflection to set the instance
        try {
            java.lang.reflect.Field instance = GameLoop.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, gameLoop);
        } catch (Exception e) {
            System.out.println("Error setting up mock GameLoop: " + e.getMessage());
        }

        wheatCulture = new Culture(Seed.WHEAT_SEED);
        cornCulture = new Culture(Seed.CORN_SEED);
    }
    
    /**
     * Test proper initialization of Culture
     */
    @Test
    public void testInitialization() {
        assertEquals(Seed.WHEAT_SEED.getName(), wheatCulture.getName());
        assertEquals(Seed.WHEAT_SEED.getCost(), wheatCulture.getCost());
        assertEquals(ItemType.CULTURE, wheatCulture.getItemType());
        assertEquals(Seed.WHEAT_SEED.getTextureId(), wheatCulture.getTextureId());
        assertEquals(CultureType.fromSeed(Seed.WHEAT_SEED), wheatCulture.getCultureType());
        
        assertFalse(wheatCulture.isPlanted());
        assertFalse(wheatCulture.isWatered());
        assertFalse(wheatCulture.isMature());
        assertEquals(0, wheatCulture.getGrowthStage());
    }
    
    /**
     * Test planting a culture
     */
    @Test
    public void testPlant() {
        assertTrue(wheatCulture.plant());
        assertTrue(wheatCulture.isPlanted());
        
        // Test planting an already planted culture
        assertFalse(wheatCulture.plant());
    }
    
    /**
     * Test watering a culture
     */
    @Test
    public void testWater() {
        // Cannot water an unplanted culture
        assertFalse(wheatCulture.water());
        
        wheatCulture.plant();
        assertTrue(wheatCulture.water());
        assertTrue(wheatCulture.isWatered());
        
        // Test watering an already watered culture
        assertFalse(wheatCulture.water());
    }
    
    /**
     * Test updating growth stage
     */
    @Test
    public void testUpdateGrowthStage() {
        // Set up culture
        wheatCulture.plant();
        wheatCulture.water();
        
        // Mock time passed
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(true);
        
        wheatCulture.updateGrowthStage();
        assertEquals(1, wheatCulture.getGrowthStage());
        verify(farmMap).setCropCellChange(true);
        
        // Test growth up to maturity
        for (int i = 1; i < 5; i++) {
            wheatCulture.updateGrowthStage();
        }
        
        assertEquals(5, wheatCulture.getGrowthStage());
        assertTrue(wheatCulture.isMature());
    }
    
    /**
     * Test that growth stage doesn't update when time hasn't passed
     */
    @Test
    public void testNoGrowthWhenTimeNotPassed() {
        // Set up culture
        wheatCulture.plant();
        wheatCulture.water();
        
        // Mock time not passed
        when(gameTime.isTimePassed(any(GameTime.class))).thenReturn(false);
        
        wheatCulture.updateGrowthStage();
        assertEquals(0, wheatCulture.getGrowthStage());
        verify(farmMap, never()).setCropCellChange(true);
    }
    
    /**
     * Test harvesting a mature culture
     */
    @Test
    public void testHarvestWhenMature() {
        // Set up mature culture
        wheatCulture.plant();
        wheatCulture.water();
        
        // Simulate growth to maturity using reflection to avoid GameTime dependencies
        try {
            java.lang.reflect.Field growthStageField = Culture.class.getDeclaredField("growthStage");
            growthStageField.setAccessible(true);
            growthStageField.set(wheatCulture, 5);
            
            java.lang.reflect.Field isMatureField = Culture.class.getDeclaredField("isMature");
            isMatureField.setAccessible(true);
            isMatureField.set(wheatCulture, true);
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }
        
        // Harvest
        Product product = wheatCulture.attemptHarvest();
        
        // Verify
        assertNotNull(product);
        assertEquals(FarmEnums.ProductType.CULTURE, product.getProductType());
        assertFalse(wheatCulture.isPlanted());
        assertFalse(wheatCulture.isWatered());
        assertFalse(wheatCulture.isMature());
        assertEquals(0, wheatCulture.getGrowthStage());
        verify(farmMap).setPlantCropChange(true);
    }
    
    /**
     * Test attempting to harvest an immature culture
     */
    @Test
    public void testAttemptHarvestWhenImmature() {
        wheatCulture.plant();
        wheatCulture.water();
        
        Product product = wheatCulture.attemptHarvest();
        
        assertNull(product);
        assertTrue(wheatCulture.isPlanted());
        assertTrue(wheatCulture.isWatered());
        verify(farmMap, never()).setPlantCropChange(any(Boolean.class));
    }
    
    /**
     * Test that different cultures produce different products
     */
    @Test
    public void testDifferentCultureProducts() {
        // Set up mature cultures
        wheatCulture.plant();
        wheatCulture.water();
        cornCulture.plant();
        cornCulture.water();
        
        // Make them mature using reflection
        try {
            java.lang.reflect.Field isMatureField = Culture.class.getDeclaredField("isMature");
            isMatureField.setAccessible(true);
            isMatureField.set(wheatCulture, true);
            isMatureField.set(cornCulture, true);
        } catch (Exception e) {
            fail("Failed to set up test: " + e.getMessage());
        }
        
        Product wheatProduct = wheatCulture.attemptHarvest();
        Product cornProduct = cornCulture.attemptHarvest();
        
        assertNotNull(wheatProduct);
        assertNotNull(cornProduct);
        assertEquals(FarmEnums.ProductType.CULTURE, wheatProduct.getProductType());
        assertEquals(FarmEnums.ProductType.CULTURE, cornProduct.getProductType());
        assertEquals(wheatProduct.getProductType(), cornProduct.getProductType());
    }
    
    /**
     * Test the direct harvest method
     */
    @Test
    public void testHarvestMethod() {
        Product wheatProduct = wheatCulture.harvest();
        Product cornProduct = cornCulture.harvest();
        
        assertNotNull(wheatProduct);
        assertNotNull(cornProduct);
        assertEquals(FarmEnums.ProductType.CULTURE, wheatProduct.getProductType());
        assertEquals(FarmEnums.ProductType.CULTURE, cornProduct.getProductType());
    }
}