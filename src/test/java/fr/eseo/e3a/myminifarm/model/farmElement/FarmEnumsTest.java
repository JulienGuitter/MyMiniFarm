package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FarmEnums class
 * 
 * These tests verify:
 * - Enum values are correctly defined
 * - Getter methods return expected values
 * - Special methods like fromSeed and getByName work correctly
 * - toString methods return correct string representations
 */
public class FarmEnumsTest {

    /**
     * Test ItemType enum values
     */
    @Test
    public void testItemTypeValues() {
        // Verify all expected values exist
        assertEquals(9, ItemType.values().length);
        assertNotNull(ItemType.FARM_LAND);
        assertNotNull(ItemType.CROP);
        assertNotNull(ItemType.ANIMAL);
        assertNotNull(ItemType.MACHINE);
        assertNotNull(ItemType.CONTAINER);
        assertNotNull(ItemType.CULTURE);
        assertNotNull(ItemType.PRODUCT);
        assertNotNull(ItemType.STRUCTURE);
        assertNotNull(ItemType.TOOL);
    }

    /**
     * Test ProductType enum values
     */
    @Test
    public void testProductTypeValues() {
        // Verify all expected values exist
        assertEquals(4, ProductType.values().length);
        assertNotNull(ProductType.ANIMAL);
        assertNotNull(ProductType.MACHINE);
        assertNotNull(ProductType.CULTURE);
        assertNotNull(ProductType.SEED);
    }

    /**
     * Test AnimalProductType enum
     */
    @Test
    public void testAnimalProductType() {
        // Test MILK
        assertEquals("Milk", AnimalProductType.MILK.getName());
        assertEquals(20, AnimalProductType.MILK.getCost());
        assertEquals(13, AnimalProductType.MILK.getTextureId());
        assertEquals("Milk", AnimalProductType.MILK.toString());
        
        // Test all values exist
        assertEquals(4, AnimalProductType.values().length);
        
        // Test that all values have unique texture IDs
        assertEquals(AnimalProductType.values().length, 
                     java.util.Arrays.stream(AnimalProductType.values())
                     .map(AnimalProductType::getTextureId)
                     .distinct()
                     .count());
    }
    
    /**
     * Test Animals enum
     */
    @Test
    public void testAnimals() {
        // Test COW
        assertEquals("Cow", Animals.COW.getName());
        assertEquals(40, Animals.COW.getCost());
        assertEquals(25, Animals.COW.getResalePrice());
        assertEquals(MachineProductType.COW_FOOD, Animals.COW.getFood());
        assertEquals(AnimalProductType.MILK, Animals.COW.getProductionType());
        assertEquals(920, Animals.COW.getProductionDelay());
        assertEquals(1, Animals.COW.getTextureId());
        assertEquals("Cow", Animals.COW.toString());
        
        // Test all values exist
        assertEquals(4, Animals.values().length);
        
        // Test that all values have unique names
        assertEquals(Animals.values().length, 
                     java.util.Arrays.stream(Animals.values())
                     .map(Animals::getName)
                     .distinct()
                     .count());
                     
        // Test that all animals have matching production types
        assertEquals(AnimalProductType.MILK, Animals.COW.getProductionType());
        assertEquals(AnimalProductType.EGG, Animals.CHICKEN.getProductionType());
        assertEquals(AnimalProductType.BACON, Animals.PIG.getProductionType());
        assertEquals(AnimalProductType.WOOL, Animals.SHEEP.getProductionType());
    }
    
    /**
     * Test MachineProductType enum
     */
    @Test
    public void testMachineProductType() {
        // Test BREAD
        assertEquals("Bread", MachineProductType.BREAD.getName());
        assertEquals(40, MachineProductType.BREAD.getCost());
        assertEquals(18, MachineProductType.BREAD.getTextureId());
        assertEquals("Bread", MachineProductType.BREAD.toString());
        
        // Test all values exist
        assertEquals(7, MachineProductType.values().length);
        
        // Test that all values have unique texture IDs
        assertEquals(MachineProductType.values().length, 
                     java.util.Arrays.stream(MachineProductType.values())
                     .map(MachineProductType::getTextureId)
                     .distinct()
                     .count());
    }
    
