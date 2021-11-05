package de.pianomanu.asterania.world;

import com.badlogic.gdx.math.Vector2;

public class EntityCoordinates {
    public float x;
    public float y;

    public EntityCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public EntityCoordinates() {
        this.x = 0;
        this.y = 0;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static EntityCoordinates getEntityCoordinates(float x, float y) {
        return new EntityCoordinates(x, y);
    }

    public static EntityCoordinates getEntityCoordinates(Vector2 vec) {
        return new EntityCoordinates(vec.x, vec.y);
    }

    public Coordinates toTileCoordinates() {
        return new Coordinates((int) Math.floor(x), (int) Math.floor(y));
    }
}
