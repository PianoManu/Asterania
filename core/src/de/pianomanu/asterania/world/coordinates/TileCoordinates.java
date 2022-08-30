package de.pianomanu.asterania.world.coordinates;

import de.pianomanu.asterania.world.direction.Direction;
import de.pianomanu.asterania.world.worldsections.WorldSection;

public class TileCoordinates {
    public int x;
    public int y;

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

    public TileCoordinates copy(TileCoordinates coordinates) {
        this.x = coordinates.x;
        this.y = coordinates.y;
        return this;
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

    /**
     * Moves this coordinates instance by one step in the given direction. If
     * the direction is <b>null</b>, nothing changes.
     *
     * @param direction the direction in which the coordinates should be moved.
     * @return the moved coordinates instance.
     */
    public TileCoordinates move(Direction direction) {
        return move(direction, 1);
    }

    /**
     * Moves this coordinates instance by the given steps in the given
     * direction. If the direction is <b>null</b>, nothing changes.
     *
     * @param direction the direction in which the coordinates should be moved.
     * @param steps     the amount of steps to move in the given direction.
     * @return the moved coordinates instance.
     */
    public TileCoordinates move(Direction direction, int steps) {
        if (direction != null) {
            return switch (direction) {
                case RIGHT -> this.moveRight(steps);
                case LEFT -> this.moveLeft(steps);
                case UP -> this.moveUp(steps);
                case DOWN -> this.moveDown(steps);
            };
        }
        return this;
    }
}
