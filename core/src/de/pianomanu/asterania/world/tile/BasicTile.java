package de.pianomanu.asterania.world.tile;

public class BasicTile extends Tile {
    public BasicTile(String name, TileSettings settings) {
        super(name, settings);
    }

    public BasicTile(String name, TileSettings settings, TileType tileType) {
        super(name, settings, tileType);
    }

    public BasicTile(String name, TileSettings settings, TileType tileType, int numberOfDifferentTextures) {
        super(name, settings, tileType);
        this.numberOfDifferentTextures = numberOfDifferentTextures;
    }
}
