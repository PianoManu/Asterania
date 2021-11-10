package de.pianomanu.asterania.entities.hitboxes;

import de.pianomanu.asterania.world.coordinates.EntityCoordinates;

public class SimpleHitbox {
    public EntityCoordinates start;
    public EntityCoordinates end;

    public SimpleHitbox(EntityCoordinates start, EntityCoordinates end) {
        this.start = start;
        this.end = end;
    }

    public EntityCoordinates getStart() {
        return this.start;
    }

    public EntityCoordinates getEnd() {
        return this.end;
    }

    public float getArea() {
        return (this.end.x - this.start.x) * (this.end.y - this.start.y);
    }

    public SimpleHitbox move(EntityCoordinates vec) {
        this.start.add(vec);
        this.end.add(vec);
        return this;
    }

    public SimpleHitbox move(float x, float y) {
        this.start.x += x;
        this.start.y += y;
        this.end.x += x;
        this.end.y += y;
        return this;
    }

    public float getWidth() {
        return this.end.x - this.start.x;
    }

    public float getHeight() {
        return this.end.y - this.start.y;
    }

    public EntityCoordinates distanceFromStartToEnd() {
        return new EntityCoordinates(this.end.x - this.start.x, this.end.y - this.start.y);
    }
}
