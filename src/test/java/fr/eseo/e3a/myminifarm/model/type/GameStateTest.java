package fr.eseo.e3a.myminifarm.model.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GameState enum
 * 
 * These tests verify:
 * - Enum values are correctly defined
 * - Enum operations work as expected
 * - State transitions can be represented correctly
 */
public class GameStateTest {

    /**
     * Test that all expected GameState values exist
     */
    @Test
    public void testGameStateValues() {
        // Verify all expected values exist
        assertEquals(7, GameState.values().length);
        assertNotNull(GameState.MAIN_MENU);
        assertNotNull(GameState.START_GAME);
        assertNotNull(GameState.PLAYING);
        assertNotNull(GameState.MACHINE_MENU);
        assertNotNull(GameState.PAUSE);
        assertNotNull(GameState.SETTINGS);
        assertNotNull(GameState.QUIT);
    }
    
    /**
     * Test enum valueOf operation
     */
    @Test
    public void testValueOf() {
        assertEquals(GameState.MAIN_MENU, GameState.valueOf("MAIN_MENU"));
        assertEquals(GameState.START_GAME, GameState.valueOf("START_GAME"));
        assertEquals(GameState.PLAYING, GameState.valueOf("PLAYING"));
        assertEquals(GameState.MACHINE_MENU, GameState.valueOf("MACHINE_MENU"));
        assertEquals(GameState.PAUSE, GameState.valueOf("PAUSE"));
        assertEquals(GameState.SETTINGS, GameState.valueOf("SETTINGS"));
        assertEquals(GameState.QUIT, GameState.valueOf("QUIT"));
    }
    
    /**
     * Test that valueOf throws an exception for non-existent states
     */
    @Test
    public void testValueOfWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> GameState.valueOf("INVALID_STATE"));
        assertThrows(IllegalArgumentException.class, () -> GameState.valueOf(""));
        assertThrows(NullPointerException.class, () -> GameState.valueOf(null));
    }
    
    /**
     * Test ordinal values
     */
    @Test
    public void testOrdinals() {
        assertEquals(0, GameState.MAIN_MENU.ordinal());
        assertEquals(1, GameState.START_GAME.ordinal());
        assertEquals(2, GameState.PLAYING.ordinal());
        assertEquals(3, GameState.MACHINE_MENU.ordinal());
        assertEquals(4, GameState.PAUSE.ordinal());
        assertEquals(5, GameState.SETTINGS.ordinal());
        assertEquals(6, GameState.QUIT.ordinal());
    }
    
    /**
     * Test enum comparison
     */
    @Test
    public void testComparison() {
        assertTrue(GameState.MAIN_MENU.compareTo(GameState.PLAYING) < 0);
        assertTrue(GameState.PLAYING.compareTo(GameState.MAIN_MENU) > 0);
        assertEquals(0, GameState.PAUSE.compareTo(GameState.PAUSE));
    }
    
    /**
     * Test name method
     */
    @Test
    public void testName() {
        assertEquals("MAIN_MENU", GameState.MAIN_MENU.name());
        assertEquals("START_GAME", GameState.START_GAME.name());
        assertEquals("PLAYING", GameState.PLAYING.name());
        assertEquals("MACHINE_MENU", GameState.MACHINE_MENU.name());
        assertEquals("PAUSE", GameState.PAUSE.name());
        assertEquals("SETTINGS", GameState.SETTINGS.name());
        assertEquals("QUIT", GameState.QUIT.name());
    }
    
    /**
     * Test toString method
     */
    @Test
    public void testToString() {
        assertEquals("MAIN_MENU", GameState.MAIN_MENU.toString());
        assertEquals("START_GAME", GameState.START_GAME.toString());
        assertEquals("PLAYING", GameState.PLAYING.toString());
        assertEquals("MACHINE_MENU", GameState.MACHINE_MENU.toString());
        assertEquals("PAUSE", GameState.PAUSE.toString());
        assertEquals("SETTINGS", GameState.SETTINGS.toString());
        assertEquals("QUIT", GameState.QUIT.toString());
    }
    
    /**
     * Test typical state transitions using a mock state machine
     */
    @Test
    public void testTypicalStateTransitions() {
        // Simulate a game state machine
        GameState currentState = GameState.MAIN_MENU;
        
        // Start game transition
        currentState = GameState.START_GAME;
        assertEquals(GameState.START_GAME, currentState);
        
        // Move to playing
        currentState = GameState.PLAYING;
        assertEquals(GameState.PLAYING, currentState);
        
        // Pause the game
        currentState = GameState.PAUSE;
        assertEquals(GameState.PAUSE, currentState);
        
        // Return to playing
        currentState = GameState.PLAYING;
        assertEquals(GameState.PLAYING, currentState);
        
        // Open machine menu
        currentState = GameState.MACHINE_MENU;
        assertEquals(GameState.MACHINE_MENU, currentState);
        
        // Return to playing
        currentState = GameState.PLAYING;
        assertEquals(GameState.PLAYING, currentState);
        
        // Open settings
        currentState = GameState.SETTINGS;
        assertEquals(GameState.SETTINGS, currentState);
        
        // Return to main menu
        currentState = GameState.MAIN_MENU;
        assertEquals(GameState.MAIN_MENU, currentState);
        
        // Quit the game
        currentState = GameState.QUIT;
        assertEquals(GameState.QUIT, currentState);
    }
}