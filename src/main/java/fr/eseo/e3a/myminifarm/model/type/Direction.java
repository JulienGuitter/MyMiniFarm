package fr.eseo.e3a.myminifarm.model.type;

import fr.eseo.e3a.myminifarm.utils.Vector2;

public enum Direction {
    DOWN(0, new Vector2(0, -1)),
    RIGHT(4, new Vector2(1, 0)),
    UP(8, new Vector2(0, 1)),
    LEFT(12, new Vector2(-1, 0));

    private final int index;
    private final Vector2 vect;

    Direction(int index, Vector2 vect) {
        this.index = index;
        this.vect = vect;
    }

    public int getIndex() {
        return index;
    }

    public Vector2 getVect() {
        return vect;
    }
}
