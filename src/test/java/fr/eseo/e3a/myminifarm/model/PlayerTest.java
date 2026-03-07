package fr.eseo.e3a.myminifarm.model;

import fr.eseo.e3a.myminifarm.controller.GameController;
import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import fr.eseo.e3a.myminifarm.model.farmElement.Tool;
import fr.eseo.e3a.myminifarm.model.type.Direction;
import fr.eseo.e3a.myminifarm.utils.Point2D;
import fr.eseo.e3a.myminifarm.utils.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testInitialState() {
        // Verify initial state after construction
        assertEquals(new Vector2(0, 0), player.getPos());
        assertEquals(Direction.DOWN, player.getDirection());
        assertEquals(0, player.getInvBarIndex());
        assertFalse(player.isMoving());
        assertFalse(player.isInteracting());
        assertEquals(20, player.getMoney());
        assertEquals("Bernard", player.getName());
        assertFalse(player.hasChange());

        // Check initial inventory
        assertNotNull(player.getInventory());
        assertInstanceOf(Tool.class, player.getInventory().getItem(0));
        assertEquals(FarmEnums.ToolType.HOE, ((Tool) player.getInventory().getItem(0)).getTypeTools());
        assertInstanceOf(Tool.class, player.getInventory().getItem(1));
        assertEquals(FarmEnums.ToolType.PICKAXE, ((Tool) player.getInventory().getItem(1)).getTypeTools());
        assertInstanceOf(Tool.class, player.getInventory().getItem(2));
        assertEquals(FarmEnums.ToolType.WATERING_CAN, ((Tool) player.getInventory().getItem(2)).getTypeTools());
    }

    @Test
    void testUpdateSpeed() {
        // Test moving right
        player.updateSpeed(1, 0);
        assertEquals(Direction.RIGHT, player.getDirection());

        // Test moving left
        player.updateSpeed(-1, 0);
        assertEquals(Direction.LEFT, player.getDirection());

        // Test moving up
        player.updateSpeed(0, -1);
        assertEquals(Direction.UP, player.getDirection());

        // Test moving down
        player.updateSpeed(0, 1);
        assertEquals(Direction.DOWN, player.getDirection());

        // Test diagonal movement (should prioritize the last direction)
        player.updateSpeed(1, 1);
        assertEquals(Direction.DOWN, player.getDirection());
    }

    @Test
    void testUpdate() {
        // Mock the Movements.updatePos method since we can't directly test position updates
        player.updateSpeed(1, 0); // Set a non-zero speed
        player.update();
        assertTrue(player.isMoving());

        player.resetSpeedX();
        player.update();
        assertFalse(player.isMoving());
    }

    @Test
    void testResetSpeed() {
        player.updateSpeed(1, 1);

        player.resetSpeedX();
        player.update();
        assertTrue(player.isMoving()); // Only vertical speed now

        player.updateSpeed(1, 1);
        player.resetSpeedY();
        player.update();
        assertTrue(player.isMoving()); // Only vertical speed now


        player.updateSpeed(1, 1);
        player.resetSpeedX();
        player.resetSpeedY();
        player.update();
        assertFalse(player.isMoving()); // No speed now
    }

    @Test
    void testChangeInvBarIndex() {
        // Test increase
        player.changeInvBarIndex(1);
        assertEquals(1, player.getInvBarIndex());
        assertTrue(player.isInteracting());

        // Test wrapping around at the end
        for (int i = 0; i < GameController.INVBAR_SIZE; i++) {
            player.changeInvBarIndex(1);
        }
        assertEquals(1, player.getInvBarIndex());

        // Test decrease
        player.setInvBarIndex(5);
        player.changeInvBarIndex(-1);
        assertEquals(4, player.getInvBarIndex());

        // Test wrapping around at the beginning
        player.setInvBarIndex(0);
        player.changeInvBarIndex(-1);
        assertEquals(GameController.INVBAR_SIZE - 1, player.getInvBarIndex());
    }

    @Test
    void testMoneyTransactions() {
        assertEquals(20, player.getMoney());

        player.addMoney(500);
        assertEquals(520, player.getMoney());
        assertTrue(player.hasChange());

        player.setChanged(false);
        player.deductMoney(300);
        assertEquals(220, player.getMoney());
        assertTrue(player.hasChange());
    }

    @Test
    void testSetName() {
        player.setName("FarmerJoe");
        assertEquals("FarmerJoe", player.getName());
    }

    @Test
    void testTryAddItemInventory() {
        // This test requires mocking GameLoop which is a singleton
        // Complex to test without refactoring the code for testability
        // Below is a simplified approach

        GameLoop mockGameLoop = mock(GameLoop.class);
        Map mockMap = mock(Map.class);
        GameLoop.setInstance(mockGameLoop);

        //when(GameLoop.getInstance()).thenReturn(mockGameLoop);
        when(mockGameLoop.getMap()).thenReturn(mockMap);

        Item testItem = new Tool(FarmEnums.ToolType.HOE);
        Vector2 itemPos = new Vector2(10, 10);

        // Test when item is far away (not in perimeter)
        when(mockMap.isInPerimeter(any(Vector2.class), eq(itemPos))).thenReturn(false);
        player.tryAddItemInventory(testItem, itemPos);
        verify(mockMap).dropItem(itemPos, testItem);
        verify(mockMap).setDropItemChange(true);

        // Test when item is close and inventory has space
        when(mockMap.isInPerimeter(any(Vector2.class), eq(itemPos))).thenReturn(true);

        // Create a test with a partial mock of Player to control the inventory
        Player spyPlayer = spy(player);
        Inventory mockInventory = mock(Inventory.class);
        when(spyPlayer.getInventory()).thenReturn(mockInventory);
        when(mockInventory.add(testItem)).thenReturn(true);

        spyPlayer.tryAddItemInventory(testItem, itemPos);
        //verify(mockInventory).add(testItem);
        //verify(mockMap, times(1)).dropItem((Point2D) any(), any()); // Should not be called again

//        GameLoop.resetInstance();
    }

//    @Test
//    void testGetCurrentItem() {
//        Item item = new Tool(FarmEnums.ToolType.HOE);
//        Inventory mockInventory = mock(Inventory.class);
//        when(mockInventory.getItem(0)).thenReturn(item);
//
//        Player spyPlayer = spy(player);
//        when(spyPlayer.getInventory()).thenReturn(mockInventory);
//
//        assertEquals(item, spyPlayer.getCurrentItem());
//    }
}