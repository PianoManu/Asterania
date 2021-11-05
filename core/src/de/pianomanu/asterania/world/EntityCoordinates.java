package de.pianomanu.asterania.world;

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

    public Coordinates toTileCoordinates() {
        return new Coordinates((int) Math.floor(x), (int) Math.floor(y));
    }
}
