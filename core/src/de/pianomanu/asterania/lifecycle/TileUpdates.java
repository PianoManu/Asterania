package de.pianomanu.asterania.lifecycle;

import de.pianomanu.asterania.world.tile.Tiles;

public class TileUpdates {
    public static void updateTiles() {
        Tiles.WATER_TILE.checkForAnimationUpdate();
    }
}
