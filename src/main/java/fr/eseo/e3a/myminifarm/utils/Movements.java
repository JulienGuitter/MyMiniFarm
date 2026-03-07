package fr.eseo.e3a.myminifarm.utils;

import fr.eseo.e3a.myminifarm.controller.UIController;

public class Movements {
    private final static float MAX_SPEED = 0.02f;
    private final static float MIN_SPEED = -MAX_SPEED;
    private final static float SPEED_INCREMENT = 0.001f;
    private final static float SPEED_DECREMENT = 0.001f;

    public static Vector2 updatePos(Vector2 pos, Vector2 speed){
        return new Vector2(pos.x() + speed.x(), pos.y() + speed.y());
    }

    /**
     * Update the speed of the entity based on the current speed and the direction of movement.
     *
     * @param speed the current speed of the entity
     * @param x the x orientation -1 for left, 1 for right, 0 for none
     * @param y the y orientation -1 for up, 1 for down, 0 for none
     *
     * @return the new speed of the entity
     */
    public static Vector2 updateSpeed(Vector2 speed, int x, int y){
        // Increment the speed based on the direction with a speed of 0.1 and a max of 1. If x or y is 0, the speed decreases
        double newX = speed.x();
        double newY = speed.y();

        if(x == 0){
            if(newX > 0){
                newX -= SPEED_DECREMENT;
            } else if(newX < 0){
                newX += SPEED_DECREMENT;
            }
            if( newX > -SPEED_DECREMENT && newX < SPEED_DECREMENT){
                newX = 0;
            }
        } else {
            newX += x*SPEED_INCREMENT;
        }

        if(y == 0){
            if(newY > 0){
                newY -= SPEED_DECREMENT;
            } else if(newY < 0){
                newY += SPEED_DECREMENT;
            }
            if( newY > -SPEED_DECREMENT && newY < SPEED_DECREMENT){
                newY = 0;
            }
        } else {
            newY += y*SPEED_INCREMENT;
        }

        // Clamp the speed to the max speed
        if(newX > MAX_SPEED){
            newX = MAX_SPEED;
        } else if(newX < MIN_SPEED){
            newX = MIN_SPEED;
        }
        if(newY > MAX_SPEED){
            newY = MAX_SPEED;
        } else if(newY < MIN_SPEED){
            newY = MIN_SPEED;
        }
        return new Vector2(newX, newY);
    }


    public static Point2D cellPos(Vector2 pos){
        return new Point2D((int)pos.x(), (int)pos.y());
    }
}
