package de.pianomanu.asterania.world;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EntityCoordinates toEntityCoordinates() {
        return new EntityCoordinates(x + 0.5f, y + 0.5f);
    }
}
