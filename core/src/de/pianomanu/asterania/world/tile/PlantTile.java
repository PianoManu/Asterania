package de.pianomanu.asterania.world.tile;

import de.pianomanu.asterania.entities.Player;
import de.pianomanu.asterania.world.World;
import de.pianomanu.asterania.world.coordinates.TileCoordinates;
import de.pianomanu.asterania.world.tile.tileutils.TileMaterial;
import de.pianomanu.asterania.world.tile.tileutils.TileMaterials;
import de.pianomanu.asterania.world.tile.tileutils.TileSettings;

public class PlantTile extends BasicDecorationLayerTile {
    protected PlantTile(String name, TileMaterial tileMaterial, TileSettings settings) {
        super(name, tileMaterial, settings);
    }

    @Override
    public boolean runPrePlacementEvents(World world, Player player, TileCoordinates tileCoordinates) {
        super.runPrePlacementEvents(world, player, tileCoordinates);
        Tile backgroundTile = world.getTile(tileCoordinates);
        return TileMaterials.canGrowPlant(backgroundTile.tileMaterial);
    }
}
