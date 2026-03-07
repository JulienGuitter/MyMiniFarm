package fr.eseo.e3a.myminifarm.model;

import fr.eseo.e3a.myminifarm.controller.GameController;
import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.model.farmElement.*;
import fr.eseo.e3a.myminifarm.model.type.Direction;
import fr.eseo.e3a.myminifarm.utils.Movements;
import fr.eseo.e3a.myminifarm.utils.Point2D;
import fr.eseo.e3a.myminifarm.utils.Vector2;

import java.util.ArrayList;

public class Player {

    private Vector2 pos;
    private Vector2 speed;
    private Direction direction;
    private int invBarIndex;
    private boolean isMoving;
    private boolean isInteracting;
    private int money;
    private Inventory inventory;
    /**
     * The name of the player.
     */
    private String name;

    private boolean hasChange;

    public Player() {
        this.pos = new Vector2(0, 0);
        this.speed = new Vector2(0, 0);
        this.direction = Direction.DOWN;
        this.invBarIndex = 0;
        this.isMoving = false;
        this.isInteracting = false;
        this.money = 20;
        this.inventory = new Inventory(9);
        this.name = "Bernard";

        this.hasChange = false;


        inventory.add(new Tool(FarmEnums.ToolType.HOE), 0);
        inventory.add(new Tool(FarmEnums.ToolType.PICKAXE), 1);
        inventory.add(new Tool(FarmEnums.ToolType.WATERING_CAN), 2);
    }

    public void update() {
        this.pos = Movements.updatePos(pos, speed);
        this.isMoving = speed.x() != 0 || speed.y() != 0;
    }

    public void updateSpeed(int x, int y) {
        this.speed = Movements.updateSpeed(speed, x, y);

        switch (x) {
            case -1 -> this.direction = Direction.LEFT;
            case 1 -> this.direction = Direction.RIGHT;
        }
        switch (y) {
            case -1 -> this.direction = Direction.UP;
            case 1 -> this.direction = Direction.DOWN;
        }
    }

    public void resetSpeedX() {
        this.speed = new Vector2(0, speed.y());
    }

    public void resetSpeedY() {
        this.speed = new Vector2(speed.x(), 0);
    }

    public void changeInvBarIndex(int sens) {
        if (sens > 0) {
            this.invBarIndex++;
        } else {
            this.invBarIndex--;
        }

        if (this.invBarIndex < 0) {
            this.invBarIndex = GameController.INVBAR_SIZE - 1;
        } else if (this.invBarIndex >= GameController.INVBAR_SIZE) {
            this.invBarIndex = 0;
        }
        isInteracting = true;
    }

    public void setInvBarIndex(int index) {
        if (index >= 0 && index < GameController.INVBAR_SIZE) {
            this.invBarIndex = index;
        }
    }

    public void tryAddItemInventory(Item item, Vector2 pos) {
        Map map = GameLoop.getInstance().getMap();
        if (item != null) {
            if (!map.isInPerimeter(this.pos, pos)) {
                map.dropItem(pos, item);
                map.setDropItemChange(true);
                return;
            }
            if (!inventory.add(item)) {
                map.dropItem(pos, item);
                map.setDropItemChange(true);
            }
        }
    }


    public Vector2 getNextPos() {
        return Movements.updatePos(pos, speed);
    }

    /**
     * Getter of player direction
     *
     * @return a Direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Getter of player position
     *
     * @return a Vector2
     */
    public Vector2 getPos() {
        return pos;
    }

    /**
     * Setter of player position
     *
     * @param pos a Vector2
     */
    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    /**
     * Getter of isMoving
     *
     * @return a boolean
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Getter of isInteracting
     *
     * @return a boolean
     */
    public boolean isInteracting() {
        return isInteracting;
    }

    /**
     * Setter of isInteracting
     *
     * @param isInteracting a boolean
     */
    public void setInteracting(boolean isInteracting) {
        this.isInteracting = isInteracting;
    }

    /**
     * Getter of invBarIndex
     *
     * @return an int
     */
    public int getInvBarIndex() {
        return invBarIndex;
    }

    /**
     * Getter of player money
     *
     * @return an int
     */
    public int getMoney() {
        return money;
    }

    public void addMoney(int amount) {
        this.money += amount;
        hasChange = true;
    }

    public void deductMoney(int amount) {
        this.money -= amount;
        hasChange = true;
    }

    /**
     * Getter of player inventory
     *
     * @return an Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Getter of current item
     *
     * @return an Item
     */
    public Item getCurrentItem() {
        return inventory.getItem(invBarIndex);
    }

    /**
     * Getter of hasChange
     *
     * @return a boolean
     */
    public boolean hasChange() {
        return hasChange;
    }

    /**
     * Setter of hasChange
     *
     * @param hasChange a boolean
     */
    public void setChanged(boolean hasChange) {
        this.hasChange = hasChange;
    }

    /**
     * Setter for Player Name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for Player Name
     *
     * @return name a String
     */
    public String getName() {
        return this.name;
    }


}
