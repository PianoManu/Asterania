package de.pianomanu.asterania.world.coordinates;

import de.pianomanu.asterania.world.worldsections.WorldSection;

public class WorldSectionCoordinates {
    public final int x;
    public final int y;

    public WorldSectionCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public TileCoordinates startToTileCoordinates() {
        return new TileCoordinates(this.x * WorldSection.SECTION_SIZE, this.y * WorldSection.SECTION_SIZE);
    }

    public TileCoordinates endToTileCoordinates() {
        return new TileCoordinates(this.x * WorldSection.SECTION_SIZE + WorldSection.SECTION_SIZE - 1, this.y * WorldSection.SECTION_SIZE + WorldSection.SECTION_SIZE - 1);
    }

    @Override
    public String toString() {
        return "[" + this.x + " | " + this.y + ']';
    }
}
