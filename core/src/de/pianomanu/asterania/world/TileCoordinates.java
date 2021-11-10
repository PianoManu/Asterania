package de.pianomanu.asterania.world;

public class TileCoordinates {
    private int x;
    private int y;

    public TileCoordinates(int x, int y) {
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

    public WorldSectionCoordinates toWorldSectionCoordinates() {
        return new WorldSectionCoordinates((int) Math.floor(Math.floor(this.x) / WorldSection.SECTION_SIZE), (int) Math.floor(Math.floor(this.y) / WorldSection.SECTION_SIZE));
    }

    public TileCoordinates copy() {
        return new TileCoordinates(this.x, this.y);
    }

    public TileCoordinates moveUp() {
        return moveUp(1);
    }

    public TileCoordinates moveUp(int steps) {
        this.y += steps;
        return this;
    }

    public TileCoordinates moveDown() {
        return moveDown(1);
    }

    public TileCoordinates moveDown(int steps) {
        this.y -= steps;
        return this;
    }

    public TileCoordinates moveRight() {
        return moveRight(1);
    }

    public TileCoordinates moveRight(int steps) {
        this.x += steps;
        return this;
    }

    public TileCoordinates moveLeft() {
        return moveLeft(1);
    }

    public TileCoordinates moveLeft(int steps) {
        this.x -= steps;
        return this;
    }
}
