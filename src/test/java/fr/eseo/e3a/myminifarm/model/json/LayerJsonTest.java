package fr.eseo.e3a.myminifarm.model.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the LayerJson class
 * 
 * These tests verify the functionality of the LayerJson class including:
 * - Getting and setting properties
 * - Proper toString() method implementation
 */
public class LayerJsonTest {

    private LayerJson layerJson;
    private List<Integer> testData;
    
    /**
     * Setup method that runs before each test
     */
    @BeforeEach
    public void setUp() {
        layerJson = new LayerJson();
        testData = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    }
    
    /**
     * Test default initialization
     */
    @Test
    public void testDefaultInitialization() {
        assertNull(layerJson.getData());
        assertEquals(0, layerJson.getHeight());
        assertNull(layerJson.getName());
        assertEquals(0.0, layerJson.getOpacity());
        assertFalse(layerJson.isVisible());
        assertEquals(0, layerJson.getWidth());
        assertEquals(0, layerJson.getX());
        assertEquals(0, layerJson.getY());
    }
    
    /**
     * Test setting and getting data
     */
    @Test
    public void testDataGetterSetter() {
        layerJson.setData(testData);
        
        assertEquals(testData, layerJson.getData());
        assertEquals(10, layerJson.getData().size());
        assertEquals(0, layerJson.getData().get(0));
        assertEquals(9, layerJson.getData().get(9));
    }
    
    /**
     * Test setting and getting height
     */
    @Test
    public void testHeightGetterSetter() {
        assertEquals(0, layerJson.getHeight());
        
        layerJson.setHeight(10);
        assertEquals(10, layerJson.getHeight());
        
        layerJson.setHeight(20);
        assertEquals(20, layerJson.getHeight());
    }
    
    /**
     * Test setting and getting name
     */
    @Test
    public void testNameGetterSetter() {
        assertNull(layerJson.getName());
        
        layerJson.setName("Ground");
        assertEquals("Ground", layerJson.getName());
        
        layerJson.setName("Objects");
        assertEquals("Objects", layerJson.getName());
    }
    
    /**
     * Test setting and getting opacity
     */
    @Test
    public void testOpacityGetterSetter() {
        assertEquals(0.0, layerJson.getOpacity());
        
        layerJson.setOpacity(0.5);
        assertEquals(0.5, layerJson.getOpacity());
        
        layerJson.setOpacity(1.0);
        assertEquals(1.0, layerJson.getOpacity());
    }
    
    /**
     * Test setting and getting visible
     */
    @Test
    public void testVisibleGetterSetter() {
        assertFalse(layerJson.isVisible());
        
        layerJson.setVisible(true);
        assertTrue(layerJson.isVisible());
        
        layerJson.setVisible(false);
        assertFalse(layerJson.isVisible());
    }
    
    /**
     * Test setting and getting width
     */
    @Test
    public void testWidthGetterSetter() {
        assertEquals(0, layerJson.getWidth());
        
        layerJson.setWidth(15);
        assertEquals(15, layerJson.getWidth());
        
        layerJson.setWidth(30);
        assertEquals(30, layerJson.getWidth());
    }
    
    /**
     * Test setting and getting x
     */
    @Test
    public void testXGetterSetter() {
        assertEquals(0, layerJson.getX());
        
        layerJson.setX(5);
        assertEquals(5, layerJson.getX());
        
        layerJson.setX(-3);
        assertEquals(-3, layerJson.getX());
    }
    
    /**
     * Test setting and getting y
     */
    @Test
    public void testYGetterSetter() {
        assertEquals(0, layerJson.getY());
        
        layerJson.setY(8);
        assertEquals(8, layerJson.getY());
        
        layerJson.setY(-2);
        assertEquals(-2, layerJson.getY());
    }
    
    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        layerJson.setData(testData);
        layerJson.setHeight(10);
        layerJson.setName("Ground");
        layerJson.setOpacity(1.0);
        layerJson.setVisible(true);
        layerJson.setWidth(15);
        layerJson.setX(0);
        layerJson.setY(0);
        
        String expected = "Layer{ \n" +
                "data=" + testData + ", \n" +
                "height=10, \n" +
                "name='Ground', \n" +
                "opacity=1.0, \n" +
                "visible=true, \n" +
                "width=15, \n" +
                "x=0, \n" +
                "y=0\n" +
                '}';
        assertEquals(expected, layerJson.toString());
    }
    
    /**
     * Test data modification operations
     */
    @Test
    public void testDataModification() {
        layerJson.setData(testData);
        
        // Test adding a new element
        layerJson.getData().add(10);
        assertEquals(11, layerJson.getData().size());
        assertEquals(10, layerJson.getData().get(10));
        
        // Test modifying an existing element
        layerJson.getData().set(0, 100);
        assertEquals(100, layerJson.getData().get(0));
        
        // Test removing an element
        layerJson.getData().remove(5);
        assertEquals(10, layerJson.getData().size());
        assertEquals(6, layerJson.getData().get(5));
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
        LayerJson layer = new LayerJson();
        layer.setName("TestLayer");
        assertEquals("TestLayer", layer.getName());
        
        // Note: The actual behavior of JsonIgnoreProperties would be tested
        // in integration tests with actual JSON deserialization
    }
    
    /**
     * Test extreme values for numeric properties
     */
    @Test
    public void testExtremeValues() {
        // Test with extreme integer values
        layerJson.setHeight(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, layerJson.getHeight());
        
        layerJson.setWidth(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, layerJson.getWidth());
        
        // Test with extreme double values
        layerJson.setOpacity(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, layerJson.getOpacity());
        
        layerJson.setOpacity(0.000000001);
        assertEquals(0.000000001, layerJson.getOpacity());
    }
    
    /**
     * Test with empty data list
     */
    @Test
    public void testEmptyDataList() {
        List<Integer> emptyList = new ArrayList<>();
        layerJson.setData(emptyList);
        
        assertEquals(emptyList, layerJson.getData());
        assertEquals(0, layerJson.getData().size());
        assertTrue(layerJson.getData().isEmpty());
    }
}