package de.pianomanu.asterania.world.direction;

public enum Direction {
    DOWN, UP, LEFT, RIGHT;

    public boolean isHorizontal() {
        return this == LEFT || this == RIGHT;
    }

    public boolean isVertical() {
        return this == DOWN || this == UP;
    }

    @Override
    public String toString() {
        switch (this) {
            case DOWN:
                return "down";
            case UP:
                return "up";
            case LEFT:
                return "left";
            case RIGHT:
                return "right";
        }
        return null;
    }
}
