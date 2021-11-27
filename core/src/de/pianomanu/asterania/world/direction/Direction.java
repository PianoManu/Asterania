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