    /**
     * Test Machines enum
     */
    @Test
    public void testMachines() {
        // Test OVEN
        assertEquals("Oven", Machines.OVEN.getName());
        assertEquals(700, Machines.OVEN.getCost());
        assertEquals(400, Machines.OVEN.getResalePrice());
        assertEquals(50, Machines.OVEN.getProductionDelay());
        assertEquals(0, Machines.OVEN.getTextureId());
        assertEquals("Oven", Machines.OVEN.toString());
        
        // Test all values exist
        assertEquals(5, Machines.values().length);
        
        // Test that all machines have unique names
        assertEquals(Machines.values().length, 
                     java.util.Arrays.stream(Machines.values())
                     .map(Machines::getName)
                     .distinct()
                     .count());
    }
    
    /**
     * Test Containers enum
     */
    @Test
    public void testContainers() {
        // Test CHEST
        assertEquals("Chest", Containers.CHEST.getName());
        assertEquals(9, Containers.CHEST.getSize());
        assertFalse(Containers.CHEST.isKeepOnDestroy());
        assertTrue(Containers.CHEST.isKeepOnClose());
        assertEquals(100, Containers.CHEST.getCost());
        assertEquals(3, Containers.CHEST.getTextureId());
        assertEquals("Chest", Containers.CHEST.toString());
        
        // Test TRASH
        assertEquals("Trash", Containers.TRASH.getName());
        assertEquals(3, Containers.TRASH.getSize());
        assertFalse(Containers.TRASH.isKeepOnDestroy());
        assertFalse(Containers.TRASH.isKeepOnClose());
        assertEquals(50, Containers.TRASH.getCost());
        assertEquals(2, Containers.TRASH.getTextureId());
        
        // Test all values exist
        assertEquals(2, Containers.values().length);
    }
    
    /**
     * Test Seed enum
     */
    @Test
    public void testSeed() {
        // Test WHEAT_SEED
        assertEquals("Wheat_Seed", Seed.WHEAT_SEED.getName());
        assertEquals(5, Seed.WHEAT_SEED.getCost());
        assertEquals(0, Seed.WHEAT_SEED.getTextureId());
        assertEquals("Wheat_Seed", Seed.WHEAT_SEED.toString());
        
        // Test all values exist
        assertEquals(6, Seed.values().length);
        
        // Test that all seeds have unique texture IDs
        assertEquals(Seed.values().length, 
                     java.util.Arrays.stream(Seed.values())
                     .map(Seed::getTextureId)
                     .distinct()
                     .count());
    }
    
    /**
     * Test CultureProductType enum
     */
    @Test
    public void testCultureProductType() {
        // Test WHEAT
        assertEquals("Wheat", CultureProductType.WHEAT.getName());
        assertEquals(5, CultureProductType.WHEAT.getCost());
        assertEquals(6, CultureProductType.WHEAT.getTextureId());
        assertEquals("Wheat", CultureProductType.WHEAT.toString());
        
        // Test all values exist
        assertEquals(6, CultureProductType.values().length);
        
        // Test that all culture products have unique texture IDs
        assertEquals(CultureProductType.values().length, 
                     java.util.Arrays.stream(CultureProductType.values())
                     .map(CultureProductType::getTextureId)
                     .distinct()
                     .count());
    }
    
