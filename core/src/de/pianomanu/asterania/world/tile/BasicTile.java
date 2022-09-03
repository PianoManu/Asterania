package de.pianomanu.asterania.world.tile;

public class BasicTile extends Tile {
    public BasicTile(String name, TileSettings settings) {
        super(name, settings);
    }

    public BasicTile(String name, TileSettings settings, LayerType layerType) {
        super(name, settings, layerType);
    }

    public BasicTile(String name, TileSettings settings, LayerType layerType, int numberOfDifferentTextures) {
        super(name, settings, layerType);
        this.numberOfDifferentTextures = numberOfDifferentTextures;
    }
}
