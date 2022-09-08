package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.tileutils.*;

public class BasicDecorationLayerTile extends BasicTile {
    public BasicDecorationLayerTile(String name, TileMaterial tileMaterial, TileSettings settings, int numbersOfDifferentTextures) {
        super(name, tileMaterial, settings, LayerType.DECORATION, numbersOfDifferentTextures);
    }

    public BasicDecorationLayerTile(String name, TileMaterial tileMaterial, TileSettings settings) {
        super(name, tileMaterial, settings, LayerType.DECORATION);
    }

    @Override
    public PlacementEventInfo runPrePlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        super.runPrePlacementEvents(world, player, tileCoordinates);
        Tile backgroundTile = world.getTile(tileCoordinates);
        if (TileMaterials.isSolid(backgroundTile.tileMaterial)) {
            return PlacementEventInfos.TILE_CAN_BE_PLACED;
        }
        return PlacementEventInfos.TILE_CANNOT_BE_PLACED;
    }
}
