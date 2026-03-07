package fr.eseo.e3a.myminifarm.model.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ConfigJson class
 * 
 * These tests verify the functionality of the ConfigJson class including:
 * - Getting and setting properties
 * - Proper toString() method implementation
 */
public class ConfigJsonTest {

    private ConfigJson configJson;
    private HashMap<String, String> testKeys;
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        configJson = new ConfigJson();
        testKeys = new HashMap<>();
        testKeys.put("UP", "W");
        testKeys.put("DOWN", "S");
        testKeys.put("LEFT", "A");
        testKeys.put("RIGHT", "D");
    }
    
    /**
     * Test default initialization
     */
    @Test
    public void testDefaultInitialization() {
        assertNotNull(configJson.getKeys());
        assertTrue(configJson.getKeys().isEmpty());
        assertFalse(configJson.isFullscreen());
        assertEquals(0, configJson.getVolume());
        assertEquals(0, configJson.getUps());
        assertEquals(0, configJson.getFps());
    }
    
    /**
     * Test setting and getting keys
     */
    @Test
    public void testKeysGetterSetter() {
        configJson.setKeys(testKeys);
        
        assertEquals(testKeys, configJson.getKeys());
        assertEquals(4, configJson.getKeys().size());
        assertEquals("W", configJson.getKeys().get("UP"));
        assertEquals("S", configJson.getKeys().get("DOWN"));
        assertEquals("A", configJson.getKeys().get("LEFT"));
        assertEquals("D", configJson.getKeys().get("RIGHT"));
    }
    
    /**
     * Test setting and getting fullscreen property
     */
    @Test
    public void testFullscreenGetterSetter() {
        assertFalse(configJson.isFullscreen());
        
        configJson.setFullscreen(true);
        assertTrue(configJson.isFullscreen());
        
        configJson.setFullscreen(false);
        assertFalse(configJson.isFullscreen());
    }
    
    /**
     * Test setting and getting volume property
     */
    @Test
    public void testVolumeGetterSetter() {
        assertEquals(0, configJson.getVolume());
        
        configJson.setVolume(50);
        assertEquals(50, configJson.getVolume());
        
        configJson.setVolume(100);
        assertEquals(100, configJson.getVolume());
        
        configJson.setVolume(0);
        assertEquals(0, configJson.getVolume());
    }
    
    /**
     * Test setting and getting ups property
     */
    @Test
    public void testUpsGetterSetter() {
        assertEquals(0, configJson.getUps());
        
        configJson.setUps(30);
        assertEquals(30, configJson.getUps());
        
        configJson.setUps(60);
        assertEquals(60, configJson.getUps());
    }
    
    /**
     * Test setting and getting fps property
     */
    @Test
    public void testFpsGetterSetter() {
        assertEquals(0, configJson.getFps());
        
        configJson.setFps(30);
        assertEquals(30, configJson.getFps());
        
        configJson.setFps(60);
        assertEquals(60, configJson.getFps());
    }
    
    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        configJson.setKeys(testKeys);
        configJson.setFullscreen(true);
        
        String expected = "ConfigJson{keys=" + testKeys + ", fullscreen=true}";
        assertEquals(expected, configJson.toString());
    }
    
    /**
     * Test key modification operations
     */
    @Test
    public void testKeyModification() {
        configJson.setKeys(testKeys);
        
        // Test adding a new key
        configJson.getKeys().put("JUMP", "SPACE");
        assertEquals(5, configJson.getKeys().size());
        assertEquals("SPACE", configJson.getKeys().get("JUMP"));
        
        // Test modifying an existing key
        configJson.getKeys().put("UP", "Z");
        assertEquals(5, configJson.getKeys().size());
        assertEquals("Z", configJson.getKeys().get("UP"));
        
        // Test removing a key
        configJson.getKeys().remove("LEFT");
        assertEquals(4, configJson.getKeys().size());
        assertNull(configJson.getKeys().get("LEFT"));
    }
    
    /**
     * Test modifying the keys map
     */
    @Test
    public void testKeyMapModification() {
        configJson.setKeys(testKeys);
        
        // Get the map and modify it
        HashMap<String, String> keys = configJson.getKeys();
        keys.put("EXTRA", "X");
        
        // Verify the changes are reflected in the object
        assertEquals(5, configJson.getKeys().size());
        assertEquals("X", configJson.getKeys().get("EXTRA"));
    }
    
    /**
     * Test extreme values for numeric properties
     */
    @Test
    public void testExtremeValues() {
        // Test with extreme values
        configJson.setVolume(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, configJson.getVolume());
        
        configJson.setVolume(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, configJson.getVolume());
        
        configJson.setUps(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, configJson.getUps());
        
        configJson.setFps(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, configJson.getFps());
    }
}