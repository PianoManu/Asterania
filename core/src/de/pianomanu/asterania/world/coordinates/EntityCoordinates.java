package de.pianomanu.asterania.world.coordinates;

import com.badlogic.gdx.math.Vector2;
import de.pianomanu.asterania.world.worldsections.WorldSection;

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

    public EntityCoordinates(EntityCoordinates entityCoordinates) {
        this.x = entityCoordinates.x;
        this.y = entityCoordinates.y;
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

    public TileCoordinates toTileCoordinates() {
        return new TileCoordinates((int) Math.floor(x), (int) Math.floor(y));
    }

    public WorldSectionCoordinates toWorldSectionCoordinates() {
        return new WorldSectionCoordinates((int) Math.floor(this.x / WorldSection.SECTION_SIZE), (int) Math.floor(this.y / WorldSection.SECTION_SIZE));
    }

    public EntityCoordinates add(Vector2 vec) {
        this.x += vec.x;
        this.y += vec.y;
        return this;
    }

    public EntityCoordinates add(EntityCoordinates coordinates) {
        this.x += coordinates.x;
        this.y += coordinates.y;
        return this;
    }
}
