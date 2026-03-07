package fr.eseo.e3a.myminifarm.model.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MapJson class
 * 
 * These tests verify the functionality of the MapJson class including:
 * - Getting and setting properties
 * - Proper toString() method implementation
 */
public class MapJsonTest {

    private MapJson mapJson;
    private List<LayerJson> testLayers;
    private List<TilesetJson> testTilesets;
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        mapJson = new MapJson();
        
        // Create test layers
        testLayers = new ArrayList<>();
        LayerJson layer1 = new LayerJson();
        layer1.setName("Ground");
        layer1.setWidth(10);
        layer1.setHeight(10);
        
        LayerJson layer2 = new LayerJson();
        layer2.setName("Objects");
        layer2.setWidth(10);
        layer2.setHeight(10);
        
        testLayers.add(layer1);
        testLayers.add(layer2);
        
        // Create test tilesets
        testTilesets = new ArrayList<>();
        TilesetJson tileset1 = new TilesetJson();
        tileset1.setFirstgid(1);

        TilesetJson tileset2 = new TilesetJson();
        tileset2.setFirstgid(2);
        
        testTilesets.add(tileset1);
        testTilesets.add(tileset2);
    }
    
    /**
     * Test default initialization
     */
    @Test
    public void testDefaultInitialization() {
        assertNull(mapJson.getLayers());
        assertNull(mapJson.getTilesets());
        assertEquals(0, mapJson.getWidth());
        assertEquals(0, mapJson.getHeight());
        assertEquals(0, mapJson.getTilewidth());
        assertEquals(0, mapJson.getTileheight());
        assertEquals(0, mapJson.getNextobjectid());
        assertEquals(0, mapJson.getNextlayerid());
    }
    
    /**
     * Test setting and getting layers
     */
    @Test
    public void testLayersGetterSetter() {
        mapJson.setLayers(testLayers);
        
        assertEquals(testLayers, mapJson.getLayers());
        assertEquals(2, mapJson.getLayers().size());
        assertEquals("Ground", mapJson.getLayers().get(0).getName());
        assertEquals("Objects", mapJson.getLayers().get(1).getName());
    }
    
    /**
     * Test setting and getting tilesets
     */
    @Test
    public void testTilesetsGetterSetter() {
        mapJson.setTilesets(testTilesets);
        
        assertEquals(testTilesets, mapJson.getTilesets());
        assertEquals(2, mapJson.getTilesets().size());
        assertEquals(1, mapJson.getTilesets().get(0).getFirstgid());
        assertEquals(2, mapJson.getTilesets().get(1).getFirstgid());
    }
    
    /**
     * Test setting and getting width
     */
    @Test
    public void testWidthGetterSetter() {
        assertEquals(0, mapJson.getWidth());
        
        mapJson.setWidth(20);
        assertEquals(20, mapJson.getWidth());
        
        mapJson.setWidth(30);
        assertEquals(30, mapJson.getWidth());
    }
    
    /**
     * Test setting and getting height
     */
    @Test
    public void testHeightGetterSetter() {
        assertEquals(0, mapJson.getHeight());
        
        mapJson.setHeight(15);
        assertEquals(15, mapJson.getHeight());
        
        mapJson.setHeight(25);
        assertEquals(25, mapJson.getHeight());
    }
    
    /**
     * Test setting and getting tilewidth
     */
    @Test
    public void testTilewidthGetterSetter() {
        assertEquals(0, mapJson.getTilewidth());
        
        mapJson.setTilewidth(16);
        assertEquals(16, mapJson.getTilewidth());
        
        mapJson.setTilewidth(32);
        assertEquals(32, mapJson.getTilewidth());
    }
    
    /**
     * Test setting and getting tileheight
     */
    @Test
    public void testTileheightGetterSetter() {
        assertEquals(0, mapJson.getTileheight());
        
        mapJson.setTileheight(16);
        assertEquals(16, mapJson.getTileheight());
        
        mapJson.setTileheight(32);
        assertEquals(32, mapJson.getTileheight());
    }
    
    /**
     * Test setting and getting nextobjectid
     */
    @Test
    public void testNextobjectidGetterSetter() {
        assertEquals(0, mapJson.getNextobjectid());
        
        mapJson.setNextobjectid(1);
        assertEquals(1, mapJson.getNextobjectid());
        
        mapJson.setNextobjectid(10);
        assertEquals(10, mapJson.getNextobjectid());
    }
    
    /**
     * Test setting and getting nextlayerid
     */
    @Test
    public void testNextlayeridGetterSetter() {
        assertEquals(0, mapJson.getNextlayerid());
        
        mapJson.setNextlayerid(2);
        assertEquals(2, mapJson.getNextlayerid());
        
        mapJson.setNextlayerid(5);
        assertEquals(5, mapJson.getNextlayerid());
    }
    
    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        mapJson.setLayers(testLayers);
        mapJson.setTilesets(testTilesets);
        mapJson.setWidth(20);
        mapJson.setHeight(15);
        mapJson.setTilewidth(16);
        mapJson.setTileheight(16);
        mapJson.setNextobjectid(1);
        mapJson.setNextlayerid(3);
        
        String expected = "MapJson{ \n" +
                "layers=" + testLayers + ", \n" +
                "tilesets=" + testTilesets + ", \n" +
                "width=20, \n" +
                "height=15, \n" +
                "tilewidth=16, \n" +
                "tileheight=16, \n" +
                "nextobjectid=1, \n" +
                "nextlayerid=3\n" +
                '}';
        assertEquals(expected, mapJson.toString());
    }
    
    /**
     * Test layers modification operations
     */
    @Test
    public void testLayersModification() {
        mapJson.setLayers(testLayers);
        
        // Test adding a new layer
        LayerJson layer3 = new LayerJson();
        layer3.setName("Decoration");
        mapJson.getLayers().add(layer3);
        
        assertEquals(3, mapJson.getLayers().size());
        assertEquals("Decoration", mapJson.getLayers().get(2).getName());
        
        // Test modifying an existing layer
        mapJson.getLayers().get(0).setName("UpdatedGround");
        assertEquals("UpdatedGround", mapJson.getLayers().get(0).getName());
        
        // Test removing a layer
        mapJson.getLayers().remove(1);
        assertEquals(2, mapJson.getLayers().size());
        assertEquals("Decoration", mapJson.getLayers().get(1).getName());
    }
    
    /**
     * Test tilesets modification operations
     */
    @Test
    public void testTilesetsModification() {
        mapJson.setTilesets(testTilesets);
        
        // Test adding a new tileset
        TilesetJson tileset3 = new TilesetJson();
        tileset3.setFirstgid(3);
        mapJson.getTilesets().add(tileset3);
        
        assertEquals(3, mapJson.getTilesets().size());
        assertEquals(3, mapJson.getTilesets().get(2).getFirstgid());
        
        // Test modifying an existing tileset
        mapJson.getTilesets().get(0).setFirstgid(10);
        assertEquals(10, mapJson.getTilesets().get(0).getFirstgid());
        
        // Test removing a tileset
        mapJson.getTilesets().remove(1);
        assertEquals(2, mapJson.getTilesets().size());
        assertEquals(3, mapJson.getTilesets().get(1).getFirstgid());
    }
    
    /**
     * Test with empty collections
     */
    @Test
    public void testEmptyCollections() {
        // Test with empty layers
        List<LayerJson> emptyLayers = new ArrayList<>();
        mapJson.setLayers(emptyLayers);
        
        assertEquals(emptyLayers, mapJson.getLayers());
        assertEquals(0, mapJson.getLayers().size());
        assertTrue(mapJson.getLayers().isEmpty());
        
        // Test with empty tilesets
        List<TilesetJson> emptyTilesets = new ArrayList<>();
        mapJson.setTilesets(emptyTilesets);
        
        assertEquals(emptyTilesets, mapJson.getTilesets());
        assertEquals(0, mapJson.getTilesets().size());
        assertTrue(mapJson.getTilesets().isEmpty());
    }
    
    /**
     * Test JsonIgnoreProperties annotation effect
     * (We cannot directly test the annotation, but we can document its presence)
     */
    @Test
    public void testJsonIgnorePropertiesAnnotation() {
        // This test is more informative than functional
        // It documents that the class has the JsonIgnoreProperties annotation
        
        // We verify that the class can be instantiated and properties set normally
        MapJson map = new MapJson();
        map.setWidth(50);
        assertEquals(50, map.getWidth());
        
        // Note: The actual behavior of JsonIgnoreProperties would be tested
        // in integration tests with actual JSON deserialization
    }
    
    /**
     * Test extreme values for numeric properties
     */
    @Test
    public void testExtremeValues() {
        // Test with extreme integer values
        mapJson.setWidth(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, mapJson.getWidth());
        
        mapJson.setHeight(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, mapJson.getHeight());
    }
}