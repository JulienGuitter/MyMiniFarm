package fr.eseo.e3a.myminifarm.model.type;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the KeyAction enum
 * 
 * These tests verify:
 * - Enum values are correctly defined with proper action strings
 * - The getAction method returns the correct action string
 * - The fromString method correctly retrieves an enum value from its action string
 * - Error handling for invalid action strings
 */
public class KeyActionTest {

    /**
     * Test that all expected KeyAction values exist
     */
    @Test
    public void testKeyActionValues() {
        // Verify all expected values exist
        assertEquals(5, KeyAction.values().length);
        assertNotNull(KeyAction.MOVE_UP);
        assertNotNull(KeyAction.MOVE_DOWN);
        assertNotNull(KeyAction.MOVE_LEFT);
        assertNotNull(KeyAction.MOVE_RIGHT);
        assertNotNull(KeyAction.SHOP);
    }
    
    /**
     * Test getAction method returns correct action string for each enum value
     */
    @Test
    public void testGetAction() {
        assertEquals("Move Up", KeyAction.MOVE_UP.getAction());
        assertEquals("Move Down", KeyAction.MOVE_DOWN.getAction());
        assertEquals("Move Left", KeyAction.MOVE_LEFT.getAction());
        assertEquals("Move Right", KeyAction.MOVE_RIGHT.getAction());
        assertEquals("Shop Menu", KeyAction.SHOP.getAction());
    }
    
    /**
     * Test fromString method with valid action strings
     * Uses parameterized test to test multiple inputs
     */
    @ParameterizedTest
    @MethodSource("provideValidActionStrings")
    public void testFromStringValid(String actionString, KeyAction expectedKeyAction) {
        assertEquals(expectedKeyAction, KeyAction.fromString(actionString));
    }
    
    /**
     * Provide test data for valid action strings
     */
    private static Stream<Arguments> provideValidActionStrings() {
        return Stream.of(
            Arguments.of("Move Up", KeyAction.MOVE_UP),
            Arguments.of("move up", KeyAction.MOVE_UP),
            Arguments.of("MOVE UP", KeyAction.MOVE_UP),
            Arguments.of("Move Down", KeyAction.MOVE_DOWN),
            Arguments.of("Move Left", KeyAction.MOVE_LEFT),
            Arguments.of("Move Right", KeyAction.MOVE_RIGHT),
            Arguments.of("Shop Menu", KeyAction.SHOP)
        );
    }
    
    /**
     * Test fromString method with invalid action strings
     * Should throw IllegalArgumentException
     */
    @ParameterizedTest
    @ValueSource(strings = {"Invalid Action", "", "Move", "Shop", "Jump"})
    public void testFromStringInvalid(String invalidActionString) {
        assertThrows(IllegalArgumentException.class, () -> KeyAction.fromString(invalidActionString));
    }
    
    /**
     * Test fromString method with null action string
     * Should throw NullPointerException
     */
    @Test
    public void testFromStringNull() {
        assertThrows(IllegalArgumentException.class, () -> KeyAction.fromString(null));
    }
    
    /**
     * Test enum comparison
     */
    @Test
    public void testComparison() {
        assertTrue(KeyAction.MOVE_UP.compareTo(KeyAction.MOVE_DOWN) < 0);
        assertTrue(KeyAction.SHOP.compareTo(KeyAction.MOVE_RIGHT) > 0);
        assertEquals(0, KeyAction.MOVE_LEFT.compareTo(KeyAction.MOVE_LEFT));
    }
    
    /**
     * Test name method
     */
    @Test
    public void testName() {
        assertEquals("MOVE_UP", KeyAction.MOVE_UP.name());
        assertEquals("MOVE_DOWN", KeyAction.MOVE_DOWN.name());
        assertEquals("MOVE_LEFT", KeyAction.MOVE_LEFT.name());
        assertEquals("MOVE_RIGHT", KeyAction.MOVE_RIGHT.name());
        assertEquals("SHOP", KeyAction.SHOP.name());
    }
    
    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        assertEquals("MOVE_UP", KeyAction.MOVE_UP.toString());
        assertEquals("MOVE_DOWN", KeyAction.MOVE_DOWN.toString());
        assertEquals("MOVE_LEFT", KeyAction.MOVE_LEFT.toString());
        assertEquals("MOVE_RIGHT", KeyAction.MOVE_RIGHT.toString());
        assertEquals("SHOP", KeyAction.SHOP.toString());
    }
    
    /**
     * Test case insensitivity of fromString method
     */
    @Test
    public void testFromStringCaseInsensitivity() {
        assertEquals(KeyAction.MOVE_UP, KeyAction.fromString("MOVE UP"));
        assertEquals(KeyAction.MOVE_UP, KeyAction.fromString("move up"));
        assertEquals(KeyAction.MOVE_UP, KeyAction.fromString("Move Up"));
        assertEquals(KeyAction.MOVE_UP, KeyAction.fromString("mOvE uP"));
    }
    
    /**
     * Test consistency between getAction and fromString methods
     */
    @Test
    public void testGetActionAndFromStringConsistency() {
        for (KeyAction keyAction : KeyAction.values()) {
            String action = keyAction.getAction();
            KeyAction fromStringResult = KeyAction.fromString(action);
            assertEquals(keyAction, fromStringResult, 
                    "fromString should return the same enum value when given the result of getAction");
        }
    }
}