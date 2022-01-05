package de.pianomanu.asterania.world.tile;

public class BasicTile extends Tile {
    public BasicTile(String name, TileSettings settings) {
        super(name, settings);
    }

    public BasicTile(String name, TileSettings settings, int numberOfDifferentTextures) {
        super(name, settings);
        this.numberOfDifferentTextures = numberOfDifferentTextures;
    }
}
