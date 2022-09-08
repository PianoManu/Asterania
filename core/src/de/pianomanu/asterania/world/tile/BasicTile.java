package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.world.tile.tileutils.LayerType;
import de.pianomanu.asterania.world.tile.tileutils.TileMaterial;
import de.pianomanu.asterania.world.tile.tileutils.TileSettings;

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