    /**
     * Test CultureType enum
     */
    @Test
    public void testCultureType() {
        // Test CULTURE_WHEAT
        assertEquals(Seed.WHEAT_SEED, CultureType.CULTURE_WHEAT.getSeedType());
        assertEquals(CultureProductType.WHEAT, CultureType.CULTURE_WHEAT.getProductType());
        assertEquals(10, CultureType.CULTURE_WHEAT.getGrowthTime());
        assertEquals(0, CultureType.CULTURE_WHEAT.getTextureId());
        
        // Test all values exist
        assertEquals(6, CultureType.values().length);
        
        // Test that culture types have matching seeds and products
        for (CultureType cultureType : CultureType.values()) {
            assertNotNull(cultureType.getSeedType());
            assertNotNull(cultureType.getProductType());
        }
        
        // Test fromSeed method
        assertEquals(CultureType.CULTURE_WHEAT, CultureType.fromSeed(Seed.WHEAT_SEED));
        assertEquals(CultureType.CULTURE_POTATO, CultureType.fromSeed(Seed.POTATO_SEED));
        assertEquals(CultureType.CULTURE_CORN, CultureType.fromSeed(Seed.CORN_SEED));
        assertEquals(CultureType.CULTURE_TOMATO, CultureType.fromSeed(Seed.TOMATO_SEED));
        assertEquals(CultureType.CULTURE_CABBAGE, CultureType.fromSeed(Seed.CABBAGE_SEED));
        assertEquals(CultureType.CULTURE_SUNFLOWER, CultureType.fromSeed(Seed.SUNFLOWER_SEED));

        assertNull(CultureType.fromSeed(null));
    }
    
    /**
     * Test ToolType enum
     */
    @Test
    public void testToolType() {
        // Test PICKAXE
        assertEquals("Pickaxe", ToolType.PICKAXE.getName());
        assertEquals(15, ToolType.PICKAXE.getCost());
        assertEquals(1, ToolType.PICKAXE.getTextureId());
        assertEquals("Pickaxe", ToolType.PICKAXE.toString());
        
        // Test all values exist
        assertEquals(4, ToolType.values().length);
        
        // Test that all tools have unique texture IDs
        assertEquals(ToolType.values().length, 
                     java.util.Arrays.stream(ToolType.values())
                     .map(ToolType::getTextureId)
                     .distinct()
                     .count());
    }
    
    /**
     * Test ReceipeType enum
     */
    @Test
    public void testReceipeType() {
        // Test FLOUR
        assertEquals(CultureProductType.WHEAT, ReceipeType.FLOUR.getInput());
        assertEquals(MachineProductType.FLOUR, ReceipeType.FLOUR.getOutput());
        assertEquals(Machines.WINDMILL, ReceipeType.FLOUR.getMachine());
        assertEquals(1, ReceipeType.FLOUR.getQuantity());
        
        // Test all values exist
        assertEquals(7, ReceipeType.values().length);
        
        // Test getByName method
        assertEquals(ReceipeType.FLOUR, ReceipeType.getByName("Wheat"));
        assertEquals(ReceipeType.BREAD, ReceipeType.getByName("Flour"));
        assertEquals(ReceipeType.CHEESE, ReceipeType.getByName("Milk"));
        assertNull(ReceipeType.getByName("NonExistingIngredient"));
    }
    
    /**
     * Test interactions between related enums
     */
    @Test
    public void testEnumRelationships() {
        // Test relationship between Animals and their food
        assertEquals(MachineProductType.COW_FOOD, Animals.COW.getFood());
        assertEquals(MachineProductType.CHICKEN_FOOD, Animals.CHICKEN.getFood());
        assertEquals(MachineProductType.PIG_FOOD, Animals.PIG.getFood());
        assertEquals(MachineProductType.SHEEP_FOOD, Animals.SHEEP.getFood());
        
        // Test relationship between Seeds and CultureTypes
        for (Seed seed : Seed.values()) {
            CultureType cultureType = CultureType.fromSeed(seed);
            assertNotNull(cultureType, "Each seed should have a matching culture type");
            assertEquals(seed, cultureType.getSeedType());
        }
        
        // Test relationship between Recipes inputs/outputs
        for (ReceipeType recipe : ReceipeType.values()) {
            assertNotNull(recipe.getInput());
            assertNotNull(recipe.getOutput());
            assertNotNull(recipe.getMachine());
        }
    }
}