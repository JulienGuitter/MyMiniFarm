package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Product.
 * 
 * This class contains exhaustive tests for all constructors and getters of the Product class,
 * covering all possible enum values for each product type.
 */
public class ProductTest {

    /**
     * Tests the constructor with all possible CultureProductType values.
     * 
     * @param type The culture product type to test
     */
    @ParameterizedTest
    @EnumSource(CultureProductType.class)
    public void testCultureProductConstruction(CultureProductType type) {
        Product product = new Product(type);
        
        assertEquals(ItemType.PRODUCT, product.getItemType());
        assertEquals(type.getName(), product.getName());
        assertEquals(type.getCost(), product.getCost());
        assertEquals(type.getTextureId(), product.getTextureId());
        assertEquals(ProductType.CULTURE, product.getProductType());
        assertEquals(type, product.getCultureProductType());
        assertNull(product.getAnimalProductType());
        assertNull(product.getMachineProductType());
        assertNull(product.getSeed());
    }

    /**
     * Tests the constructor with all possible AnimalProductType values.
     * 
     * @param type The animal product type to test
     */
    @ParameterizedTest
    @EnumSource(AnimalProductType.class)
    public void testAnimalProductConstruction(AnimalProductType type) {
        Product product = new Product(type);
        
        assertEquals(ItemType.PRODUCT, product.getItemType());
        assertEquals(type.getName(), product.getName());
        assertEquals(type.getCost(), product.getCost());
        assertEquals(type.getTextureId(), product.getTextureId());
        assertEquals(ProductType.ANIMAL, product.getProductType());
        assertEquals(type, product.getAnimalProductType());
        assertNull(product.getCultureProductType());
        assertNull(product.getMachineProductType());
        assertNull(product.getSeed());
    }

    /**
     * Tests the constructor with all possible MachineProductType values.
     * 
     * @param type The machine product type to test
     */
    @ParameterizedTest
    @EnumSource(MachineProductType.class)
    public void testMachineProductConstruction(MachineProductType type) {
        Product product = new Product(type);
        
        assertEquals(ItemType.PRODUCT, product.getItemType());
        assertEquals(type.getName(), product.getName());
        assertEquals(type.getCost(), product.getCost());
        assertEquals(type.getTextureId(), product.getTextureId());
        assertEquals(ProductType.MACHINE, product.getProductType());
        assertEquals(type, product.getMachineProductType());
        assertNull(product.getCultureProductType());
        assertNull(product.getAnimalProductType());
        assertNull(product.getSeed());
    }

    /**
     * Tests the constructor with various Seed instances for each culture type.
     */
    @ParameterizedTest
    @EnumSource(Seed.class)
    public void testSeedProductConstruction(Seed seed) {
        Product product = new Product(seed);
        
        assertEquals(ItemType.PRODUCT, product.getItemType());
        assertEquals(seed.getName(), product.getName());
        assertEquals(seed.getCost(), product.getCost());
        assertEquals(seed.getTextureId(), product.getTextureId());
        assertEquals(ProductType.SEED, product.getProductType());
        assertEquals(seed, product.getSeed());
        assertNull(product.getCultureProductType());
        assertNull(product.getAnimalProductType());
        assertNull(product.getMachineProductType());
    }
    
    /**
     * Tests getProductType returns the correct product type for each constructor.
     */
    @Test
    public void testGetProductType() {
        Product cultureProd = new Product(CultureProductType.CORN);
        Product animalProd = new Product(AnimalProductType.MILK);
        Product machineProd = new Product(MachineProductType.FLOUR);
        Product seedProd = new Product(Seed.CORN_SEED);
        
        assertEquals(ProductType.CULTURE, cultureProd.getProductType());
        assertEquals(ProductType.ANIMAL, animalProd.getProductType());
        assertEquals(ProductType.MACHINE, machineProd.getProductType());
        assertEquals(ProductType.SEED, seedProd.getProductType());
    }
    
    /**
     * Tests that the Item properties are correctly inherited.
     */
    @Test
    public void testInheritedItemProperties() {
        // Test a product instance inherits and manages Item properties correctly
        CultureProductType type = CultureProductType.CORN;
        Product product = new Product(type);
        
        assertEquals(ItemType.PRODUCT, product.getItemType());
        assertEquals(type.getName(), product.getName());
        assertEquals(type.getCost(), product.getCost());
        assertEquals(type.getTextureId(), product.getTextureId());
        
        // Verify same behavior with other types
        AnimalProductType animalType = AnimalProductType.MILK;
        Product animalProduct = new Product(animalType);
        assertEquals(animalType.getName(), animalProduct.getName());
        assertEquals(animalType.getCost(), animalProduct.getCost());
    }
    
    /**
     * Tests that products with same type but different underlying specific types are distinct.
     */
    @Test
    public void testProductDifferentiation() {
        // Create two products of same type but different specific types
        Product cornProduct = new Product(CultureProductType.CORN);
        Product wheatProduct = new Product(CultureProductType.WHEAT);
        
        assertEquals(cornProduct.getProductType(), wheatProduct.getProductType());
        assertNotEquals(cornProduct.getCultureProductType(), wheatProduct.getCultureProductType());
        
        // Same test with animal products
        Product milkProduct = new Product(AnimalProductType.MILK);
        Product eggProduct = new Product(AnimalProductType.EGG);
        
        assertEquals(milkProduct.getProductType(), eggProduct.getProductType());
        assertNotEquals(milkProduct.getAnimalProductType(), eggProduct.getAnimalProductType());
    }
}