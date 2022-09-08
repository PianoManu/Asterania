package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;

public class BasicDecorationLayerTile extends BasicTile {
    public BasicDecorationLayerTile(String name, TileMaterial tileMaterial, TileSettings settings, int numbersOfDifferentTextures) {
        super(name, tileMaterial, settings, LayerType.DECORATION, numbersOfDifferentTextures);
    }

    public BasicDecorationLayerTile(String name, TileMaterial tileMaterial, TileSettings settings) {
        super(name, tileMaterial, settings, LayerType.DECORATION);
    }

    @Override
    public boolean runPrePlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        super.runPrePlacementEvents(world, player, tileCoordinates);
        Tile backgroundTile = world.getTile(tileCoordinates);
        return TileMaterials.isSolid(backgroundTile.tileMaterial);
    }
}
