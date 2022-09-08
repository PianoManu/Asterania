package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.entities.Player;
import de.pianomanu.asterania.world.tile.tileutils.*;

public class PlantTile extends BasicDecorationLayerTile {
    protected PlantTile(String name, TileMaterial tileMaterial, TileSettings settings) {
        super(name, tileMaterial, settings);
    }

    @Override
    public PlacementEventInfo runPrePlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        super.runPrePlacementEvents(world, player, tileCoordinates);
        Tile backgroundTile = world.getTile(tileCoordinates);
        if (TileMaterials.canGrowPlant(backgroundTile.tileMaterial))
            return PlacementEventInfos.PLANT_CAN_GROW;
        return PlacementEventInfos.PLANT_CANNOT_GROW;
    }
}
