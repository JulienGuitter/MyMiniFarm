package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.model.Inventory;
import fr.eseo.e3a.myminifarm.model.Map;
import fr.eseo.e3a.myminifarm.model.Player;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ItemType;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ProductType;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.Seed;
import fr.eseo.e3a.myminifarm.utils.GameTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CropCell class
 * 
 * This test class verifies the functionality of the CropCell class including:
 * - Initialization
 * - Planting
 * - Watering
 * - Growth checking
 * - Cell state management
 */
@ExtendWith(MockitoExtension.class)
public class CropCellTest {

    private CropCell cropCell;
    
    @Mock
    private GameLoop gameLoop;
    
    @Mock
    private Map farmMap;
    
    @Mock
    private Player player;
    
    @Mock
    private Inventory inventory;
    
    @Mock
    private Product seedProduct;

    @Mock
    private GameTime gameTime;

    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        cropCell = new CropCell();

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

        lenient().when(player.getInventory()).thenReturn(inventory);
    }
    
    /**
     * Configures le mock GameLoop comme singleton pour les tests qui en ont besoin
     */
    private void setupGameLoopSingleton() {
        try {
            Field instance = GameLoop.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(null, gameLoop);
        } catch (Exception e) {
            System.out.println("Error setting up mock GameLoop: " + e.getMessage());
        }
    }
    
    /**
     * Test l'initialisation correcte de CropCell
     */
    @Test
    public void testInitialization() {
        assertEquals("Crop Cell", cropCell.getName());
        assertEquals(0, cropCell.getCost());
        assertEquals(ItemType.STRUCTURE, cropCell.getItemType());
        assertEquals(1, cropCell.getTextureId());
        assertFalse(cropCell.isOccupied());
        assertTrue(cropCell.isFree());
        assertNull(cropCell.getCulture());
    }
    
    /**
     * Test la plantation d'une graine valide
     */
    @Test
    public void testPlantValidSeed() {
        setupGameLoopSingleton();
        
        // Configure un produit de graine valide
        when(seedProduct.getProductType()).thenReturn(ProductType.SEED);
        when(seedProduct.getSeed()).thenReturn(Seed.WHEAT_SEED);
        
        assertTrue(cropCell.plant(seedProduct));
        assertTrue(cropCell.isOccupied());
        assertFalse(cropCell.isFree());
        assertNotNull(cropCell.getCulture());
        verify(farmMap).setPlantCropChange(true);
    }
    
    /**
     * Test la plantation avec une graine nulle
     */
    @Test
    public void testPlantNullSeed() {
        setupGameLoopSingleton();
        
        assertFalse(cropCell.plant(null));
        assertFalse(cropCell.isOccupied());
        assertTrue(cropCell.isFree());
        assertNull(cropCell.getCulture());
    }
    
    /**
     * Test la plantation avec un produit qui n'est pas une graine
     */
    @Test
    public void testPlantNonSeedProduct() {
        setupGameLoopSingleton();
        
        // Configure un produit qui n'est pas une graine
        when(seedProduct.getProductType()).thenReturn(ProductType.ANIMAL);
        
        assertFalse(cropCell.plant(seedProduct));
        assertFalse(cropCell.isOccupied());
        assertTrue(cropCell.isFree());
        assertNull(cropCell.getCulture());
    }
    
    /**
     * Test la plantation dans une cellule déjà occupée
     */
    @Test
    public void testPlantInOccupiedCell() {
        setupGameLoopSingleton();
        
        // Plante d'abord une graine valide
        when(seedProduct.getProductType()).thenReturn(ProductType.SEED);
        when(seedProduct.getSeed()).thenReturn(Seed.WHEAT_SEED);
        cropCell.plant(seedProduct);
        
        // Essaie de planter une autre graine
        assertFalse(cropCell.plant(seedProduct));
    }
    
    /**
     * Test l'arrosage d'une culture plantée
     */
    @Test
    public void testWaterPlantedCulture() {
        setupGameLoopSingleton();
        
        // Plante d'abord une graine
        when(seedProduct.getProductType()).thenReturn(ProductType.SEED);
        when(seedProduct.getSeed()).thenReturn(Seed.WHEAT_SEED);
        cropCell.plant(seedProduct);
        
        assertTrue(cropCell.water());
        assertTrue(cropCell.isWatered());
        verify(farmMap).setCropCellChange(true);
    }
    
    /**
     * Test l'arrosage d'une cellule vide
     */
    @Test
    public void testWaterEmptyCell() {
        setupGameLoopSingleton();
        
        assertFalse(cropCell.water());
        assertFalse(cropCell.isWatered());
    }
    
    /**
     * Test l'arrosage d'une culture déjà arrosée
     */
    @Test
    public void testWaterAlreadyWateredCulture() {
        setupGameLoopSingleton();
        
        // Plante et arrose
        when(seedProduct.getProductType()).thenReturn(ProductType.SEED);
        when(seedProduct.getSeed()).thenReturn(Seed.WHEAT_SEED);
        cropCell.plant(seedProduct);
        cropCell.water();
        
        // Essaie d'arroser à nouveau
        clearInvocations(farmMap); // Réinitialiser le compteur d'appels
        assertFalse(cropCell.water());
        verify(farmMap, never()).setCropCellChange(true);
    }
    
    /**
     * Test la vérification de croissance sur une culture mature
     */
    @Test
    public void testCheckGrowthOnMatureCulture() {
        setupGameLoopSingleton();
        
        // Plante une culture
        when(seedProduct.getProductType()).thenReturn(ProductType.SEED);
        when(seedProduct.getSeed()).thenReturn(Seed.WHEAT_SEED);
        cropCell.plant(seedProduct);
        
        // Simule une culture mature à l'aide de la réflexion
        try {
            Culture culture = cropCell.getCulture();
            Field isMatureField = Culture.class.getDeclaredField("isMature");
            isMatureField.setAccessible(true);
            isMatureField.set(culture, true);
        } catch (Exception e) {
            fail("Failed to set up mature culture: " + e.getMessage());
        }
        
        // Vérifie la croissance (récolte)
        assertTrue(cropCell.checkGrowth(player));
        assertFalse(cropCell.isOccupied());
        assertNull(cropCell.getCulture());
        verify(inventory).add(any(Product.class));
    }
    
    /**
     * Test la vérification de croissance sur une culture non mature
     */
    @Test
    public void testCheckGrowthOnImmatureCulture() {
        setupGameLoopSingleton();
        
        // Plante et arrose une culture
        when(seedProduct.getProductType()).thenReturn(ProductType.SEED);
        when(seedProduct.getSeed()).thenReturn(Seed.WHEAT_SEED);
        cropCell.plant(seedProduct);
        cropCell.water();
        
        // La culture n'est pas encore mature
        assertFalse(cropCell.checkGrowth(player));
        assertTrue(cropCell.isOccupied());
        assertNotNull(cropCell.getCulture());
        verify(inventory, never()).add(any(Product.class));
    }
    
    /**
     * Test la vérification de croissance sur une cellule vide
     */
    @Test
    public void testCheckGrowthOnEmptyCell() {
        setupGameLoopSingleton();
        
        assertFalse(cropCell.checkGrowth(player));
        verify(inventory, never()).add(any(Product.class));
    }
    
    /**
     * Test le nettoyage d'une cellule
     */
    @Test
    public void testClear() {
        setupGameLoopSingleton();
        
        // Plante d'abord une culture
        when(seedProduct.getProductType()).thenReturn(ProductType.SEED);
        when(seedProduct.getSeed()).thenReturn(Seed.WHEAT_SEED);
        cropCell.plant(seedProduct);
        
        // Nettoie la cellule
        cropCell.clear();
        assertFalse(cropCell.isOccupied());
        assertTrue(cropCell.isFree());
        assertNull(cropCell.getCulture());
    }
}