package de.pianomanu.asterania.world.tile;

public class BasicTile extends Tile {
    public BasicTile(String name, TileMaterial tileMaterial, TileSettings settings) {
        super(name, tileMaterial, settings);
    }

    public BasicTile(String name, TileMaterial tileMaterial, TileSettings settings, LayerType layerType) {
        super(name, tileMaterial, settings, layerType);
    }

    public BasicTile(String name, TileMaterial tileMaterial, TileSettings settings, LayerType layerType, int numberOfDifferentTextures) {
        super(name, tileMaterial, settings, layerType);
        this.numberOfDifferentTextures = numberOfDifferentTextures;
    }
}
