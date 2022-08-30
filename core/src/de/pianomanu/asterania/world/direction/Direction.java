package de.pianomanu.asterania.world.direction;

import de.pianomanu.asterania.config.KeyConfig;

public enum Direction {
    DOWN, UP, LEFT, RIGHT;

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }

    public boolean isVertical() {
        return this == DOWN || this == UP;
    }

    public static Direction getDirectionFromKey(int key) {
        return switch (key) {
            case KeyConfig.MOVE_RIGHT -> RIGHT;
            case KeyConfig.MOVE_LEFT -> LEFT;
            case KeyConfig.MOVE_UP -> UP;
            case KeyConfig.MOVE_DOWN -> DOWN;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case DOWN -> "down";
            case UP -> "up";
            case LEFT -> "left";
            case RIGHT -> "right";
        };
    }

    public int getKeyFromDirection() {
        return switch (this) {
            case RIGHT -> KeyConfig.MOVE_RIGHT;
            case LEFT -> KeyConfig.MOVE_LEFT;
            case UP -> KeyConfig.MOVE_UP;
            case DOWN -> KeyConfig.MOVE_DOWN;
        };
    }

    public static Direction toDirection(String direction) {
        if (direction.equals("down") || direction.equals("DOWN"))
            return DOWN;
        if (direction.equals("up") || direction.equals("UP"))
            return UP;
        if (direction.equals("left") || direction.equals("LEFT"))
            return LEFT;
        if (direction.equals("right") || direction.equals("RIGHT"))
            return RIGHT;
        return null;
    }
}
