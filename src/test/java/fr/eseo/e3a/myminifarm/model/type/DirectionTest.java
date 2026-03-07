package fr.eseo.e3a.myminifarm.model.type;

import fr.eseo.e3a.myminifarm.utils.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Direction enum
 *
 * This class verifies that:
 * - Each Direction has the correct index value
 * - Each Direction has the correct vector representation
 * - All expected Direction values are present
 */
public class DirectionTest {

    @Test
    public void testDirectionValues() {
        // Verify all expected directions exist
        Direction[] directions = Direction.values();
        assertEquals(4, directions.length);
        assertTrue(containsDirection(directions, Direction.DOWN));
        assertTrue(containsDirection(directions, Direction.RIGHT));
        assertTrue(containsDirection(directions, Direction.UP));
        assertTrue(containsDirection(directions, Direction.LEFT));
    }

    @Test
    public void testDirectionIndices() {
        // Test each direction has the correct index
        assertEquals(0, Direction.DOWN.getIndex());
        assertEquals(4, Direction.RIGHT.getIndex());
        assertEquals(8, Direction.UP.getIndex());
        assertEquals(12, Direction.LEFT.getIndex());
    }

    @Test
    public void testDirectionVectors() {
        // Test each direction has the correct vector
        assertEquals(new Vector2(0, -1), Direction.DOWN.getVect());
        assertEquals(new Vector2(1, 0), Direction.RIGHT.getVect());
        assertEquals(new Vector2(0, 1), Direction.UP.getVect());
        assertEquals(new Vector2(-1, 0), Direction.LEFT.getVect());
    }

    /**
     * Helper method to check if a Direction exists in an array
     * @param directions Array of Direction to search in
     * @param target Direction to search for
     * @return true if found, false otherwise
     */
    private boolean containsDirection(Direction[] directions, Direction target) {
        for (Direction dir : directions) {
            if (dir == target) {
                return true;
            }
        }
        return false;
    }
}